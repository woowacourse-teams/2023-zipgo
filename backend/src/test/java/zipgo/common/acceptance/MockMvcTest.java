package zipgo.common.acceptance;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import zipgo.aspect.QueryCounter;
import zipgo.auth.support.JwtProvider;

@WebMvcTest
@AutoConfigureRestDocs
@ExtendWith(SpringExtension.class)
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class MockMvcTest {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    protected JwtProvider jwtProvider;

    @MockBean
    protected QueryCounter queryCounter;

}
