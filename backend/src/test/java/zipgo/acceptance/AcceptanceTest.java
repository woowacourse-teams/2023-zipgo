package zipgo.acceptance;

import static com.epages.restdocs.apispec.Schema.schema;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.documentationConfiguration;

import com.epages.restdocs.apispec.Schema;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import zipgo.auth.util.JwtProvider;

@SuppressWarnings("NonAsciiCharacters")
@Sql(scripts = {"classpath:truncate.sql", "classpath:data.sql"})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AcceptanceTest {

    protected RequestSpecification spec;
    protected Schema 에러_응답_형식 = schema("ErrorResponse");

    @LocalServerPort
    private int port;

    @Autowired
    protected JwtProvider jwtProvider;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @BeforeEach
    void setUpRestDocs(RestDocumentationContextProvider provider) {
        this.spec = new RequestSpecBuilder()
                .setPort(port)
                .addFilter(documentationConfiguration(provider)
                        .operationPreprocessors()
                        .withRequestDefaults(prettyPrint())
                        .withResponseDefaults(prettyPrint())
                ).build();
    }

}
