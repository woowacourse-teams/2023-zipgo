package zipgo.petfood.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static zipgo.petfood.domain.fixture.PetFoodFixture.키워드_없이_식품_초기화;
import static zipgo.petfood.domain.fixture.PetFoodFixture.키워드_있는_식품_초기화;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import zipgo.petfood.domain.Keyword;
import zipgo.petfood.domain.PetFood;

@DataJpaTest
@DisplayNameGeneration(ReplaceUnderscores.class)
class PetFoodRepositoryTest {

    @Autowired
    private PetFoodRepository petFoodRepository;

    @Autowired
    KeywordRepository keywordRepository;

    @Test
    void 모든_식품을_조회할_수_있다() {
        // given
        PetFood 반려동물_식품_1 = petFoodRepository.save(키워드_없이_식품_초기화());
        PetFood 반려동물_식품_2 = petFoodRepository.save(키워드_없이_식품_초기화());

        // when
        List<PetFood> petFoods = petFoodRepository.findAll();

        // then
        assertThat(petFoods).contains(반려동물_식품_1, 반려동물_식품_2);
    }

    @Test
    void 키워드로_식품을_조회할_수_있다() {
        // given
        PetFood 키워드가_없는_식품 = petFoodRepository.save(키워드_없이_식품_초기화());
        PetFood 키워드가_있는_식품 = 키워드있는_식품_생성하기();

        // when
        List<PetFood> 조회된_식품 = petFoodRepository.findByKeyword(키워드가_있는_식품.getKeyword());

        // then
        assertThat(조회된_식품).contains(키워드가_있는_식품);
        assertThat(조회된_식품).doesNotContain(키워드가_없는_식품);
    }

    private PetFood 키워드있는_식품_생성하기() {
        Keyword 키워드 = keywordRepository.findAll().get(0);
        return petFoodRepository.save(키워드_있는_식품_초기화(키워드));
    }

    @Test
    void 아이디로_식품을_조회할_수_있다() {
        //given
        PetFood 테스트_식품 = 키워드_없이_식품_초기화();
        Long 아이디 = petFoodRepository.save(테스트_식품).getId();

        //when
        Optional<PetFood> optional_식품 = petFoodRepository.findById(아이디);

        //then
        assertAll(() -> {
            assertThat(optional_식품).isNotEmpty();

            PetFood 조회한_식품 = optional_식품.orElseThrow();
            assertThat(조회한_식품).isEqualTo(테스트_식품);
        });
    }

    @ParameterizedTest
    @ValueSource(longs = {-1L, 999999999999999L, 0L})
    void 존재하지_않는_아이디로_조회하면_빈_옵셔널을_반환한다(Long 존재하지_않는_아이디) {
        //when
        Optional<PetFood> optional_식품 = petFoodRepository.findById(존재하지_않는_아이디);

        //then
        assertThat(optional_식품).isEmpty();
    }

}
