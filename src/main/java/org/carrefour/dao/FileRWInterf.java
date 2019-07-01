package org.carrefour.dao;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public interface FileRWInterf {

    public abstract Stream<String> getStreamFromFile(String filePath) ;

    public abstract void splitTransaction(String filePath , String outputPath) throws IOException;

    public abstract void writeFileToDisk(Map<String,Double> map, String path) throws IOException ;
    public abstract List<String> listFileName(String dirPath);
}
