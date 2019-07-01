package org.carrefour.dao;

import java.io.*;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class FileRW  implements  FileRWInterf{

    /**
     *
     * @param filePath
     * @return
     */


    public Stream<String> getStreamFromFile(String filePath){
        Stream<String> stream = null;

        try {
            stream= Files.lines(Paths.get(filePath));

        } catch (IOException e){
            e.printStackTrace();
        }
       return  stream ;
    }

    /**
     *
     * @param filePath
     * @throws IOException
     */
    public void splitTransaction(String filePath, String outputPath) throws  IOException {

        Map<String, FileWriter>  splitTra = new HashMap<String,FileWriter>();
        String line;
        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        while ((line = reader.readLine()) != null)
        {
            String[] arr = line.split("\\|");


            if(splitTra.get(arr[2]) == null)
                splitTra.put(arr[2],new FileWriter(outputPath + arr[2] +".data")); // "/home/lafi/IdeaProjects/TestCarrefour/newData/1200Mag/"

            splitTra.get(arr[2]).write(arr[3]+"|"+arr[4]);
            splitTra.get(arr[2]).append("\n");


        }

        for (Map.Entry<String,FileWriter> entry: splitTra.entrySet()) {
            entry.getValue().flush();
            entry.getValue().close();
        }
    }


    /**
     *
     * @param map
     * @param fileName
     * @throws IOException
     */
    public void writeFileToDisk( Map<String,Double> map, String fileName) throws IOException {
        /*try {
            Writer writer = new BufferedWriter(new FileWriter(fileName));
            map.forEach((key, value) -> {
                try { writer.write( value + System.lineSeparator()); }
                catch (IOException e) {

                }
            });
        } catch(IOException e) {

        }*/

        Writer writer = new FileWriter(fileName);
        map.forEach((key, value) -> {
            try {
                writer.write(value.toString() + System.lineSeparator());
            }catch (IOException e){

            }
        });
        writer.flush();
        writer.close();
    }

    /**
     *
     * @param dirPath
     * @return
     */

    public List<String> listFileName(String dirPath){

        Stream<Path> paths = null;
        try {
            paths = Files.walk(Paths.get(dirPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return paths.
                filter(Files::isRegularFile).
                map(path -> path.toString()).collect(Collectors.toList());
    }

    public void getRefByMag(String magId){

    }

}
