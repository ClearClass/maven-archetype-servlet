package $package;

import java.io.*; 
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/servletPath")
public class MainServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest reqs, HttpServletResponse resp) 
			throws ServletException, IOException {
		resp.setContentType("text/html"); 
		PrintStream ps = new PrintStream(resp.getOutputStream(), true, "cp1251");
		ps.println("<h2>Hello, Сервлет!</h2>");
		ps.close();
	} 
}