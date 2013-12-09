package edu.cmu.cs.webapp.todolist5.formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class ItemForm extends FormBean {
	private String item;
	
	public String getItem()  { return item; }
	
	public void setItem(String s)  { item = s.trim(); }

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (item == null || item.length() == 0) {
			errors.add("Item is required");
		}

        if (item.matches(".*[<>\"].*")) errors.add("Item may not contain angle brackets or quotes");

		return errors;
	}
}
