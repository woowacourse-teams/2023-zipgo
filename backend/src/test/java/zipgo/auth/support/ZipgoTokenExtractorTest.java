package zipgo.auth.support;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import zipgo.auth.exception.TokenInvalidException;
import zipgo.auth.exception.TokenMissingException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ZipgoTokenExtractorTest {

    @Test
    void 요청_헤더의_Zipgo_토큰을_추출할_수_있다() {
        // given
        var 요청 = new MockHttpServletRequest();
        요청.addHeader("Refresh", "Zipgo a1.b2.c3");

        // when
        String 토큰 = ZipgoTokenExtractor.extract(요청);

        // then
        assertThat(토큰).isEqualTo("a1.b2.c3");
    }

    @Test
    void 토큰_형식이_유효하지_않으면_예외가_발생한다() {
        // given
        var 요청 = new MockHttpServletRequest();
        요청.addHeader("Refresh", "Zipgo 내맘대로토큰");

        // expect
        assertThatThrownBy(() -> ZipgoTokenExtractor.extract(요청))
                .isInstanceOf(TokenInvalidException.class)
                .hasMessageContaining("잘못된 토큰입니다. 올바른 토큰으로 다시 시도해주세요.");
    }

    @Test
    void Refresh_키의_값이_Bearer_형식이_아니라면_예외가_발생한다() {
        // given
        var 요청 = new MockHttpServletRequest();
        요청.addHeader("Refresh", "Basic aaaaa:bbbb");

        // expect
        assertThatThrownBy(() -> ZipgoTokenExtractor.extract(요청))
                .isInstanceOf(TokenInvalidException.class)
                .hasMessageContaining("잘못된 토큰입니다. 올바른 토큰으로 다시 시도해주세요.");
    }

    @Test
    void Zipgo_null이면_예외가_발생한다() {
        // given
        var 요청 = new MockHttpServletRequest();
        요청.addHeader("Refresh", "Zipgo null");

        // expect
        assertThatThrownBy(() -> ZipgoTokenExtractor.extract(요청))
                .isInstanceOf(TokenInvalidException.class)
                .hasMessageContaining("잘못된 토큰입니다. 올바른 토큰으로 다시 시도해주세요.");
    }

    @Test
    void 토큰이_없으면_MissingException이_발생한다() {
        //given
        var 요청 = new MockHttpServletRequest();

        //expect
        assertThatThrownBy(() -> ZipgoTokenExtractor.extract(요청))
                .isInstanceOf(TokenMissingException.class)
                .hasMessageContaining("토큰이 필요합니다.");
    }

}
