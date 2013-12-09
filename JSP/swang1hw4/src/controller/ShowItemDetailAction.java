package controller;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databean.Item;
import databean.User;
import formbean.IdForm;

import model.*;

public class ShowItemDetailAction extends Action {
	private FormBeanFactory<IdForm>  formBeanFactory  = FormBeanFactory.getInstance(IdForm.class);
	
	ItemDAO itemDAO;
	UserDAO userDAO;
	public ShowItemDetailAction(Model model) {
		itemDAO = model.getItemDAO();
		userDAO = model.getUserDAO();
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "showItemDetail.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		
		try {
			IdForm form = formBeanFactory.create(request);
			int id = form.getIdAsInt();
			Item item = itemDAO.read(id);
			
			if (item != null) {
				request.setAttribute("item", item);
				User user = userDAO.read(item.getUserId());
				request.setAttribute("seller", user);
				return "showItemDetail.jsp";
			}
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FormBeanException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "error.jsp";
	}

}
