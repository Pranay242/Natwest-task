package com.Natwest.task.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FileMetadata {
    private String alias;
    private List<FieldInfo> fields;
}
