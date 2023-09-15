package zipgo.pet.domain.repository;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import zipgo.common.repository.RepositoryTest;
import zipgo.pet.domain.Breeds;
import zipgo.pet.domain.PetSize;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static zipgo.pet.domain.fixture.BreedsFixture.견종_생성;
import static zipgo.pet.domain.fixture.PetSizeFixture.대형견;
import static zipgo.pet.domain.fixture.PetSizeFixture.소형견;

class BreedsRepositoryTest extends RepositoryTest {

    @Autowired
    private BreedsRepository breedsRepository;

    @Autowired
    private PetSizeRepository petSizeRepository;

    @Test
    void 이름으로_견종을_찾을_수_있다() {
        // given
        PetSize 소형견 = petSizeRepository.save(소형견());
        breedsRepository.save(견종_생성("말티즈", 소형견));

        // when
        Breeds 견종 = breedsRepository.getByName("말티즈");

        // then
        assertAll(
                () -> assertThat(견종.getName()).isEqualTo("말티즈"),
                () -> assertThat(견종.getPetSize().getName()).isEqualTo("소형견")
        );
    }

    @Test
    void 크기와_이름으로_견종을_찾을_수_있다() {
        // given
        PetSize 대형견 = petSizeRepository.save(대형견());
        breedsRepository.save(견종_생성("말라뮤트", 대형견));

        // when
        Breeds 견종 = breedsRepository.getByPetSizeAndName(대형견, "말라뮤트");

        // then
        assertAll(
                () -> assertThat(견종.getName()).isEqualTo("말라뮤트"),
                () -> assertThat(견종.getPetSize().getName()).isEqualTo("대형견")
        );
    }

    @Test
    void 특정_글자가_이름에_포함되지_않는_견종을_찾을_수_있다() {
        // given
        PetSize 대형견 = petSizeRepository.save(대형견());
        PetSize 소형견 = petSizeRepository.save(소형견());
        breedsRepository.save(견종_생성("시고르잡종", 대형견));
        breedsRepository.save(견종_생성("시고르잡종", 소형견));
        breedsRepository.save(견종_생성("말티즈", 소형견));

        // when
        List<Breeds> 견종_목록 = breedsRepository.findByNameNotContaining("시고르잡종");

        // then
        assertThat(견종_목록).hasSize(1);
    }

}
