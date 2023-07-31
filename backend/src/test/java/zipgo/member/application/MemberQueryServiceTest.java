package zipgo.member.application;

import static org.assertj.core.api.Assertions.assertThat;
import static zipgo.member.domain.fixture.MemberFixture.식별자_없는_멤버;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import zipgo.member.domain.Member;
import zipgo.member.domain.repository.MemberRepository;

@Transactional
@SpringBootTest
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MemberQueryServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberQueryService memberQueryService;

    @Test
    void 문자_id로_회원을_찾을_수_있다() {
        // given
        Member 멤버 = 식별자_없는_멤버();
        memberRepository.save(멤버);

        // when
        Member 찾은_멤버 = memberQueryService.findById(1L);

        // then
        assertThat(찾은_멤버).isEqualTo(멤버);
    }

}