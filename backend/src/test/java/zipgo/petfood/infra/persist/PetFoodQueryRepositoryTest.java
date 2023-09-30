package zipgo.petfood.infra.persist;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import zipgo.brand.domain.repository.BrandRepository;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.domain.repository.FunctionalityRepository;
import zipgo.petfood.domain.repository.PetFoodQueryRepository;
import zipgo.petfood.domain.repository.PetFoodRepository;
import zipgo.petfood.domain.repository.PrimaryIngredientRepository;
import zipgo.petfood.dto.response.GetPetFoodQueryResponse;

import java.util.List;

import static java.util.Collections.EMPTY_LIST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static zipgo.brand.domain.fixture.BrandFixture.아카나_식품_브랜드_생성;
import static zipgo.brand.domain.fixture.BrandFixture.오리젠_식품_브랜드_생성;
import static zipgo.brand.domain.fixture.BrandFixture.퓨리나_식품_브랜드_생성;
import static zipgo.petfood.domain.fixture.FunctionalityFixture.기능성_다이어트;
import static zipgo.petfood.domain.fixture.FunctionalityFixture.기능성_짱짱;
import static zipgo.petfood.domain.fixture.FunctionalityFixture.기능성_튼튼;
import static zipgo.petfood.domain.fixture.PetFoodFixture.모든_영양기준_만족_식품;
import static zipgo.petfood.domain.fixture.PetFoodFixture.미국_영양기준_만족_식품;
import static zipgo.petfood.domain.fixture.PetFoodFixture.식품_저장;
import static zipgo.petfood.domain.fixture.PetFoodFixture.유럽_영양기준_만족_식품;
import static zipgo.petfood.domain.fixture.PetFoodFunctionalityFixture.식품_기능성_연관관계_매핑;
import static zipgo.petfood.domain.fixture.PetFoodFunctionalityFixture.식품_기능성_추가;
import static zipgo.petfood.domain.fixture.PetFoodPrimaryIngredientFixture.식품_주원료_연관관계_매핑;
import static zipgo.petfood.domain.fixture.PetFoodPrimaryIngredientFixture.식품_주원료_추가;
import static zipgo.petfood.domain.fixture.PrimaryIngredientFixture.주원료_닭고기;
import static zipgo.petfood.domain.fixture.PrimaryIngredientFixture.주원료_돼지고기;
import static zipgo.petfood.domain.fixture.PrimaryIngredientFixture.주원료_말미잘;
import static zipgo.petfood.domain.fixture.PrimaryIngredientFixture.주원료_소고기;

@Transactional
@SuppressWarnings("NonAsciiCharacters")
@SpringBootTest(properties = {"spring.sql.init.mode=never"})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PetFoodQueryRepositoryTest {

    @Autowired
    private PetFoodRepository petFoodRepository;

    @Autowired
    private PetFoodQueryRepository petFoodQueryRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private FunctionalityRepository functionalityRepository;

    @Autowired
    private PrimaryIngredientRepository primaryIngredientRepository;

    @Test
    void 조건에_맞는_식품을_반환한다() {
        // given
        PetFood 모든_영양기준_만족_식품 = 모든_영양기준_만족_식품(brandRepository.save(아카나_식품_브랜드_생성()));
        PetFood 미국_영양기준_만족_식품 = 미국_영양기준_만족_식품(brandRepository.save(오리젠_식품_브랜드_생성()));
        PetFood 유럽_영양기준_만족_식품 = 유럽_영양기준_만족_식품(brandRepository.save(퓨리나_식품_브랜드_생성()));

        식품_기능성_추가(모든_영양기준_만족_식품, 기능성_튼튼(), functionalityRepository);
        식품_기능성_추가(미국_영양기준_만족_식품, 기능성_짱짱(), functionalityRepository);
        식품_기능성_추가(유럽_영양기준_만족_식품, 기능성_다이어트(), functionalityRepository);

        식품_주원료_추가(모든_영양기준_만족_식품, 주원료_소고기(), primaryIngredientRepository);
        식품_주원료_추가(미국_영양기준_만족_식품, 주원료_돼지고기(), primaryIngredientRepository);
        식품_주원료_추가(유럽_영양기준_만족_식품, 주원료_닭고기(), primaryIngredientRepository);

        식품_저장(petFoodRepository, 모든_영양기준_만족_식품, 미국_영양기준_만족_식품, 유럽_영양기준_만족_식품);

        List<PetFood> allFoods = petFoodRepository.findAll();
        Long lastPetFoodId = allFoods.get(allFoods.size() - 1).getId();

        List<String> brandsName = List.of("오리젠");
        List<String> standards = List.of("미국");
        List<String> primaryIngredientList = EMPTY_LIST;
        List<String> functionalityList = EMPTY_LIST;
        int size = 20;

        // when
        List<GetPetFoodQueryResponse> responses = petFoodQueryRepository.findPagingPetFoods(brandsName, standards,
                primaryIngredientList, functionalityList, lastPetFoodId, size);

        // then
        assertAll(
                () -> assertThat(responses).hasSize(1),
                () -> assertThat(responses).extracting(GetPetFoodQueryResponse::foodName)
                        .contains("미국 영양기준 만족 식품"),
                () -> assertThat(responses).extracting(GetPetFoodQueryResponse::brandName)
                        .contains("오리젠")
        );
    }

    @Test
    void 조건에_맞는_식품의_전체_개수를_반환한다() {
        // given
        PetFood 모든_영양기준_만족_식품 = 모든_영양기준_만족_식품(brandRepository.save(아카나_식품_브랜드_생성()));
        PetFood 미국_영양기준_만족_식품 = 미국_영양기준_만족_식품(brandRepository.save(오리젠_식품_브랜드_생성()));
        PetFood 유럽_영양기준_만족_식품 = 유럽_영양기준_만족_식품(brandRepository.save(퓨리나_식품_브랜드_생성()));

        식품_기능성_추가(모든_영양기준_만족_식품, 기능성_튼튼(), functionalityRepository);
        식품_기능성_추가(미국_영양기준_만족_식품, 기능성_짱짱(), functionalityRepository);
        식품_기능성_추가(유럽_영양기준_만족_식품, 기능성_다이어트(), functionalityRepository);

        식품_주원료_추가(모든_영양기준_만족_식품, 주원료_소고기(), primaryIngredientRepository);
        식품_주원료_추가(미국_영양기준_만족_식품, 주원료_돼지고기(), primaryIngredientRepository);
        식품_주원료_추가(유럽_영양기준_만족_식품, 주원료_닭고기(), primaryIngredientRepository);

        식품_저장(petFoodRepository, 모든_영양기준_만족_식품, 미국_영양기준_만족_식품, 유럽_영양기준_만족_식품);

        List<PetFood> allFoods = petFoodRepository.findAll();
        Long lastPetFoodId = allFoods.get(allFoods.size() - 1).getId();

        PetFood petFood = petFoodRepository.getById(lastPetFoodId);
        식품_기능성_연관관계_매핑(petFood, 기능성_다이어트());
        식품_주원료_연관관계_매핑(petFood, 주원료_말미잘());
        petFoodRepository.save(petFood);

        List<String> brandsName = EMPTY_LIST;
        List<String> standards = EMPTY_LIST;
        List<String> primaryIngredientList = EMPTY_LIST;
        List<String> functionalityList = EMPTY_LIST;

        // when
        Long petFoodsCount = petFoodQueryRepository.findPetFoodsCount(brandsName, standards,
                primaryIngredientList, functionalityList);

        // then
        assertThat(petFoodsCount).isEqualTo(3);
    }

}
