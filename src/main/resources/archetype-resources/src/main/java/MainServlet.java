package $package;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.DataSource;
import javax.servlet.annotation.*;

@WebServlet("/servletPath")
public class MainServlet extends HttpServlet {
	@Resource(name = "jdbc/postgres")
	DataSource ds;
   
	@Override
	public void doGet(HttpServletRequest reqs, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		try(Connection conn = ds.getConnection()){
			reqs.setAttribute("conn", conn.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		getServletContext().getRequestDispatcher("/WEB-INF/jsp/foo.jsp").forward(reqs, resp);
	} 
}