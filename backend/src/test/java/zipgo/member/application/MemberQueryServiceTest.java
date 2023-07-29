package zipgo.member.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static zipgo.member.domain.fixture.MemberFixture.멤버_생성;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import zipgo.member.domain.Member;
import zipgo.member.domain.repository.MemberRepository;

import java.util.Optional;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@ExtendWith(MockitoExtension.class)
class MemberQueryServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberQueryService memberQueryService;

    @Test
    void 사용자를_저장할_수_있다() {
        // given
        Member 멤버 = 멤버_생성();
        when(memberRepository.findByEmail("이메일"))
                .thenReturn(Optional.ofNullable(멤버));

        // when
        Optional<Member> 조회한_멤버 = memberQueryService.findByEmail("이메일");

        // then
        assertThat(조회한_멤버).isNotNull();
    }

    @Test
    void 문자_id로_회원을_찾을_수_있다() {
        // given
        Member 멤버 = 멤버_생성();
        when(memberRepository.getById(1L))
                .thenReturn(멤버);

        // when
        Member 찾은_멤버 = memberQueryService.findById("1");

        // then
        assertThat(찾은_멤버).isEqualTo(멤버);
    }

}
