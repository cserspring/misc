package edu.cmu.hw3.formbean;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class LoginForm {

	private String email;
	private String password;
	private String button;

	public LoginForm(HttpServletRequest request) {
		email = request.getParameter("loginEmail");
		password = request.getParameter("loginPassword");
		button = request.getParameter("button");
	}

	public boolean isPresent() {
		return (button != null);
	}

	/*
	 * Determine whether the value is valid
	 */
	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (email == null || email.length() == 0)
			errors.add("Email is required");
		if (password == null || password.length() == 0)
			errors.add("Password is required");
		return errors;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getButton() {
		return button;
	}
}
