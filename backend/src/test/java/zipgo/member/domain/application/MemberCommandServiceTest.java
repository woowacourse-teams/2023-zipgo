package zipgo.member.domain.application;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import zipgo.member.domain.Member;
import zipgo.member.domain.repository.MemberRepository;

import java.util.Optional;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@ExtendWith(MockitoExtension.class)
class MemberCommandServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberCommandService memberCommandService;

    @Test
    void 사용자를_저장할_수_있다() {
        // given
        Member member = Member.builder()
                .email("이메일")
                .profileImgUrl("사진")
                .name("이름")
                .build();
        when(memberRepository.findByEmail("이메일"))
                .thenReturn(Optional.ofNullable(Member.builder().id(1L).email("이메일").profileImgUrl("사진").name("이름").build()));

        // when
        Optional<Member> 조회한_멤버 = memberCommandService.findByEmail("이메일");

        // then
        assertThat(조회한_멤버).isNotNull();
    }
}
