package zipgo.member.domain.repository;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;
import static zipgo.member.domain.fixture.MemberFixture.멤버_생성;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import zipgo.member.domain.Member;
import zipgo.petfood.domain.PetFood;

@DataJpaTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 아이디로_멤버를_찾을_수_있다() {
        // given
        Member 멤버 = 멤버_생성();
        Member 저장된_멤버 = memberRepository.save(멤버);
        Long 식별자 = 저장된_멤버.getId();

        // when
        Member 찾은_멤버 = memberRepository.getById(식별자);

        //then
        assertThat(식별자).isEqualTo(찾은_멤버.getId());
    }

}
