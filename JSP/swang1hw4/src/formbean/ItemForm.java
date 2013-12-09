package formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FileProperty;
import org.mybeans.form.FormBean;

public class ItemForm extends FormBean {
	private String title;
	private String description;
	private String price;
	private FileProperty file;

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (title == null || title.length() == 0)
			errors.add("Title is required");
		if (description == null || description.length() == 0)
			errors.add("Description is required");
		if (price == null || price.length() == 0)
			errors.add("Price is required");
		if (file == null)
			errors.add("Image file is required");
		try {
			Double.parseDouble(price);
		} catch(NumberFormatException e){
			errors.add("Price must in numeric format");
		}
		if (errors.size() > 0) 
			return errors;

        if (title.matches(".*[<>\"].*")) 
        	errors.add("Title may not contain angle brackets or quotes");
        if (description.matches(".*[<>\"].*")) 
        	errors.add("Description may not contain angle brackets or quotes");
		return errors;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public FileProperty getFile() {
		return file;
	}

	public void setFile(FileProperty file) {
		this.file = file;
	}
}
