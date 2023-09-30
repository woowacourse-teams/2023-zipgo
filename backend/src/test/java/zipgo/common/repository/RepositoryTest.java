package zipgo.common.repository;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import zipgo.common.config.JpaConfig;
import zipgo.common.config.QueryDslTestConfig;


@DataJpaTest
@Import({
        JpaConfig.class,
        QueryDslTestConfig.class
})
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public abstract class RepositoryTest {

}
