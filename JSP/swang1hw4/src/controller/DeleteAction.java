package controller;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.ItemDAO;
import model.Model;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databean.Item;
import databean.User;

import formbean.IdForm;

public class DeleteAction extends Action {
	private FormBeanFactory<IdForm> idFormFactory = FormBeanFactory.getInstance(IdForm.class);
	
	private ItemDAO itemDAO;

	public DeleteAction(Model model) {
		itemDAO = model.getItemDAO();
	}

	public String getName() { return "delete.do"; }
    
    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        
        try {
	        IdForm form = idFormFactory.create(request);
	        errors.addAll(form.getValidationErrors());
	        if (errors.size() > 0) {
	        	return "error.jsp";
	        }
	        HttpSession session = request.getSession();
            User user = (User)(session.getAttribute("user"));
            if (user == null)
            	return "login.jsp";
	    	itemDAO.delete(form.getIdAsInt());
	    	
            Item[] items = itemDAO.getItemsByUserId(user.getId());
            request.setAttribute("items", items);
       		return "manageItems.jsp";

        } catch (RollbackException e) {
        	errors.add(e.getMessage());
        	return "error.jsp";
        } catch (FormBeanException e) {
        	errors.add(e.getMessage());
        	return "error.jsp";
        }
    }
}
