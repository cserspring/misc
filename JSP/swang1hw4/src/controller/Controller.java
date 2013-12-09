package controller;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.genericdao.RollbackException;
import model.ItemDAO;
import model.Model;
import model.UserDAO;

import databean.Item;
import databean.User;



public class Controller extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public void init() throws ServletException {
        Model model = new Model(getServletConfig());
        Action.add(new DisplayItemsAction(model));
        Action.add(new ShowItemDetailAction(model));
        Action.add(new ImageAction(model));
        Action.add(new GotoLoginAction(model));
        Action.add(new GotoRegisterAction(model));
        Action.add(new LoginAction(model));
        Action.add(new ManageItemsAction(model));
        Action.add(new AddAction(model));
        Action.add(new DeleteAction(model));
        Action.add(new LogoutAction(model));
        Action.add(new RegisterAction(model));
        Action.add(new SearchAction(model));
        Action.add(new GotoChangePasswordAction(model));
        Action.add(new ChangePasswordAction(model));
        
		UserDAO userDAO = model.getUserDAO();
		ItemDAO itemDAO = model.getItemDAO();
		try {
			/* Pre-populated the database */
			if (userDAO.getCount() == 0) {
				User user = new User();
				user.setUsername("shuai");
				user.setPassword("shuai");
				user.setFirstname("Shuai");
				user.setLastname("Wang");
				user.setEmail("shuai@cmu.edu");
				userDAO.create(user);
				
				user.setUsername("sun");
				user.setPassword("sun");
				user.setFirstname("Jim");
				user.setLastname("Green");
				user.setEmail("sun@cmu.edu");
				userDAO.create(user);
				
				user.setUsername("oracle");
				user.setPassword("oracle");
				user.setFirstname("Leo");
				user.setLastname("Lin");
				user.setEmail("oracle@cmu.edu");
				userDAO.create(user);
				createItem("Apple", 1, "an apple", "apple", itemDAO);
				createItem("Pear", 1, "a pear", "pear", itemDAO);
				createItem("Grape", 1, "a grape", "grape", itemDAO);
				createItem("Grapefruit", 2, "a greapefruit", "grapefruit", itemDAO);
				createItem("Orange", 2, "an orange", "orange", itemDAO);
				createItem("Peach", 2, "a peach", "peach", itemDAO);
				createItem("Pumpkin", 3, "a pumpkin", "pumpkin", itemDAO);
				createItem("Walnut", 3, "a walnut", "walnut", itemDAO);
				createItem("Watermelon", 3, "a watermelon", "watermelon", itemDAO);
			}
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	private byte[] streamToBytes(InputStream is) throws IOException {
    	BufferedInputStream bis = new BufferedInputStream(is);
        try {
        	ByteArrayOutputStream baos = new ByteArrayOutputStream();
        	int b =  bis.read();
        	while (b != -1) {
        		baos.write(b);
        		b = bis.read();
        	}

        	return baos.toByteArray();
		} finally {
			try { bis.close(); } catch (IOException e) { /* Ignore */ }
        }
    }
	
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nextPage = performTheAction(request);
        sendToNextPage(nextPage,request,response);
    }
    
    /*
     * Extracts the requested action and (depending on whether the user is logged in)
     * perform it (or make the user login).
     * @param request
     * @return the next page (the view)
     */
    private String performTheAction(HttpServletRequest request) {
        String      servletPath = request.getServletPath();
        String      action = getActionName(servletPath);

//        if (user == null) {
//        	// If the user hasn't logged in, so login is the only option
//			return Action.perform("login.do",request);
//        }
        
        if (action.equals("welcome")) {
        	// User is logged in, but at the root of our web app
			return Action.perform("displayItems.do",request);
        }
        
      	// Let the logged in user run his chosen action
		return Action.perform(action,request);
    }
    
    /*
     * If nextPage is null, send back 404
     * If nextPage ends with ".do", redirect to this page.
     * If nextPage ends with ".jsp", dispatch (forward) to the page (the view)
     *    This is the common case
     */
    private void sendToNextPage(String nextPage, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	if (nextPage == null) {
    		response.sendError(HttpServletResponse.SC_NOT_FOUND,request.getServletPath());
    		return;
    	}
    	
    	if (nextPage.endsWith(".do")) {
			response.sendRedirect(nextPage);
			return;
    	}
    	
    	if (nextPage.endsWith(".jsp")) {
	   		RequestDispatcher d = request.getRequestDispatcher("WEB-INF/" + nextPage);
	   		d.forward(request,response);
	   		return;
    	}
    	
    	if (nextPage.equals("image")) {
	   		RequestDispatcher d = request.getRequestDispatcher(nextPage);
	   		d.forward(request,response);
	   		return;
    	}
    	
    	throw new ServletException(Controller.class.getName()+".sendToNextPage(\"" + nextPage + "\"): invalid extension.");
    }

	/*
	 * Returns the path component after the last slash removing any "extension"
	 * if present.
	 */
    private String getActionName(String path) {
    	// We're guaranteed that the path will start with a slash
        int slash = path.lastIndexOf('/');
        return path.substring(slash+1);
    }
    
    private void createItem(String title, int userId, String description, String path, ItemDAO itemDAO) throws IOException, RollbackException {
    	 Date date = new Date();
         SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Item item = new Item();
		item.setTitle(title);
		item.setDescription("We have " + description + " to be sold.");
		item.setListingDate(df.format(date));
		item.setPrice(3000);
		item.setUserId(userId);
		InputStream ins = getServletContext().getResourceAsStream("WEB-INF/images/"+ path +".jpg");
		byte[] imageBytes = streamToBytes(ins);
		item.setImageType("image/jpeg");
		item.setImageBytes(imageBytes);
		itemDAO.create(item);
    }
}
