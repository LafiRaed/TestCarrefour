package org.carrefour.services;

import java.util.Map;

public interface TopSaleByStoreInter {
    public abstract Map<String,Double> getTopProduct(String transFilePath, String refProductFilePath);
}
