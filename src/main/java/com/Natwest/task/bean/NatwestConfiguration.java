package com.Natwest.task.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NatwestConfiguration {
    private FileMetadata input;
    private FileMetadata reference;
    private FileMetadata output;
    private List<JoinMetadata> join;
}
