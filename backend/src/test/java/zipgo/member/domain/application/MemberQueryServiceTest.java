package zipgo.member.domain.application;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import zipgo.member.domain.Member;
import zipgo.member.domain.repository.MemberRepository;
import zipgo.petfood.domain.repository.KeywordRepository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

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
        Member member = Member.builder()
                .email("이메일")
                .profileImgUrl("사진")
                .name("이름")
                .build();
        when(memberRepository.save(member))
                .thenReturn(Member.builder().id(1L).email("이메일").profileImgUrl("사진").name("이름").build());

        // when
        Member 저장된_멤버 = memberQueryService.save(member);

        // then
        assertAll(
                () -> assertThat(저장된_멤버.getEmail()).isEqualTo("이메일"),
                () ->assertThat(저장된_멤버.getProfileImgUrl()).isEqualTo("사진"),
                () ->assertThat(저장된_멤버.getName()).isEqualTo("이름"),
                () ->assertThat(저장된_멤버.getId()).isNotNull()
        );
    }

}
