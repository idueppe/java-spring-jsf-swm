package de.swm.speedauction.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import de.swm.auction.services.ProductService;

@WebServlet(name = "AddProductServlet", urlPatterns = {"/add"})
public class AddProductServlet extends HttpServlet
{

	private static final long serialVersionUID = 1L;

	private ProductService productService;

	@Override
	public void init(ServletConfig config) throws ServletException
	{
		super.init(config);
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		productService = context.getBean("productService", ProductService.class);
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		out.println("<html><body><h3>Add Product</h3>");

		out.print("<form method=\"post\">");
		out.print("<input type=\"text\" name=\"title\" />");
		out.print("<input type=\"text\" name=\"description\" />");
		out.print("<input type=\"submit\" value=\"add\"/>");
		out.print("</form>");

		out.print("<br/><a href=\"products\">Übersicht</a>");
		out.println("</body></html>");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		String title = req.getParameter("title");
		String description = req.getParameter("description");
		Long productId = productService.registerProduct(title, description);
		
		String location = "product?id="+productId;
		resp.sendRedirect(location);
	}
	
	

}
