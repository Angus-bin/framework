package com.romaway.common.protocol.tougu.entity;

import java.util.List;

/**
 * Created by edward on 16/7/12.
 */
public class PortfolioInfoEntity {

    public String bkCode;
    public String bkName;
    public String totalAmount;

    private List<StockInfoEntity> mStockInfoEntityList;

    public Float getCangWei() {
        Float tempCangWei = 0.00f;
        if(mStockInfoEntityList == null || mStockInfoEntityList.size() == 0)
            return tempCangWei;

        for (StockInfoEntity stockInfoEntity: mStockInfoEntityList)
            tempCangWei += Float.parseFloat(stockInfoEntity.cangWei);

        return tempCangWei;
    }

    public PortfolioInfoEntity(String bkCode, String bkName, List<StockInfoEntity> stockInfoEntityList){
        this.bkCode = bkCode;
        this.bkName = bkName;
        this.mStockInfoEntityList = stockInfoEntityList;
    }

//    public PortfolioInfoEntity(String bkCode, String bkName, String totalAmount, String cangWei){
//        this.bkCode = bkCode;
//        this.bkName = bkName;
//        this.totalAmount = totalAmount;
//        this.cangWei = cangWei;
//    }

    public String getBkCode() {
        return bkCode;
    }

    public void setBkCode(String bkCode) {
        this.bkCode = bkCode;
    }

    public String getBkName() {
        return bkName;
    }

    public void setBkName(String bkName) {
        this.bkName = bkName;
    }
}
