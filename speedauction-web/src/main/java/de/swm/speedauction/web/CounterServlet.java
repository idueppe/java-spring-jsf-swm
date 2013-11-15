package de.swm.speedauction.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "CounterServlet", urlPatterns = {"/counter"})
public class CounterServlet extends HttpServlet
{

	private static final long serialVersionUID = 1L;
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		out.println("<html><body><h3>Count</h3>");

		HttpSession session = req.getSession(true);
		Long sessionCount = (Long) session.getAttribute("counter");
		sessionCount = (sessionCount == null) ? 0l : ++sessionCount; 
		session.setAttribute("counter", sessionCount);
		
		Long applicationCount = (Long) getServletContext().getAttribute("counter");
		applicationCount = (applicationCount == null) ? 0l : ++applicationCount; 
		getServletContext().setAttribute("counter", applicationCount);

		out.print("<br/>"+sessionCount);
		out.print("<br/>"+applicationCount);
		

		out.print("<br/><a href=\"counter\">refresh</a>");
		out.println("</body></html>");
	}


}
