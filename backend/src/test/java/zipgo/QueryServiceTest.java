package zipgo;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional(readOnly = true)
@SuppressWarnings("NonAsciiCharacters")
@Sql(scripts = {"classpath:truncate.sql"})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class QueryServiceTest {

}
