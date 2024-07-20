package com.Natwest.task.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FieldInfo {

    private String fieldName;
    private String dataType;
    private FormulaInfo formula;
    private String fileAlias;
}
