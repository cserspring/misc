package edu.cmu.hw3.formbean;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class RegisterForm {
	String username;
	String email;
	String firstname;
	String lastname;
	String password;
	String confirmPassword;
	String button;
	
	public RegisterForm(HttpServletRequest request) {
		username = request.getParameter("username");
		email = request.getParameter("email");
		firstname = request.getParameter("firstname");
		lastname = request.getParameter("lastname");
		confirmPassword = request.getParameter("confirmPassword");
		password = request.getParameter("password");
		button  = request.getParameter("button");
	}
	
	/*
	 * Determine whether the value is valid
	 */
	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (username == null || username.length() == 0)
			errors.add("User Name is required");
		if (password == null || password.length() == 0)
			errors.add("Password is required");
		if (confirmPassword == null || confirmPassword.length() == 0)
			errors.add("Confrim password is required");
		if (email == null || email.length() == 0)
			errors.add("Email is required");
		if (firstname == null || firstname.length() == 0)
			errors.add("First Name is required");
		if (lastname == null || lastname.length() == 0)
			errors.add("Last Name is required");
		if (username != null && !username.matches("\\S+"))
			errors.add("User Name doesn't permit blank characters");
		if (password != null && !password.matches("\\S+"))
			errors.add("Password doesn't permit blank characters");
		if (firstname != null && !firstname.matches("\\S+"))
			errors.add("First Name doesn't permit blank characters");
		if (lastname != null && !lastname.matches("\\S+"))
			errors.add("Last Name doesn't permit blank characters");
		if (confirmPassword != null && !confirmPassword.matches("\\S+"))
			errors.add("Confirm Password doesn't permit blank characters");
		if (confirmPassword != null && confirmPassword.length() > 0 && !(confirmPassword.equals(password)))
			errors.add("Password and confirm password are not matched");
		if (email != null && email.length() > 0 && !(email.matches("(\\S)+@(\\S)+")))
			errors.add("Invalid Format for E-mail Address");
		return errors;
	}
	
	public boolean isPresent() {
		return (button != null && button.equals("Submit"));
	}
	
	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public String getPassword() {
		return password;
	}
	
	public String getConfirmPassword() {
		return confirmPassword;
	}

	public String getButton() {
		return button;
	}
}
