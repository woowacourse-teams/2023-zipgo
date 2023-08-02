package zipgo.auth.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static zipgo.member.domain.fixture.MemberFixture.식별자_없는_멤버;
import static zipgo.member.domain.fixture.MemberFixture.식별자_있는_멤버;

import java.util.Optional;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;
import zipgo.auth.application.dto.KakaoMemberResponse;
import zipgo.auth.application.dto.OAuthMemberResponse;
import zipgo.auth.util.JwtProvider;
import zipgo.member.domain.Member;
import zipgo.member.domain.repository.MemberRepository;

@Transactional
@SpringBootTest
@ExtendWith(MockitoExtension.class)
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AuthServiceTest {

    @MockBean
    private KakaoOAuthClient oAuthClient;

    @MockBean
    private JwtProvider jwtProvider;

    @MockBean
    private MemberRepository memberRepository;

    @Autowired
    private AuthService authService;

    @Test
    void 기존_멤버의_토큰을_발급한다() {
        // given
        OAuthMemberResponse 카카오_응답 = 카카오_토큰_받기_성공();
        Member 저장된_멤버 = 식별자_있는_멤버();
        when(memberRepository.findByEmail("이메일"))
                .thenReturn(Optional.of(저장된_멤버));
        when(jwtProvider.create(String.valueOf(저장된_멤버.getId())))
                .thenReturn("생성된 토큰");

        // when
        String 토큰 = authService.createToken("코드");

        // then
        assertThat(토큰).isEqualTo("생성된 토큰");
    }

    @Test
    void 새로_가입한_멤버의_토큰을_발급한다() {
        // given
        OAuthMemberResponse 카카오_응답 = 카카오_토큰_받기_성공();
        when(memberRepository.findByEmail("이메일"))
                .thenReturn(Optional.empty());
        Member 멤버 = 식별자_없는_멤버();
        Member 저장된_멤버 = 식별자_있는_멤버();
        when(memberRepository.save(식별자_없는_멤버()))
                .thenReturn(저장된_멤버);

        when(jwtProvider.create("1"))
                .thenReturn("생성된 토큰");

        // when
        String 토큰 = authService.createToken("코드");

        // then
        assertThat(토큰).isEqualTo("생성된 토큰");
    }

    private OAuthMemberResponse 카카오_토큰_받기_성공() {
        when(oAuthClient.getAccessToken("코드"))
                .thenReturn("토큰");

        OAuthMemberResponse oAuthMemberResponse = 카카오_응답();
        when(oAuthClient.getMember("토큰"))
                .thenReturn(oAuthMemberResponse);

        return oAuthMemberResponse;
    }

    private KakaoMemberResponse 카카오_응답() {
        return KakaoMemberResponse.builder().kakaoAccount(KakaoMemberResponse.KakaoAccount.builder()
                .email("이메일")
                .profile(KakaoMemberResponse.Profile.builder()
                        .nickname("이름")
                        .picture("사진")
                        .build())
                .build()).build();
    }

}
