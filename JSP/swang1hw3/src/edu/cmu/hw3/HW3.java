/*
 * File:   HW3.java
 * Author: cserspring
 * ID:     swang1
 * Date:   02/09/2013
 * 
 * This a servlet to process all the requests.
 * Used some code from the instructor's lecture examples.
 */
package edu.cmu.hw3;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.RollbackException;
import edu.cmu.hw3.dao.*;
import edu.cmu.hw3.databean.*;
import edu.cmu.hw3.formbean.*;

public class HW3 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ItemDAO itemDAO;
	private UserDAO userDAO;

	public void init() throws ServletException {
		String jdbcDriverName = getInitParameter("jdbcDriver");
		String jdbcURL = getInitParameter("jdbcURL");
		try {
			ConnectionPool cp = new ConnectionPool(jdbcDriverName, jdbcURL);
			userDAO = new UserDAO(cp, "swang1_user");
			itemDAO = new ItemDAO(cp, "swang1_item");
		} catch (DAOException e) {
			throw new ServletException(e);
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute("user") == null) {
			try {
				loginRegister(request, response);
			} catch (RollbackException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			manageList(request, response);
		}
	}

	public void loginRegister(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			RollbackException {
		LoginForm form = new LoginForm(request);
		if (!form.isPresent() || form.getButton().equals("Login"))
			login(request, response);
		else if (form.getButton().equals("Register")
				|| form.getButton().equals("Submit"))
			register(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<String> errors = new ArrayList<String>();

		LoginForm form = new LoginForm(request);

		if (!form.isPresent() || !form.getButton().equals("Login")) {
			outputLoginPage(response, form, null);
			return;
		}

		errors.addAll(form.getValidationErrors());
		if (errors.size() != 0) {
			outputLoginPage(response, form, errors);
			return;
		}

		try {
			User user;
			user = userDAO.read(form.getEmail());

			if (user == null) {
				errors.add("No such user");
				outputLoginPage(response, form, errors);
				return;
			}

			if (!form.getPassword().equals(user.getPassword())) {
				errors.add("Incorrect password");
				outputLoginPage(response, form, errors);
				return;
			}

			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			manageList(request, response);
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			outputLoginPage(response, form, errors);
		}
	}

	private void register(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			RollbackException {
		List<String> errors = new ArrayList<String>();

		RegisterForm form = new RegisterForm(request);

		if (!form.isPresent()) {
			outputRegisterPage(response, form, null);
			return;
		}

		errors.addAll(form.getValidationErrors());
		if (errors.size() != 0) {
			outputRegisterPage(response, form, errors);
			return;
		}

		try {
			if (userDAO.read(form.getEmail()) != null) {
				errors.add("This Email account has been registed");
				outputRegisterPage(response, form, errors);
			}
			User user = new User();
			user.setEmail(form.getEmail());
			user.setFirstname(form.getFirstname());
			user.setLastname(form.getLastname());
			user.setPassword(form.getPassword());
			user.setUsername(form.getUsername());
			userDAO.create(user);
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			outputRegisterPage(response, form, errors);
		}
		login(request, response);
	}

	private void manageList(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// Look at the what parameter to see what we're doing to the list
		String what = request.getParameter("what");

		String button = request.getParameter("button");
		if (button != null && button.equals("Logout")) {

			request.getSession().setAttribute("user", null);
			outputLoginPage(response, null, null);
			return;
		}

		if (what == null) {
			// No change to list requested
			outputItems(request, response);
			return;
		}

		if (what.equals("Add")) {
			processAdd(request, response, true);
			return;
		}

		outputItems(request, response, "No such operation: " + what);
	}

	private void processAdd(HttpServletRequest request,
			HttpServletResponse response, boolean addToTop)
			throws ServletException, IOException {
		List<String> errors = new ArrayList<String>();

		AddItemForm form = new AddItemForm(request);

		errors.addAll(form.getValidationErrors());
		if (errors.size() > 0) {
			outputItems(request, response, errors);
			return;
		}

		try {
			Item item = new Item();
			item.setUserId(((User) request.getSession().getAttribute("user"))
					.getId());
			item.setListingDate(form.getListingDate());
			item.setDescription(form.getDescription());
			item.setPrice(form.getPrice());

			itemDAO.create(item);
			outputItems(request, response, "");

		} catch (RollbackException e) {
			errors.add(e.getMessage());
			outputItems(request, response, errors);
		}
	}

	private void generateHead(PrintWriter out) {
		out.println("  <head>");
		out.println("    <meta http-equiv=\"cache-control\" content=\"no-cache\">");
		out.println("    <meta http-equiv=\"pragma\" content=\"no-cache\">");
		out.println("    <meta http-equiv=\"expires\" content=\"0\">");
		out.println("    <title>To Do List Login</title>");
		out.println("  </head>");
	}

	private void outputLoginPage(HttpServletResponse response, LoginForm form,
			List<String> errors) throws IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		out.println("<html>");

		generateHead(out);

		out.println("<body>");
		out.println("<center>");

		// Generate an HTML <form> to get data from the user
		out.println("<form method=\"POST\">");
		out.println("    <table/>");
		out.println("        <tr>");
		out.println("            <td style=\"font-size: x-large\">Email:</td>");
		out.println("            <td>");
		out.println("                <input type=\"text\" name=\"loginEmail\"");
		if (form != null && form.getEmail() != null) {
			out.println("                    value=\"" + form.getEmail() + "\"");
		}
		out.println("                />");
		out.println("            <td>");
		out.println("        </tr>");
		out.println("        <tr>");
		out.println("            <td style=\"font-size: x-large\">Password:</td>");
		out.println("            <td><input type=\"password\" name=\"loginPassword\" /></td>");
		out.println("        </tr>");
		out.println("        <tr>");
		out.println("            <td colspan=\"2\" align=\"center\">");
		out.println("                <input type=\"submit\" name=\"button\" value=\"Login\" /></br></br></br>");
		out.println("                No account yet, click ");
		out.println("                <input type=\"submit\" name=\"button\" value=\"Register\" />");
		out.println("                to register.");
		out.println("            </td>");
		out.println("        </tr>");
		out.println("    </table>");
		out.println("</form>");
		if (errors != null && errors.size() > 0) {
			for (String error : errors) {
				out.println("<p style=\"font-size: large; color: red\">");
				out.println(error);
				out.println("</p>");
			}
		}
		out.println("</center>");
		out.println("</body>");
		out.println("</html>");
	}

	private void outputRegisterPage(HttpServletResponse response,
			RegisterForm form, List<String> errors) throws IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		out.println("<html>");

		generateHead(out);

		out.println("<body>");
		out.println("<center>");

		// Generate an HTML <form> to get data from the user
		out.println("<form method=\"POST\">");
		out.println("    <table/>");
		out.println("        <tr>");
		out.println("            <td style=\"font-size: x-large\">User Name:</td>");
		out.println("            <td>");
		out.println("                <input type=\"text\" name=\"username\"");
		if (form != null && form.getUsername() != null) {
			out.println("                    value=\"" + form.getUsername()
					+ "\"");
		}
		out.println("                />");
		out.println("            </td>");
		out.println("        </tr>");

		out.println("        <tr>");
		out.println("            <td style=\"font-size: x-large\">Email:</td>");
		out.println("            <td>");
		out.println("                <input type=\"text\" name=\"email\"");
		if (form != null && form.getEmail() != null) {
			out.println("                    value=\"" + form.getEmail() + "\"");
		}
		out.println("                />");
		out.println("            </td>");
		out.println("        </tr>");

		out.println("        <tr>");
		out.println("            <td style=\"font-size: x-large\">First Name:</td>");
		out.println("            <td>");
		out.println("                <input type=\"text\" name=\"firstname\"");
		if (form != null && form.getFirstname() != null) {
			out.println("                    value=\"" + form.getFirstname()
					+ "\"");
		}
		out.println("                />");
		out.println("            </td>");
		out.println("        </tr>");

		out.println("        <tr>");
		out.println("            <td style=\"font-size: x-large\">Last Name:</td>");
		out.println("            <td>");
		out.println("                <input type=\"text\" name=\"lastname\"");
		if (form != null && form.getLastname() != null) {
			out.println("                    value=\"" + form.getLastname()
					+ "\"");
		}
		out.println("                />");
		out.println("            </td>");
		out.println("        </tr>");

		out.println("        <tr>");
		out.println("            <td style=\"font-size: x-large\">Password:</td>");
		out.println("            <td><input type=\"password\" name=\"password\" /></td>");
		out.println("        </tr>");

		out.println("        <tr>");
		out.println("            <td style=\"font-size: x-large\">Confirm Password:</td>");
		out.println("            <td><input type=\"password\" name=\"confirmPassword\" /></td>");
		out.println("        </tr>");

		out.println("        <tr>");
		out.println("            <td colspan=\"2\" align=\"center\">");
		out.println("                <input type=\"submit\" name=\"button\" value=\"Submit\" />");
		out.println("            </td>");
		out.println("        </tr>");
		out.println("    </table>");
		out.println("</form>");
		if (errors != null && errors.size() > 0) {
			for (String error : errors) {
				out.println("<p style=\"font-size: large; color: red\">");
				out.println(error);
				out.println("</p>");
			}
		}
		out.println("</center>");
		out.println("</body>");
		out.println("</html>");
	}

	private void outputItems(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// Just call the version that takes a List passing an empty List
		List<String> list = new ArrayList<String>();
		outputItems(request, response, list);
	}

	private void outputItems(HttpServletRequest request,
			HttpServletResponse response, String message) throws IOException {
		// Just put the message into a List and call the version that takes a
		// List
		List<String> list = new ArrayList<String>();
		list.add(message);
		outputItems(request, response, list);
	}

	private void outputItems(HttpServletRequest request,
			HttpServletResponse response, List<String> messages)
			throws IOException {
		// Get the list of items to display at the end
		Item[] items;
		try {
			items = itemDAO.getUseItems(((User) request.getSession()
					.getAttribute("user")).getId());
		} catch (RollbackException e) {
			// If there's an access error, add the message to our list of
			// messages
			messages.add(e.getMessage());
			items = new Item[0];
		}

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		out.println("<html>");

		generateHead(out);

		out.println("<body>");
		out.println("<center>");
		User user = (User) request.getSession().getAttribute("user");

		out.println("<form method=\"POST\">");
		out.println("Welcome, " + user.getFirstname() + " "
				+ user.getLastname() + ".    ");
		out.println("<input type=\"submit\" name=\"button\" value=\"Logout\"/>");
		out.println("</form>");
		// Generate an HTML <form> to get data from the user
		out.println("<form method=\"POST\">");
		out.println("    <table>");

		out.println("        <tr>");
		out.println("            <td style=\"font-size: large\">Description:</td>");
		out.println("            <td colspan=\"2\"><input type=\"text\" size=\"40\" name=\"description\"/></td>");
		out.println("        </tr>");

		out.println("        <tr>");
		out.println("            <td style=\"font-size: large\">Price:</td>");
		out.println("            <td colspan=\"2\"><input type=\"text\" size=\"40\" name=\"price\"/></td>");
		out.println("        </tr>");

		out.println("        <tr>");
		out.println("            <td/><td/>");
		out.println("            <td><input type=\"submit\" name=\"what\" value=\"Add\"/></td>");
		out.println("        </tr>");

		out.println("    </table>");
		out.println("</form>");

		for (String message : messages) {
			out.println("<p style=\"font-size: large; color: red\">");
			out.println(message);
			out.println("</p>");
		}
		out.println("<table border=\"1\">");
		out.println("    <tr><th>Listing Date</th><th>Description</th><th>Price</th></tr>");
		for (int i = 0; i < items.length; i++) {
			out.println("    <tr>");
			out.println("        <td>");

			out.println("                <label>" + items[i].getListingDate()
					+ "</label>");

			out.println("        </td>");
			out.println("        <td><label>" + items[i].getDescription()
					+ "</label>");

			out.println("        <td><label>" + items[i].getPrice()
					+ "</label>");
			out.println("    </tr>");
		}
		out.println("</table>");
		out.println("</center>");
		out.println("</body>");
		out.println("</html>");
	}
}
