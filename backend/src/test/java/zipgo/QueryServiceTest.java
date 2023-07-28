package zipgo;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional(readOnly = true)
@Sql(scripts = {"classpath:truncate.sql"})
public class QueryServiceTest {

}
