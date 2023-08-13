package zipgo.petfood.domain.repository;

import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import zipgo.brand.domain.Brand;
import zipgo.brand.domain.fixture.BrandFixture;
import zipgo.brand.domain.repository.BrandRepository;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.exception.PetFoodException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static zipgo.petfood.domain.fixture.PetFoodFixture.모든_영양기준_만족_식품;
import static zipgo.petfood.domain.fixture.PetFoodFixture.유럽_영양기준_만족_식품;

@DataJpaTest
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PetFoodRepositoryTest {

    @Autowired
    private PetFoodRepository petFoodRepository;

    @Autowired
    private BrandRepository brandRepository;

    private Brand 브랜드_조회하기() {
        return brandRepository.save(BrandFixture.오리젠_식품_브랜드_생성());
    }

    @Test
    void 모든_식품을_조회할_수_있다() {
        // given
        Brand 브랜드 = 브랜드_조회하기();
        PetFood 반려동물_식품_1 = petFoodRepository.save(모든_영양기준_만족_식품(브랜드));
        PetFood 반려동물_식품_2 = petFoodRepository.save(유럽_영양기준_만족_식품(브랜드));

        // when
        List<PetFood> petFoods = petFoodRepository.findAll();

        // then
        assertThat(petFoods).contains(반려동물_식품_1, 반려동물_식품_2);
    }

    @Test
    void 아이디로_식품을_조회할_수_있다() {
        //given
        PetFood 테스트_식품 = 모든_영양기준_만족_식품(브랜드_조회하기());
        Long 아이디 = petFoodRepository.save(테스트_식품).getId();

        //when
        PetFood 조회_식품 = petFoodRepository.getById(아이디);

        //then
        assertThat(조회_식품).isEqualTo(테스트_식품);
    }

    @ParameterizedTest
    @ValueSource(longs = {-1L, 999999999999999L, 0L})
    void 존재하지_않는_아이디로_조회하면_예외가_발생한다(Long 존재하지_않는_아이디) {
        //when, then
        assertThatThrownBy(() -> petFoodRepository.getById(존재하지_않는_아이디))
                .isInstanceOf(PetFoodException.NotFound.class);

    }

}
