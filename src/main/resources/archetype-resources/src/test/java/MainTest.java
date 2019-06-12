package $package;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/applicationContext.xml")
public class MainTest {
	@Autowired
	JdbcTemplate jdbcTemplate;

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