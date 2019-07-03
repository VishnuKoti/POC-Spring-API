package com.macys.pricing.management.tool.pricemanagements.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;
import java.math.BigDecimal;

/**
 * Product entity.
 */
@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private Integer itemNum;
	private String itemName;
	private BigDecimal itemPrice;
	private Integer location;
	private String analyticsInfo;
	private String merchantDecision;
	
	public Product(Integer id,Integer itemNum,String itemName,BigDecimal itemPrice,Integer location,String analyticsInfo, String merchantDecision,Integer version)
	{
		this.itemNum=itemNum;
		this.itemName=itemName;
		this.itemPrice=itemPrice;
		this.location=location;
		this.analyticsInfo=analyticsInfo;
		this.merchantDecision=merchantDecision;
	}

	public Product() {
		// TODO Auto-generated constructor stub
	}

	@Version
	private Integer version;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getItemNum() {
		return itemNum;
	}

	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public BigDecimal getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(BigDecimal itemPrice) {
		this.itemPrice = itemPrice;
	}

	public Integer getLocation() {
		return location;
	}

	public void setLocation(Integer location) {
		this.location = location;
	}

	public String getAnalyticsInfo() {
		return analyticsInfo;
	}

	public void setAnalyticsInfo(String analyticsInfo) {
		this.analyticsInfo = analyticsInfo;
	}

	public String getMerchantDecision() {
		return merchantDecision;
	}

	public void setMerchantDecision(String merchantDecision) {
		this.merchantDecision = merchantDecision;
	}

}
