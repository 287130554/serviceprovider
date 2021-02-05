package com.jim.learn.serviceprovider.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(value="用户信息", description="用于用户中心操作的报文")
public class UserCreateDTO {

    @ApiModelProperty(value="昵称",name="nickname",example="xingguo")
    private String nickname;

    @ApiModelProperty(value="性别",name="gender",required=true)
    private Integer gender;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }
}
