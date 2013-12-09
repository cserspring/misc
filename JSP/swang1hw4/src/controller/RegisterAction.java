package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.*;

import org.mybeans.form.FormBeanFactory;

import databean.User;

import formbean.RegisterForm;

public class RegisterAction extends Action {
	private FormBeanFactory<RegisterForm> formBeanFactory = FormBeanFactory.getInstance(RegisterForm.class);
	private UserDAO userDAO;
	
	public RegisterAction(Model model) {
		userDAO = model.getUserDAO();
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "register.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute("user") != null) {
        	return "manageItems.do";
        }
		List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        try{
        	RegisterForm form = formBeanFactory.create(request);
        	request.setAttribute("form",form);
        	if (!form.isPresent()) {
	            return "register.jsp";
	        }
        	errors.addAll(form.getValidationErrors());
        	if (errors.size() != 0) {
	            return "register.jsp";
	        }
        	
        	User user = userDAO.getUserByUsername(form.getUsername());
        	if (user!=null){
        		errors.add("This username has been used");
	            return "register.jsp";
        	}
        	User newUser = new User();
        	newUser.setUsername(form.getUsername());
        	newUser.setFirstname(form.getFirstname());
        	newUser.setLastname(form.getLastname());
        	newUser.setEmail(form.getEmail());
        	newUser.setPassword(form.getPassword());
        	userDAO.create(newUser);
        	return "gotoLogin.do";
        } catch(Exception e){
        	return "error.jsp";
        }
	}

}
