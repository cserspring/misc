package controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.ItemDAO;
import model.Model;

import org.genericdao.RollbackException;
import org.mybeans.form.FileProperty;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databean.Item;
import databean.User;

import formbean.ItemForm;

public class AddAction extends Action {
	private FormBeanFactory<ItemForm>  itemFormFactory  = FormBeanFactory.getInstance(ItemForm.class);
	
	private ItemDAO itemDAO;

	public AddAction(Model model) {
		itemDAO = model.getItemDAO();
	}

	public String getName() { return "add.do"; }
    
    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        
        try {
        	
        	HttpSession session = request.getSession();
            User user = (User)(session.getAttribute("user"));
            if (user == null)
            	return "login.jsp";
            Item[] items = itemDAO.getItemsByUserId(user.getId());
            request.setAttribute("items", items);
       		// Fetch the items now, so that in case there is no form or there are errors
       		// We can just dispatch to the JSP to show the item list (and any errors)
       		
	        ItemForm form = itemFormFactory.create(request);
        	request.setAttribute("form", form);

	        errors.addAll(form.getValidationErrors());
	        if (errors.size() > 0) {
	        	return "manageItems.jsp";
	        }
	        Item item = new Item();
	        item.setTitle(fixBadChars(form.getTitle()));
	        item.setDescription(fixBadChars(form.getDescription()));
	        item.setPrice(Double.parseDouble(form.getPrice()));
	        Date date = new Date();
	        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        item.setListingDate(df.format(date));
	        FileProperty file = form.getFile();
	        item.setImageType(file.getContentType());
	        item.setImageBytes(file.getBytes());
	        item.setUserId(user.getId());
	        try {
				itemDAO.create(item);
				items = itemDAO.getItemsByUserId(user.getId());
	            request.setAttribute("items", items);
	            return "manageItems.jsp";
			} catch (RollbackException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "manageItems.jsp";
			}

        } catch (FormBeanException e) {
        	errors.add(e.getMessage());
        	return "error.jsp";
        }
    }
    
    private String fixBadChars(String s) {
		if (s == null || s.length() == 0) return s;
		
		Pattern p = Pattern.compile("[<>\"&]");
        Matcher m = p.matcher(s);
        StringBuffer b = null;
        while (m.find()) {
            if (b == null) b = new StringBuffer();
            switch (s.charAt(m.start())) {
                case '<':  m.appendReplacement(b,"&lt;");
                           break;
                case '>':  m.appendReplacement(b,"&gt;");
                           break;
                case '&':  m.appendReplacement(b,"&amp;");
                		   break;
                case '"':  m.appendReplacement(b,"&quot;");
                           break;
                default:   m.appendReplacement(b,"&#"+((int)s.charAt(m.start()))+';');
            }
        }
        
        if (b == null) return s;
        m.appendTail(b);
        return b.toString();
    }
}
