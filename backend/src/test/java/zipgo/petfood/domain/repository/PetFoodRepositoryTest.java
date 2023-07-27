package zipgo.petfood.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static zipgo.petfood.domain.fixture.PetFoodFixture.식품_초기화;
import static zipgo.petfood.domain.fixture.PetFoodFixture.키워드_없이_식품_초기화;
import static zipgo.petfood.domain.fixture.PetFoodFixture.키워드_있는_식품_초기화;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import zipgo.brand.domain.Brand;
import zipgo.brand.domain.repository.BrandRepository;
import zipgo.petfood.domain.Keyword;
import zipgo.petfood.domain.PetFood;

@DataJpaTest
@DisplayNameGeneration(ReplaceUnderscores.class)
class PetFoodRepositoryTest {

    @Autowired
    private PetFoodRepository petFoodRepository;

    @Autowired
    private KeywordRepository keywordRepository;

    @Autowired
    private BrandRepository brandRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private Brand 브랜드_조회하기() {
        return brandRepository.findAll().get(0);
    }

    @Test
    void 모든_식품을_조회할_수_있다() {
        // given
        Brand 브랜드 = 브랜드_조회하기();
        PetFood 반려동물_식품_1 = petFoodRepository.save(키워드_없이_식품_초기화(브랜드));
        PetFood 반려동물_식품_2 = petFoodRepository.save(키워드_없이_식품_초기화(브랜드));

        // when
        List<PetFood> petFoods = petFoodRepository.findAll();

        // then
        assertThat(petFoods).contains(반려동물_식품_1, 반려동물_식품_2);
    }

    @Test
    void 키워드로_식품을_조회할_수_있다() {
        // given
        PetFood 키워드가_없는_식품 = petFoodRepository.save(키워드_없이_식품_초기화(브랜드_조회하기()));
        PetFood 키워드가_있는_식품 = 키워드있는_식품_생성하기();

        // when
        List<PetFood> 조회된_식품 = petFoodRepository.findByKeyword(키워드가_있는_식품.getKeyword());

        // then
        assertThat(조회된_식품).contains(키워드가_있는_식품);
        assertThat(조회된_식품).doesNotContain(키워드가_없는_식품);
    }

    private PetFood 키워드있는_식품_생성하기() {
        Keyword 키워드 = keywordRepository.findAll().get(0);
        return petFoodRepository.save(키워드_있는_식품_초기화(키워드, 브랜드_조회하기()));
    }

    @Test
    void 아이디로_식품을_조회할_수_있다() {
        //given
        PetFood 테스트_식품 = 키워드_없이_식품_초기화(브랜드_조회하기());
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

    @Test
    void 주원료_리스트를_문자열로_저장할_수_있다() {
        //given
        PetFood 식품 = 식품_초기화(브랜드_조회하기());
        Long 생성된_아이디 = petFoodRepository.save(식품).getId();

        entityManager.clear();

        //when
        PetFood 조회한_식품 = petFoodRepository.findById(생성된_아이디).orElseThrow();

        //then
        assertThat(조회한_식품.getPrimaryIngredients()).contains("닭고기", "쌀", "귀리", "보리");
    }

}
