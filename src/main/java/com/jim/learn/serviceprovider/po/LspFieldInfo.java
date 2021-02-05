package com.jim.learn.serviceprovider.po;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LspFieldInfo {

    private String fieldName;
    private String type;
    private String staticField;
    private String constField;
    private String description;
}
