/*
 * File:   Item.java
 * Author: cserspring
 * ID:     swang1
 * Date:   02/09/2013
 */
package edu.cmu.hw3.databean;

import org.genericdao.PrimaryKey;

@PrimaryKey("id")
public class Item {
	int id;
	int userId;
	String listingDate;
	String description;
	String price;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getListingDate() {
		return listingDate;
	}
	public void setListingDate(String listingDate) {
		this.listingDate = listingDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
}
