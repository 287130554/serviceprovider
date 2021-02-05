package com.jim.learn.serviceprovider.po;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LspJarInfo {

    private String packageInfo;
    private List<LspClassInfo> classes;
    private String description;
}
