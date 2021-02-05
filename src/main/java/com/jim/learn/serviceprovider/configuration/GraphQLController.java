package com.jim.learn.serviceprovider.configuration;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jim.learn.serviceprovider.graphQL.GraphQLProvider;
import graphql.ExecutionInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GraphQLController {

    @Autowired
    private GraphQLProvider graphQLProvider;

    @RequestMapping(value = "/graphql")
    // 这里定义的一个字符串接口所有的参数，定义对象也是可以的
    public Map<String, Object> graphql(@RequestBody String request) {

        System.out.println("the request is "+request);


        JSONObject req = JSON.parseObject(request);


        System.out.println("the query is "+req.getString("query")+" - the operationName is "+req.getString("operationName")
                +" - the variables is "+req.getJSONObject("variables"));

        ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                // 需要执行的查询语言
                .query(req.getString("query"))
                // 执行操作的名称，默认为null
                .operationName(req.getString("operationName"))
                // 获取query语句中定义的变量的值
                .variables(req.getJSONObject("variables"))
                .build();
        // 执行并返回结果
        return this.graphQLProvider.graphQL().execute(executionInput).toSpecification();
    }
}
