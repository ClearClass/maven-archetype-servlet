package $package;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/applicationContext.xml")
public class MainTest {
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired @Qualifier("h2")
	DataSource h2;
	
	@BeforeClass
	public static void contextSetup() throws NamingException {
		SimpleNamingContextBuilder builder = SimpleNamingContextBuilder.emptyActivatedContextBuilder();
		DriverManagerDataSource dataSource = new DriverManagerDataSource("jdbc:postgresql://127.0.0.1:5432/db1", "postgres", "post");
		builder.bind("java:comp/env/jdbc/postgres", dataSource);
		builder.activate();
	}
	
	@Before
	public void init(){
		jdbcTemplate.setDataSource(h2);
	}

	@Test
	public void test1() {
		String query = "INSERT INTO table1(word, num) VALUES(?,?);";
		List<Object[]> queryData = new ArrayList<>();
		queryData.add(new Object[] { "Пчела", 11 });
		queryData.add(new Object[] { "Утка", 16 });
		jdbcTemplate.batchUpdate(query, queryData);
		int rows = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM table1", Integer.class);
		assertEquals(4, rows);
		List<Map<String, Object>> map = jdbcTemplate.queryForList("SELECT * FROM table1");
		System.out.println(map);
	}
}