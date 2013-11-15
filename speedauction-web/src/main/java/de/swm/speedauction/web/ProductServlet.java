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

import de.swm.auction.exceptions.ProductNotFoundException;
import de.swm.auction.model.Product;
import de.swm.auction.model.ProductDetails;
import de.swm.auction.services.ProductService;


@WebServlet(name="ProductServlet", urlPatterns={"/product"})
public class ProductServlet extends HttpServlet
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
		try
		{
			Long productId = Long.parseLong(req.getParameter("id"));
			ProductDetails product = productService.findDetails(new Product(productId));
			
			resp.setContentType("text/html;charset=UTF-8");
			PrintWriter out = resp.getWriter();
			out.println("<html><body><h3>Product</h3>");
			
			
			out.print("<br/>"+product.getTitle());
			out.print("<br/>"+product.getDescription());
		
			out.print("<br/><a href=\"products\">Übersicht</a>");
			out.println("</body></html>");
		} catch (ProductNotFoundException e)
		{
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

}


