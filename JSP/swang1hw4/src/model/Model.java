package model;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;

public class Model {
	private ItemDAO itemDAO;
	private UserDAO userDAO;

	public Model(ServletConfig config) throws ServletException {
		try {
			String jdbcDriver = config.getInitParameter("jdbcDriverName");
			String jdbcURL    = config.getInitParameter("jdbcURL");
			
			ConnectionPool pool = new ConnectionPool(jdbcDriver,jdbcURL);
			
			userDAO  = new UserDAO(pool, "swang1_user");
			itemDAO = new ItemDAO(pool, "swang1_item");
		} catch (DAOException e) {
			throw new ServletException(e);
		}
	}
	
	public ItemDAO getItemDAO()  { return itemDAO; }
	public UserDAO getUserDAO()  { return userDAO; }
}