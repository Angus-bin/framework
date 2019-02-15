package com.romaway.common.protocol.tougu.entity;

import java.io.Serializable;

/**
 * Created by edward on 16/7/11.
 */
public class StockInfoEntity implements Serializable {

    public String stockCode;
    public String stockName;
    public String marketID;
    /** 股票类型 */
    public String stockType;
    public String bkCode;
    public String bkName;
    public String cangWei;

    /** 总价值cash */
    public String amount;
    /** 最新价/最近成交价 */
    public String latestPrice;
    /** 涨跌幅 */
    public String priceZdf;
    /** 调仓前比例 */
    public String tcqbfb;
    /** 调仓后比例 */
    public String tchbfb;
    /** 买卖方向 */
    public String BSgoto;
    /** 股票数量 */
    public String gpsl;
    /** 停牌 */
    public String Suspension;
    /** 涨停 */
    public String SuspensionUp;
    /** 跌停 */
    public String SuspensionDown;
    /** 可卖最大比例 */
    public String UsableSellScale;
    /** 可买最大比例 */
    public String UsableBuyScale;
    /** 是否可以全量卖出 */
    public boolean isCanSellAll;

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(null == obj)
            return false;
        if(getClass() != obj.getClass())
            return false;

        StockInfoEntity stockInfoEntity = (StockInfoEntity)obj;
        if(stockCode.equalsIgnoreCase(stockInfoEntity.stockCode) && marketID.equalsIgnoreCase(stockInfoEntity.marketID)){
            return true;
        }
        return super.equals(obj);
    }

    /**
     * @param stockCode
     * @param stockName
     * @param marketID
     */
    public StockInfoEntity(String stockCode, String stockName, String marketID) {
        this.stockCode = stockCode;
        this.stockName = stockName;
        this.marketID = marketID;
    }

    /**
     *
     * @param stockCode
     * @param stockName
     * @param marketID
     * @param cangWei
     */
    public StockInfoEntity(String stockCode, String stockName, String marketID, String cangWei) {
        this.stockCode = stockCode;
        this.stockName = stockName;
        this.marketID = marketID;
        this.cangWei = cangWei;
    }

    /**
     * @param stockCode
     * @param stockName
     * @param marketID
     * @param stockType
     * @param bkCode
     * @param bkName
     * @param cangWei
     * @param latestPrice
     * @param priceZdf
     */
    public StockInfoEntity(String stockCode, String stockName, String marketID, String stockType,
                           String bkCode, String bkName, String cangWei, String latestPrice, String priceZdf) {
        this.stockCode = stockCode;
        this.stockName = stockName;
        this.marketID = marketID;
        this.bkCode = bkCode;
        this.bkName = bkName;
        this.cangWei = cangWei;
        this.stockType = stockType;
        this.latestPrice = latestPrice;
        this.priceZdf = priceZdf;
    }

    public StockInfoEntity(String stockCode, String stockName, String marketID, String stockType,
                           String bkCode, String bkName, String cangWei, String latestPrice, String priceZdf,
                           String tcqbfb, String tchbfb, String BSgoto, String gpsl, String amount) {
        this.stockCode = stockCode;
        this.stockName = stockName;
        this.marketID = marketID;
        this.stockType = stockType;
        this.bkCode = bkCode;
        this.bkName = bkName;
        this.cangWei = cangWei;
        this.latestPrice = latestPrice;
        this.priceZdf = priceZdf;
        this.tcqbfb = tcqbfb;
        this.tchbfb = tchbfb;
        this.BSgoto = BSgoto;
        this.gpsl = gpsl;
        this.amount = amount;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getMarketID() {
        return marketID;
    }

    public void setMarketID(String marketID) {
        this.marketID = marketID;
    }

    public String getStockType() {
        return stockType;
    }

    public void setStockType(String stockType) {
        this.stockType = stockType;
    }

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

    public String getCangWei() {
        return cangWei;
    }

    public void setCangWei(String cangWei) {
        this.cangWei = cangWei;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getLatestPrice() {
        return latestPrice;
    }

    public void setLatestPrice(String latestPrice) {
        this.latestPrice = latestPrice;
    }

    public String getPriceZdf() {
        return priceZdf;
    }

    public void setPriceZdf(String priceZdf) {
        this.priceZdf = priceZdf;
    }

    public String getTcqbfb() {
        return tcqbfb;
    }

    public void setTcqbfb(String tcqbfb) {
        this.tcqbfb = tcqbfb;
    }

    public String getTchbfb() {
        return tchbfb;
    }

    public void setTchbfb(String tchbfb) {
        this.tchbfb = tchbfb;
    }

    public String getBSgoto() {
        return BSgoto;
    }

    public void setBSgoto(String BSgoto) {
        this.BSgoto = BSgoto;
    }

    public String getGpsl() {
        return gpsl;
    }

    public void setGpsl(String gpsl) {
        this.gpsl = gpsl;
    }

    @Override
    public String toString() {
        return "StockInfoEntity{" +
                "stockCode='" + stockCode + '\'' +
                ", stockName='" + stockName + '\'' +
                ", marketID='" + marketID + '\'' +
                ", stockType='" + stockType + '\'' +
                ", bkCode='" + bkCode + '\'' +
                ", bkName='" + bkName + '\'' +
                ", cangWei='" + cangWei + '\'' +
                ", amount='" + amount + '\'' +
                ", latestPrice='" + latestPrice + '\'' +
                ", priceZdf='" + priceZdf + '\'' +
                ", tcqbfb='" + tcqbfb + '\'' +
                ", tchbfb='" + tchbfb + '\'' +
                ", BSgoto='" + BSgoto + '\'' +
                ", gpsl='" + gpsl + '\'' +
                ", Suspension='" + Suspension + '\'' +
                ", SuspensionUp='" + SuspensionUp + '\'' +
                ", SuspensionDown='" + SuspensionDown + '\'' +
                ", UsableSellScale='" + UsableSellScale + '\'' +
                ", UsableBuyScale='" + UsableBuyScale + '\'' +
                ", isCanSellAll=" + isCanSellAll +
                '}';
    }
}