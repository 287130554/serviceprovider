package com.jim.learn.serviceprovider.po;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 类型信息
 */
@Getter
@Setter
public class LspClassInfo {

    private String className;
    private String staticClass;
    private String abstractClass;
    private String interfaceCalss;
    private String description;

    private List<LspFieldInfo> fields;
    private List<LspMethodInfo> methods;
}
