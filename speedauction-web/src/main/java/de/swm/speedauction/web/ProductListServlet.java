package de.swm.speedauction.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import de.swm.auction.model.Product;
import de.swm.auction.services.ProductService;


@WebServlet(name="ProductListServlet", urlPatterns={"/products","/produkte"})
public class ProductListServlet extends HttpServlet
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
		System.out.println(System.getProperty("catalina_base"));
		
		resp.setContentType("text/html;charset=UTF-8");
		resp.addHeader("speedauction", "super anwendung");
		PrintWriter out = resp.getWriter();
		
		out.println("<html><body><h3>ProductList</h3><ul>");
		List<? extends Product> allProducts = productService.findAll();
		for (Product product : allProducts)
		{
			out.println("<li>");
			out.println("<a href=\"product?id="+product.getId()+"\">");
			out.println(product.getTitle());
			out.println("</a>");
			out.println("</li>");
		}
		out.println("</ul>");
		out.println("<br/><small><a href=\"add\">Add Product</a></small>");
		out.println("</body></html>");
	}

}


