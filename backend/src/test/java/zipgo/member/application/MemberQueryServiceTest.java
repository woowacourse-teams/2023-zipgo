package zipgo.member.application;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import zipgo.common.service.ServiceTest;
import zipgo.member.domain.Member;
import zipgo.member.domain.repository.MemberRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static zipgo.member.domain.fixture.MemberFixture.식별자_없는_멤버;

class MemberQueryServiceTest extends ServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberQueryService memberQueryService;

    @Test
    void 문자_id로_회원을_찾을_수_있다() {
        // given
        Member 멤버 = 식별자_없는_멤버();
        Member 저장된_멤버 = memberRepository.save(멤버);

        // when
        Member 찾은_멤버 = memberQueryService.findById(저장된_멤버.getId());

        // then
        assertThat(찾은_멤버).isEqualTo(저장된_멤버);
    }

}
