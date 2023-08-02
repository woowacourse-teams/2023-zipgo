package zipgo.common.service;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@Sql(scripts = {"classpath:truncate.sql"})
public class ServiceTest {

}
