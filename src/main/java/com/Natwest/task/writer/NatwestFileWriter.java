package com.Natwest.task.writer;

import java.io.IOException;
import java.util.Map;

public interface NatwestFileWriter {

    void writeRow(String[] row);

    void closeAll() throws IOException;

}
