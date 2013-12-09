package edu.cmu.hw3.formbean;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

public class AddItemForm {
	int userId;
	String listingDate;
	String description;
	String price;
	public AddItemForm(HttpServletRequest request) {
		Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		listingDate = df.format(date);
		description = request.getParameter("description");
		price = request.getParameter("price");
	}
	
	/*
	 * Determine whether the value is valid
	 */
	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
		if (description == null || description.length() == 0)
			errors.add("Description is required");
		if (price == null || price.length() == 0)
			errors.add("Price is required");
		if (description != null && description.matches("\\s+"))
			errors.add("Description cannot be blank characters");
		return errors;
	}

	public int getUserId() {
		return userId;
	}

	public String getListingDate() {
		return listingDate;
	}

	public String getDescription() {
		return description;
	}

	public String getPrice() {
		return price;
	}
}
