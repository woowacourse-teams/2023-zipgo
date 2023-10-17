package zipgo.common.acceptance;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import zipgo.admin.application.AdminQueryService;
import zipgo.admin.application.AdminService;
import zipgo.admin.presentation.AdminController;
import zipgo.aspect.QueryCounter;
import zipgo.auth.application.AuthServiceFacade;
import zipgo.auth.presentation.AuthController;
import zipgo.auth.presentation.AuthInterceptor;
import zipgo.auth.presentation.JwtMandatoryArgumentResolver;
import zipgo.auth.support.JwtProvider;
import zipgo.auth.support.RefreshTokenCookieProvider;
import zipgo.image.application.ImageService;
import zipgo.image.presentaion.ImageController;
import zipgo.member.application.MemberQueryService;
import zipgo.pet.application.PetQueryService;

@AutoConfigureRestDocs
@ExtendWith(SpringExtension.class)
@SuppressWarnings("NonAsciiCharacters")
@WebMvcTest(
        controllers = {
                AdminController.class,
                AuthController.class,
                ImageController.class
        }
)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public abstract class MockMvcTest {

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    protected ImageService imageService;

    @MockBean
    protected AdminService adminService;

    @MockBean
    protected AdminQueryService adminQueryService;

    @MockBean
    protected JwtProvider jwtProvider;

    @MockBean
    protected AuthInterceptor authInterceptor;

    @MockBean
    protected JwtMandatoryArgumentResolver argumentResolver;

    @MockBean
    protected MemberQueryService memberQueryService;

    @MockBean
    protected PetQueryService petQueryService;

    @MockBean
    protected AuthServiceFacade authServiceFacade;

    @MockBean
    protected RefreshTokenCookieProvider refreshTokenCookieProvider;

    @MockBean
    protected QueryCounter queryCounter;

}
