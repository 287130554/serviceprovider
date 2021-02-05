package com.jim.learn.serviceprovider.po;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class LspModelInfo {

    private String appId;
    private List<LspModelItem> modelsInfo = new ArrayList<LspModelItem>();
}
