package com.romaway.common.protocol.tougu.entity;

/**
 * Created by kds on 2016/7/9.
 */
public class StockGroupEntity {
	private String stockCode;	// 证券代码
	private String stockName;	// 证券名称
	private String marketID;	// 市场代码
	private String bkCode;		// 板块代码
	private String bkName;		// 板块名称
	private String cangWei;	// 仓位（份额）
	private String cash;		// 现金值
	private String number;		// 持仓数量
	private String tradeNumber; // 可交易数量
	private String sfcj; // 是否成交

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

	public String getCash() {
		return cash;
	}

	public void setCash(String cash) {
		this.cash = cash;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getTradeNumber() {
		return tradeNumber;
	}

	public void setTradeNumber(String tradeNumber) {
		this.tradeNumber = tradeNumber;
	}

	public String getSfcj() {
		return sfcj;
	}

	public void setSfcj(String sfcj) {
		this.sfcj = sfcj;
	}
}
