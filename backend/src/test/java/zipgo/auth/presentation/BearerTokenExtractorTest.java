package zipgo.auth.presentation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import zipgo.auth.exception.AuthException;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class BearerTokenExtractorTest {

    @Test
    void 요청_헤더의_Bearer_토큰을_추출할_수_있다() {
        // given
        MockHttpServletRequest 요청 = new MockHttpServletRequest();
        요청.addHeader("Authorization", "Bearer a1.b2.c3");

        // when
        String 토큰 = BearerTokenExtractor.extract(요청);

        // then
        assertThat(토큰).isEqualTo("a1.b2.c3");
    }

    @Test
    void 요청이_들어오지_않는다면_예외가_발생한다() {
        // given
        MockHttpServletRequest 요청 = new MockHttpServletRequest();

        // expect
        assertThatThrownBy(() -> BearerTokenExtractor.extract(요청))
                .isInstanceOf(AuthException.class)
                .hasMessageContaining("인증이 필요합니다");
    }

    @Test
    void 토큰_형식이_유효하지_않으면_예외가_발생한다() {
        // given
        MockHttpServletRequest 요청 = new MockHttpServletRequest();
        요청.addHeader("Authorization", "Bearer 내맘대로토큰");

        // expect
        assertThatThrownBy(() -> BearerTokenExtractor.extract(요청))
                .isInstanceOf(AuthException.class)
                .hasMessageContaining("형식");
    }

}
