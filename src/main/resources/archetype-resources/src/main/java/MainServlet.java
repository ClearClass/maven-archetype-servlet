package $package;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.web.context.support.XmlWebApplicationContext;

@WebServlet("/servletPath")
public class MainServlet extends HttpServlet {
	XmlWebApplicationContext ctx = new XmlWebApplicationContext();
	JdbcTemplate jdbcTemplate;

	@Override
	public void init(ServletConfig conf) throws ServletException {
		super.init(conf);
		ctx.setServletContext(conf.getServletContext());
		ctx.refresh();
		jdbcTemplate = ctx.getBean(JdbcTemplate.class);
		ClassPathResource script = new ClassPathResource("init.sql");
		ResourceDatabasePopulator initializer = new ResourceDatabasePopulator(true, true, "UTF-8", script);
		initializer.execute(jdbcTemplate.getDataSource());
	}

	@Override
	public void doGet(HttpServletRequest reqs, HttpServletResponse resp) throws ServletException, IOException {
		String query = "INSERT INTO table1(word, num) VALUES(?,?);";
		List<Object[]> queryData = new ArrayList<>();
		queryData.add(new Object[] {"Пчела", 11});
		queryData.add(new Object[] {"Утка", 16});
		jdbcTemplate.batchUpdate(query, queryData);
		List<Map<String, Object>> data = jdbcTemplate.queryForList("SELECT * FROM table1");
		reqs.setAttribute("data", data);
		getServletContext().getRequestDispatcher("/WEB-INF/jsp/foo.jsp").forward(reqs, resp);
	}

	@Override
	public void destroy() {
		ctx.close();
	}
}