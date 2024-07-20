package com.Natwest.task.vo;

import com.Natwest.task.bean.NatwestConfiguration;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.io.File;

@Getter
@Setter
public class ReportGenerationVO {
    private int id;
    private String inputFilePath;
    private String referenceFilePath;
    private FileType inputExtension;
    private FileType referenceExtension;

    private String outputFilePath;
    private FileType outputExtension;

    @JsonIgnore
    private NatwestConfiguration natwestConfiguration;

}
