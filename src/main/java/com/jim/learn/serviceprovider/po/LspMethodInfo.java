package com.jim.learn.serviceprovider.po;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LspMethodInfo {

    private String methodName;
    private String params;
    private String staticMethod;
    private String abstractMethod;
    private String returnVal;
    private String description;
}
