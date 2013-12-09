package controller;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;

import model.ItemDAO;
import model.Model;

public class DisplayItemsAction extends Action {
	private ItemDAO itemDAO;
	public DisplayItemsAction(Model model) {
		itemDAO = model.getItemDAO();
	}
	@Override
	public String getName() {
		return "displayItems.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		try {
			request.setAttribute("items", itemDAO.getItems());
			return "mainPage.jsp";
		} catch (RollbackException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
}

