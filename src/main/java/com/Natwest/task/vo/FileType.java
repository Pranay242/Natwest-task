package com.Natwest.task.vo;

public enum FileType {

    CSV(".csv"),
    EXCEL(".excel"),
    JSON(".json")
    ;

    private String extension;

    FileType(String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
