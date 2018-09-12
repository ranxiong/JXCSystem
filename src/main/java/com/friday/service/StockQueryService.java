package com.friday.service;

import java.util.List;

import com.friday.model.Shop;

public interface StockQueryService {
	public List<Object> stockQuery(int shopId) throws Exception;
	public List<Shop> shopQuery() throws Exception;
	public String QueryShopName(int shopId) throws Exception;
}
