package com.jim.learn.serviceprovider.domain;

import com.alibaba.fastjson.JSONObject;
import com.jim.learn.serviceprovider.po.*;
import com.jim.learn.serviceprovider.vo.ClassVo;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class LspManage {

    /**
     * 获取语言对应的关键字信息
     * @return LspKeyWordInfo
     */
    public LspKeyWordInfo getLspKeyword(){

        LspKeyWordInfo lspKeyWordInfo = new LspKeyWordInfo();
        List<String> keywords = this.getKeywords();
        lspKeyWordInfo.setKeywords(keywords);

        return lspKeyWordInfo;
    }

    /**
     * 获取场景对应的Lsp信息
     * @return
     */
    public LspModelInfo getLspModelInfoOfScence(){

        LspModelInfo lspModelInfo = new LspModelInfo();
        lspModelInfo.setModelsInfo(this.getModelInfoOfLsp());
        return lspModelInfo;
    }

    /**
     * 获取jar包的描述信息
     * @return
     */
    public List<ClassVo> getJarInfo(){

        LspJarInfo lspJarInfo = getJar();

        List<ClassVo> classVos = new ArrayList<ClassVo>();

        //设置类的信息
        LspClassInfo lspClassInfo = lspJarInfo.getClasses().get(0);
        ClassVo classItem = new ClassVo();
        classItem.setNamespace(lspJarInfo.getPackageInfo());
        classItem.setKeyword(lspClassInfo.getClassName());
        classItem.setInsertword(lspClassInfo.getClassName());
        classItem.setDescription(lspClassInfo.getDescription());
        classItem.setType("Variable");
        classVos.add(classItem);
        String prefixClass = lspClassInfo.getClassName().substring(0, 1).toLowerCase() + lspClassInfo.getClassName().substring(1);

        ClassVo instanceItem = new ClassVo();
        instanceItem.setNamespace(lspJarInfo.getPackageInfo());
        instanceItem.setKeyword(prefixClass);
        instanceItem.setInsertword(prefixClass);
        instanceItem.setDescription(lspClassInfo.getDescription());
        instanceItem.setType("Class");
        classVos.add(instanceItem);

        //获取类对应的方法信息
        for(LspMethodInfo lspMethodInfo : lspClassInfo.getMethods()){

            ClassVo methodItem = new ClassVo();
            methodItem.setNamespace(lspJarInfo.getPackageInfo());

            //方法类型
            if("static".equals(lspMethodInfo.getStaticMethod())){

                //静态方法
                methodItem.setKeyword(prefixClass + "." + lspMethodInfo.getMethodName() + lspMethodInfo.getParams() + " " + lspMethodInfo.getReturnVal() + " static ");
                methodItem.setInsertword(prefixClass + "." + lspMethodInfo.getMethodName() + lspMethodInfo.getParams());

            }else if("abstract".equals(lspMethodInfo.getAbstractMethod())){

                //抽象方法
                methodItem.setKeyword(prefixClass + "." + lspMethodInfo.getMethodName() + lspMethodInfo.getParams() + " " + lspMethodInfo.getReturnVal() + " abstract ");
                methodItem.setInsertword(prefixClass + "." + lspMethodInfo.getMethodName() + lspMethodInfo.getParams());
            }else{

                //正常方法
                methodItem.setKeyword(prefixClass + "." + lspMethodInfo.getMethodName() + lspMethodInfo.getParams() + " " + lspMethodInfo.getReturnVal());
                methodItem.setInsertword(prefixClass + "." + lspMethodInfo.getMethodName() + lspMethodInfo.getParams());
            }
            methodItem.setDescription(lspMethodInfo.getDescription());
            methodItem.setType("Method");
            classVos.add(methodItem);
        }

        //获取类对应的字段信息
        for(LspFieldInfo lspFieldInfo : lspClassInfo.getFields()){

            ClassVo fieldItem = new ClassVo();
            fieldItem.setNamespace(lspJarInfo.getPackageInfo());

            if("const".equals(lspFieldInfo.getConstField())){

                //常量属性
                fieldItem.setKeyword(prefixClass+"." + lspFieldInfo.getFieldName() + " const");
                fieldItem.setInsertword(prefixClass+"." + lspFieldInfo.getFieldName());

            }else if("static".equals(lspFieldInfo.getStaticField())){

                //静态属性
                fieldItem.setKeyword(prefixClass+"." + lspFieldInfo.getFieldName() + " static");
                fieldItem.setInsertword(prefixClass+"." + lspFieldInfo.getFieldName());
            }else{

                //正常属性
                fieldItem.setKeyword(prefixClass+"." + lspFieldInfo.getFieldName());
                fieldItem.setInsertword(prefixClass+"." + lspFieldInfo.getFieldName());
            }
            fieldItem.setDescription(lspFieldInfo.getDescription());
            fieldItem.setType("Field");
            classVos.add(fieldItem);
        }

        return classVos;
    }

    /**
     * 系统关键字
     * @return
     */
    private List<String> getKeywords(){

        String[] keys = new String[]{"abstract", "continue", "for", "new", "switch", "assert", "default", "goto", "package", "synchronized",
                "boolean", "do", "if", "private", "this", "break", "double", "implements", "protected", "throw", "byte", "else", "import",
                "public", "throws", "case", "enum", "instanceof", "return", "transient", "catch", "extends", "String", "Map", "int", "short", "try", "char",
                "final", "interface", "static", "void", "class", "finally", "long", "strictfp", "volatile", "const", "float", "native", "super",
                "while", "true", "false"};

        List<String> keywords = new ArrayList<String>();
        for(String item : keys){
            keywords.add(item);
        }
        return keywords;
    }

    /**
     * 获取LspModel的描述信息
     * @return
     */
    private List<LspModelItem> getModelInfoOfLsp(){

        String jsonInfo = "{\"type\":\"object\",\"title\":\"OrderDetailDTO\",\"description\":\"订单详情TabDTO实体\"," +
                "\"properties\":{\"orderClearCustoms\":{\"type\":\"object\",\"title\":\"OrderClearCustomsDTO\"," +
                "\"description\":\"DTO实体\",\"properties\":{\"customsAgentBpId\":{\"type\":\"integer\",\"format\":\"int64\"," +
                "\"description\":\"清关代供应商id\"},\"quantity\":{\"type\":\"integer\",\"format\":\"int32\",\"description\":\"品名数量（前端用）" +
                "\"},\"preCustomsNo\":{\"type\":\"string\",\"description\":\"预报编号（前端用）\"},\"BPInfoVO\":{\"type\":\"object\"," +
                "\"properties\":{\"bpId\":{\"type\":\"number\",\"description\":\"bpId\"},\"bpName\":{\"type\":\"string\"," +
                "\"description\":\"BP名称\"},\"bpAddress\":{\"type\":\"string\",\"description\":\"BP地址\"}," +
                "\"supplierId\":{\"type\":\"number\",\"description\":\"供应商id\"},\"isOldOrderSupplier\":{\"type\":\"boolean\",\"description\":\"是否是订单老数据\"}}," +
                "\"description\":\"供应商信息（前端用）\"},\"isCheck\":{\"type\":\"boolean\",\"description\":\"是否查验（前端用）\"}," +
                "\"goods\":{\"type\":\"string\",\"description\":\"品名（前端用）\"},\"checkRemark\":{\"type\":\"string\"," +
                "\"description\":\"查验说明（前端用）\"}}},\"orderBaseInfo\":{\"type\":\"object\",\"title\":\"OrderBaseInfoDTO\",\"description\":" +
                "\"订单基础信息DTO实体\",\"properties\":{\"canvassingType\":{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"number\"," +
                "\"description\":\"id\"},\"name\":{\"type\":\"string\",\"description\":\"名称\"},\"chineseName\":{\"type\":\"string\",\"description\":" +
                "\"中文名称\"},\"englishName\":{\"type\":\"string\",\"description\":\"英文名称\"},\"shortCode\":{\"type\":\"string\",\"description\":\"简码\"}}," +
                "\"description\":\"揽货方式 canvassingType,canvassingId\"},\"consolType\":{\"type\":\"integer\",\"format\":\"int32\",\"description\":" +
                "\"装箱类型\"},\"customerTitle\":{\"type\":\"string\",\"description\":\"下单抬头\"},\"fulfillShippingInfo\":{\"type\":\"object\"," +
                "\"title\":\"FulfillShippingInfoDTO\",\"description\":\"订舱信息\",\"properties\":{\"paymentWay\":{\"type\":\"object\",\"properties\":" +
                "{\"id\":{\"type\":\"number\",\"description\":\"id\"},\"name\":{\"type\":\"string\",\"description\":\"名称\"},\"chineseName\":{\"type\":" +
                "\"string\",\"description\":\"中文名称\"},\"englishName\":{\"type\":\"string\",\"description\":\"英文名称\"},\"shortCode\":{\"type\":" +
                "\"string\",\"description\":\"简码\"}},\"description\":\"收款方式 paymentWay,acceptWayId\"},\"billTypeVO\":{\"type\":\"object\"," +
                "\"properties\":{\"id\":{\"type\":\"number\",\"description\":\"id\"},\"name\":{\"type\":\"string\",\"description\":\"名称\"}," +
                "\"chineseName\":{\"type\":\"string\",\"description\":\"中文名称\"},\"englishName\":{\"type\":\"string\",\"description\":\"英文名称\"}," +
                "\"shortCode\":{\"type\":\"string\",\"description\":\"简码\"}},\"description\":\"提单类型 bill_type,billType\"},\"hsignReasonVO\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"number\",\"description\":\"id\"},\"name\":{\"type\":\"string\",\"description\":\"名称\"},\"chineseName\":{\"type\":\"string\",\"description\":\"中文名称\"},\"englishName\":{\"type\":\"string\",\"description\":\"英文名称\"},\"shortCode\":{\"type\":\"string\",\"description\":\"简码\"}},\"description\":\"签发原因\"},\"description\":\"签发原因\"},\"deliveryMode\":{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"number\",\"description\":\"id\"},\"name\":{\"type\":\"string\",\"description\":\"名称\"},\"chineseName\":{\"type\":\"string\",\"description\":\"中文名称\"},\"englishName\":{\"type\":\"string\",\"description\":\"英文名称\"},\"shortCode\":{\"type\":\"string\",\"description\":\"简码\"}},\"description\":\"放货方式 deliveryMode,deliveryMode\"},\"freeTime\":{\"type\":\"string\",\"description\":\"免箱时间\"},\"freeTimeRemark\":{\"type\":\"string\",\"description\":\"免用箱期备注\"},\"hdeliveryMode\":{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"number\",\"description\":\"id\"},\"name\":{\"type\":\"string\",\"description\":\"名称\"},\"chineseName\":{\"type\":\"string\",\"description\":\"中文名称\"},\"englishName\":{\"type\":\"string\",\"description\":\"英文名称\"},\"shortCode\":{\"type\":\"string\",\"description\":\"简码\"}},\"description\":\"H放货方式 deliveryMode,hDeliveryModeId\"},\"hPaymentWay\":{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"number\",\"description\":\"id\"},\"name\":{\"type\":\"string\",\"description\":\"名称\"},\"chineseName\":{\"type\":\"string\",\"description\":\"中文名称\"},\"englishName\":{\"type\":\"string\",\"description\":\"英文名称\"},\"shortCode\":{\"type\":\"string\",\"description\":\"简码\"}},\"description\":\"H付款方式 paymentWay,hpaymentWayId\"},\"hShippingClause\":{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"number\",\"description\":\"id\"},\"name\":{\"type\":\"string\",\"description\":\"名称\"},\"chineseName\":{\"type\":\"string\",\"description\":\"中文名称\"},\"englishName\":{\"type\":\"string\",\"description\":\"英文名称\"},\"shortCode\":{\"type\":\"string\",\"description\":\"简码\"}},\"description\":\"H运输条款 shippingClause,hshippingClauseId\"},\"mcopyNo\":{\"type\":\"string\"},\"moriginalNo\":{\"type\":\"string\"},\"shippingClause\":{\"type\":\"object\",\"properties\":{},\"description\":\"运输条款\"},\"tradeTerms\":{\"type\":\"object\",\"properties\":{},\"description\":\"交易类型\"}},\"required\":[]},\"fullfillMainCargos\":{\"type\":\"array\",\"description\":\"货物\",\"items\":{\"type\":\"object\",\"title\":\"FullfillMainCargoDTO\",\"description\":\"履约货物DTO\",\"properties\":{\"cargoNameCn\":{\"type\":\"string\",\"description\":\"货物中文名称\"}}}},\"fullfillTrunkLineSea\":{\"type\":\"object\",\"title\":\"FullfillTrunkLineSeaDTO\",\"description\":\"干线方案\",\"properties\":{\"carrier\":{\"type\":\"object\",\"properties\":{},\"description\":\"承运人\"},\"fromTerminal\":{\"type\":\"object\",\"properties\":{},\"description\":\"起运港\"},\"receiptPlaceTerminal\":{\"type\":\"object\",\"properties\":{},\"description\":\"收货地\"},\"toTerminal\":{\"type\":\"object\",\"properties\":{},\"description\":\"目的港\"},\"vessel\":{\"type\":\"string\",\"description\":\"船名\"},\"viaTerminalCode\":{\"type\":\"string\",\"description\":\"卸货港代码(中转港代码)\"},\"viaTerminal\":{\"type\":\"object\",\"properties\":{},\"description\":\"中转港\"},\"voyage\":{\"type\":\"string\",\"description\":\"航次\"}},\"required\":[]},\"fulfillCabinInfoDTO\":{\"type\":\"object\",\"properties\":{\"closingCustomDate\":{\"type\":\"integer\",\"description\":\"截关时间\"},\"vgmCloseDate\":{\"type\":\"integer\",\"description\":\"VGM截止时间\"},\"billCloseDate\":{\"type\":\"integer\",\"description\":\"截单时间\"},\"mblno\":{\"type\":\"string\",\"description\":\"M提单号\"},\"hblno\":{\"type\":\"string\",\"description\":\"H提单号\"},\"shippingConfirmComments\":{\"type\":\"string\",\"description\":\"订舱备注\"}}},\"fulfillShipBookingRecord\":{\"type\":\"object\",\"properties\":{\"overseaName\":{\"type\":\"string\",\"description\":\"海外代理\"}}},\"importAndExportType\":{\"type\":\"integer\",\"format\":\"int32\",\"description\":\"进出口类型\"},\"noProductType\":{\"type\":\"integer\",\"format\":\"int32\",\"description\":\"无商品类别\"},\"orderBusinessType\":{\"type\":\"integer\",\"format\":\"int32\",\"description\":\"订单业务类型(1.海运整箱出口 2.海运FBA整箱出口 3.海铁联运整箱出口 4.铁路整箱出口)\"},\"orderNotes\":{\"type\":\"string\",\"description\":\"下单备注\"},\"orderRelatedNos\":{\"type\":\"array\",\"description\":\"关单号\",\"items\":{\"type\":\"string\"}},\"transportType\":{\"type\":\"integer\",\"format\":\"int32\",\"description\":\"运输类型\"},\"workNo\":{\"type\":\"string\",\"description\":\"客户工作号\"}}},\"workItSuppliers\":{\"type\":\"object\",\"title\":\"WorkItSupplierOutputDTO\",\"description\":\"DTO实体\",\"properties\":{\"BPInfoVO\":{\"type\":\"object\",\"properties\":{\"bpId\":{\"type\":\"number\",\"description\":\"bpId\"},\"bpName\":{\"type\":\"string\",\"description\":\"BP名称\\t\"},\"bpAddress\":{\"type\":\"string\",\"description\":\"BP地址\\t\"},\"supplierId\":{\"type\":\"string\",\"description\":\"供应商id\"},\"isOldOrderSupplier\":{\"type\":\"string\",\"description\":\"是否是订单老数据\"}},\"description\":\"供应商信息（前端用）\"},\"importedInlandTransportItemOutputDTOS\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"containerNo\":{\"type\":\"string\",\"description\":\"箱号\"},\"deliveryDate\":{\"type\":\"integer\",\"description\":\"派送时间\"},\"deliveryAddress\":{\"type\":\"string\"}}}}}},\"insuranceDetails\":{\"type\":\"object\",\"title\":\"InsuranceDetailOutput\",\"properties\":{\"additionRate\":{\"type\":\"number\",\"description\":\"加成比例\"},\"channelId\":{\"type\":\"integer\",\"format\":\"int32\",\"description\":\"投保渠道id\"},\"clauseCompany\":{\"type\":\"object\",\"properties\":{},\"description\":\"保险公司\"},\"clause\":{\"type\":\"object\",\"properties\":{},\"description\":\"主险条款\"},\"contractNum\":{\"type\":\"string\",\"description\":\"合同号\"},\"currencyVO\":{\"type\":\"object\",\"properties\":{},\"description\":\"投保币种\"},\"goodsMarks\":{\"type\":\"string\",\"description\":\"货物标的描述\"},\"insuranceAmount\":{\"type\":\"number\",\"description\":\"保险金额\"},\"insuranceFee\":{\"type\":\"number\",\"description\":\"保费\"},\"insuranceNum\":{\"type\":\"string\",\"description\":\"投保单号\"},\"insuranceRate\":{\"type\":\"number\",\"description\":\"费率\"},\"insuredPerson\":{\"type\":\"string\",\"description\":\"被投保人\"},\"invoiceAmount\":{\"type\":\"number\",\"description\":\"发票金额\"},\"policyHolder\":{\"type\":\"string\",\"description\":\"投保人\"},\"policyNo\":{\"type\":\"string\",\"description\":\"保险号\"},\"settleClaimTerminalVO\":{\"type\":\"integer\",\"format\":\"int64\",\"description\":\"赔偿地港口\"}},\"required\":[],\"description\":\"派送地址\"},\"warehouse\":{\"type\":\"object\",\"properties\":{\"BPInfoVO\":{\"type\":\"object\",\"properties\":{\"bpId\":{\"type\":\"string\",\"description\":\"bpId\"},\"bpName\":{\"type\":\"string\",\"description\":\"BP名称\"},\"bpAddress\":{\"type\":\"string\",\"description\":\"BP地址\"},\"supplierId\":{\"type\":\"string\",\"description\":\"供应商id\"},\"isOldOrderSupplier\":{\"type\":\"string\",\"description\":\"是否是订单老数据\"}},\"description\":\"供应商信息\"},\"inWarehouseDate\":{\"type\":\"integer\",\"description\":\"入库时间\"},\"outWarehouseDate\":{\"type\":\"integer\",\"description\":\"出库时间\"}},\"description\":\"仓储信息\",\"required\":[]}}}\n";


        JSONObject jsonHandler = JSONObject.parseObject(jsonInfo);
        List<LspModelItem> blsModels = new ArrayList<LspModelItem>();
        parseJson(jsonHandler, "", blsModels);
        return blsModels;
    }

    /**
     * 递归获取json Schema中model的属性信息
     * @param jsonObject
     * @param prefix
     * @param blsModels
     */
    private void parseJson(JSONObject jsonObject, String prefix, List<LspModelItem> blsModels){

        JSONObject jsonNode = jsonObject.getJSONObject("properties");
        JSONObject arrayNode = jsonObject.getJSONObject("items");

        if((null == jsonNode) && (null == arrayNode)){
            return;
        } else if (null != arrayNode){
            Set<String> itemKeys = arrayNode.keySet();
            for(String arrayItem : itemKeys){
                if("object".equals(arrayNode.getString(arrayItem))) {
                    parseJson(arrayNode, prefix, blsModels);
                }else{
                    return;
                }
            }
        } else if(null != jsonNode) {

            Set<String> jsonsKey = jsonNode.keySet();
            for(String item : jsonsKey){

                //获取lsp对象信息
                LspModelItem lspModelItem = new LspModelItem();

                //对象的描述及其属性
                lspModelItem.setDescription(jsonNode.getJSONObject(item).getString("description"));
                if(null != jsonNode.getJSONObject(item).getString("title")){
                    lspModelItem.setType(jsonNode.getJSONObject(item).getString("title"));
                }else{
                    lspModelItem.setType(jsonNode.getJSONObject(item).getString("type"));
                }

                if("".equals(prefix)){

                    lspModelItem.setFieldName(item);
                    blsModels.add(lspModelItem);
                    parseJson(jsonNode.getJSONObject(item), item, blsModels);

                }else {
                    lspModelItem.setFieldName(prefix + "." + item );
                    blsModels.add(lspModelItem);
                    parseJson(jsonNode.getJSONObject(item), prefix + "." + item, blsModels);
                }
            }
        }
    }

    /**
     * 获取jar包的描述信息
     * @return
     */
    private LspJarInfo getJar(){

        LspJarInfo lspJarInfo = new LspJarInfo();

        //设置jar包的信息
        lspJarInfo.setPackageInfo("com.jim.business.service");
        lspJarInfo.setDescription("运去哪订单中台订单处理能力");

        //jar包中的类
        LspClassInfo lspClassInfo = new LspClassInfo();
        lspClassInfo.setClassName("OrderService");
        lspClassInfo.setDescription("订单生命周期处理类");

        //类中的方法
        List<LspMethodInfo> lspMethodInfos = new ArrayList<LspMethodInfo>();
        LspMethodInfo normalMethod = new LspMethodInfo();
        normalMethod.setMethodName("createOrder");
        normalMethod.setParams("(UserPo, OrderDetailsPo)");
        normalMethod.setReturnVal("OrderPo");
        normalMethod.setDescription("运去哪订单中台下单接口");
        lspMethodInfos.add(normalMethod);

        LspMethodInfo staticMethod = new LspMethodInfo();
        staticMethod.setMethodName("generateOrderNum");
        staticMethod.setParams("()");
        staticMethod.setReturnVal("String");
        staticMethod.setStaticMethod("static");
        staticMethod.setDescription("该方法为静态方法，生产全局唯一的订单号");
        lspMethodInfos.add(staticMethod);

        LspMethodInfo abstractMethod = new LspMethodInfo();
        abstractMethod.setMethodName("orderStatusHandle");
        abstractMethod.setParams("(OrderPo)");
        abstractMethod.setReturnVal("OrderStatusPo");
        abstractMethod.setAbstractMethod("abstract");
        abstractMethod.setDescription("订单状态处理，该方法为抽象方法，需要各业务实现处理逻辑");
        lspMethodInfos.add(abstractMethod);
        lspClassInfo.setMethods(lspMethodInfos);

        //类中的字段
        List<LspFieldInfo> lspFieldInfos = new ArrayList<LspFieldInfo>();
        LspFieldInfo normalField = new LspFieldInfo();
        normalField.setFieldName("orderDao");
        normalField.setType("OrderDao");
        normalField.setDescription("订单数据访问层对象");
        lspFieldInfos.add(normalField);

        LspFieldInfo constField = new LspFieldInfo();
        constField.setFieldName("userPo");
        constField.setType("UserPo");
        constField.setConstField("const");
        constField.setDescription("用户信息为常量对象，不运行被修改");
        lspFieldInfos.add(constField);

        LspFieldInfo staticField = new LspFieldInfo();
        staticField.setFieldName("orderSequence");
        staticField.setType("OrderSequence");
        staticField.setStaticField("static");
        staticField.setDescription("订单唯一标识生产工具类");
        lspFieldInfos.add(staticField);
        lspClassInfo.setFields(lspFieldInfos);

        List<LspClassInfo> lspClassInfos = new ArrayList<LspClassInfo>();
        lspClassInfos.add(lspClassInfo);
        lspJarInfo.setClasses(lspClassInfos);

        return lspJarInfo;
    }
}
