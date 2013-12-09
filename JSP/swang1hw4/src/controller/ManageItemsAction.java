package controller;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.ItemDAO;
import model.Model;
import databean.Item;
import databean.User;


public class ManageItemsAction extends Action {
	private ItemDAO itemDAO;

	public ManageItemsAction(Model model) {
		itemDAO = model.getItemDAO();
	}

	public String getName() { return "manageItems.do"; }
    
    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        HttpSession session = request.getSession();
        User user = (User)(session.getAttribute("user"));
        if (user == null)
        	return "login.jsp";
        Item[] items = itemDAO.getItemsByUserId(user.getId());
        request.setAttribute("items", items);
		return ("manageItems.jsp");
    }
}
