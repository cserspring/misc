package view;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import databean.Item;

@SuppressWarnings("serial")
public class ImageServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	Item item = (Item) request.getAttribute("photo");

        if (item == null) {
        	response.sendError(HttpServletResponse.SC_NOT_FOUND);
        	return;
        }
        response.setContentType(item.getImageType());

        ServletOutputStream out = response.getOutputStream();
       
        out.write(item.getImageBytes());
    }
}
