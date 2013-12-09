package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databean.Item;
import databean.User;
import formbean.ChangePasswordForm;

import model.*;

public class ChangePasswordAction extends Action {
	private FormBeanFactory<ChangePasswordForm> formBeanFactory = FormBeanFactory.getInstance(ChangePasswordForm.class);
	private UserDAO userDAO;
	private ItemDAO itemDAO;
	public ChangePasswordAction(Model model){
		userDAO = model.getUserDAO();
		itemDAO = model.getItemDAO();
	}
	@Override
	public String getName() {
		return "changePassword.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);

        try {
            // Set up user list for nav bar
			//request.setAttribute("userList",userDAO.getUsers());
	        
	        // Load the form parameters into a form bean
	        ChangePasswordForm form = formBeanFactory.create(request);
	        User user = (User) request.getSession().getAttribute("user");
			Item[] items = itemDAO.getItemsByUserId(user.getId());
        	request.setAttribute("items", items);
	        // If no params were passed, return with no errors so that the form will be
	        // presented (we assume for the first time).
	        if (!form.isPresent()) {
	            return "changePassword.jsp";
	        }
	
	        // Check for any validation errors
	        errors.addAll(form.getValidationErrors());
	        if (errors.size() != 0) {
	            return "changePassword.jsp";
	        }
	
			// Change the password
        	userDAO.setNewPassword(user.getId(),form.getNewPassword());
        	
	        return "manageItems.jsp";
        } catch (RollbackException e) {
        	errors.add(e.toString());
        	return "error.jsp";
        } catch (FormBeanException e) {
        	errors.add(e.toString());
        	return "error.jsp";
        }
	}

}
