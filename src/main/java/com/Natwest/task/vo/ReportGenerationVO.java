package com.Natwest.task.vo;

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

    private String outputFilePath;
    private FileType outputExtension;

}
