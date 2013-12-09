package controller;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databean.Item;
import formbean.IdForm;

import model.*;

public class ImageAction extends Action {
	private FormBeanFactory<IdForm>  formBeanFactory  = FormBeanFactory.getInstance(IdForm.class);
	
	private ItemDAO itemDAO;
	
	public ImageAction(Model model) {
		itemDAO = model.getItemDAO();
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "image.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		
		try {
			IdForm form = formBeanFactory.create(request);
			int id = form.getIdAsInt();
			Item item = itemDAO.read(id);
			
			if (item != null) {
				request.setAttribute("photo", item);
				return "image";
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
