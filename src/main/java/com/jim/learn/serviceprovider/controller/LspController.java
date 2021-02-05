package com.jim.learn.serviceprovider.controller;

import com.jim.learn.serviceprovider.domain.LspManage;
import com.jim.learn.serviceprovider.po.LspKeyWordInfo;
import com.jim.learn.serviceprovider.po.LspModelInfo;
import com.jim.learn.serviceprovider.vo.ClassVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/lsp")
public class LspController {

    @Autowired
    private LspManage lspManage;

    /**
     * 获取系统关键字信息
     * @return
     */
    @GetMapping(value = "/keywords")
    public LspKeyWordInfo getLspKeyWord(){

        return lspManage.getLspKeyword();
    }

    /**
     * 获取场景模型的上下文信息
     * @return
     */
    @GetMapping(value = "/modelSence")
    public LspModelInfo getLspModel(){
        return lspManage.getLspModelInfoOfScence();
    }

    @GetMapping(value = "/jarInfo")
    public List<ClassVo> getJarInfo(){

        return lspManage.getJarInfo();
    }
}
