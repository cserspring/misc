package controller;

import javax.servlet.http.HttpServletRequest;

import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databean.Item;

import formbean.SearchForm;
import model.*;

public class SearchAction extends Action {
	private FormBeanFactory<SearchForm> formBeanFactory = FormBeanFactory.getInstance(SearchForm.class);
	ItemDAO itemDAO;
	public SearchAction(Model model){
		itemDAO = model.getItemDAO();
	}
	@Override
	public String getName() {
		return "search.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		try {
			SearchForm form = formBeanFactory.create(request);
			String keywords = form.getKeywords();
			if (keywords == null || keywords.length() == 0)
				return "displayItems.do";
			Item[] result = itemDAO.search(keywords);
			request.setAttribute("items", result);
			return "mainPage.jsp";
		} catch (FormBeanException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return"displayItems.do";
		}
		
	}

}
