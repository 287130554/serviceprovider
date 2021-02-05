package com.jim.learn.serviceprovider.controller;

import com.jim.learn.serviceprovider.po.UserCreateDTO;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

@Api(tags="用户管理", value="userCenter", consumes="application/json", produces="application/json", protocols="http")
@RestController
@RequestMapping("/user")
public class MAController {


    @ApiOperation(value="获取用户详细信息")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "query", dataType = "int")
    )
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=200,message="用户ID"),
            @ApiResponse(code=300,message="用户ID")
    })
    @GetMapping("/get")
    public String getUser(@RequestParam("id") Integer id) {

        System.out.println("DEMO:" + id);

        return "DEMO:" + id;
    }

    @ApiOperation(value="创建用户信息", notes="传递参数为非空", extensions = {
            @Extension(name="bizCode", properties = {
                    @ExtensionProperty(name="100000", value="处理成功"),
                    @ExtensionProperty(name="100001", value="接口状态处理异常")
            }),
            @Extension(name="响应编码说明2", properties = {
                    @ExtensionProperty(name="100000", value="处理成功"),
                    @ExtensionProperty(name="100001", value="接口状态处理异常")
            })
    })
    @ApiImplicitParams(
            @ApiImplicitParam(name = "createDTO", value = "用户信息", required = true, paramType = "body", dataType = "用户信息")
    )
    @PostMapping("/create")
    public UserCreateDTO createUser(@RequestBody UserCreateDTO createDTO) {
        System.out.println("[createUser][username("+createDTO.getNickname()+") password("+createDTO.getGender()+")]");
        return createDTO;
    }

//    @PostMapping("/update")
//    Response<UserCreateDTO> updateUser(@RequestBody UserCreateDTO createDTO){
//
//        System.out.println("[createUser][username("+createDTO.getNickname()+") password("+createDTO.getGender()+")]");
//        Response<UserCreateDTO> response = Response.successResponse(createDTO);
//        return response;
//    }

    @GetMapping("/hello")
    public String sayHello(@RequestParam("name") String name){

        System.out.println("hello "+name);
        return "hello"+name;
    }

}