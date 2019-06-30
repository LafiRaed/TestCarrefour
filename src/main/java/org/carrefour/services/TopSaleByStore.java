package org.carrefour.services;

import org.carrefour.dao.FileRW;
import org.carrefour.dao.Product;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.regex.Pattern;

public class TopSaleByStore implements TopSaleByStoreInter {

    private Map<String,Double> topProduct = new HashMap<>();
    private FileRW filerw = new FileRW();
    Pattern pattern = Pattern.compile("\\|");

    /**
     *
     * @param transFilePath
     * @param refProductFilePath
     * @return
     */
    public Map<String,Double> getTopProduct(String transFilePath, String refProductFilePath){

        // Reduce Product/Quantity
        Stream<String> pQStream = filerw.getStreamFromFile(transFilePath);
        Map<String, Integer> reducedProduct = pQStream.map(line->{
            String[] arr = pattern.split(line);
            return new Product(arr[0],
                    Integer.parseInt(arr[1]));
        }).collect(Collectors.groupingBy(x->x.getId_product(),Collectors.summingInt(Product::getQte)));

        // Map Product /Price
        Stream<String> pPStream = filerw.getStreamFromFile(refProductFilePath);
        Map<String, String> productPrice = pPStream.
                map(line->pattern.split(line)).
                collect(Collectors.toMap(line->line[0],line->line[1]));

        // Get Top 100 product
        for (String key: reducedProduct.keySet()){
            topProduct.put(key, Double.parseDouble(productPrice.get(key)) * reducedProduct.get(key));
        }

         Map<String,Double> result = topProduct.entrySet().stream()
                 .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                 .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                         (oldValue, newValue)->oldValue,LinkedHashMap::new));
        /*entrySet().
                stream().
                limit(100).
                collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));

         */


        //System.out.println(topProduct);
        return  result;
    }


}
