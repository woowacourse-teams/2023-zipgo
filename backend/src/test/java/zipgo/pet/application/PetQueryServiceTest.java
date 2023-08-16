package zipgo.pet.application;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import zipgo.common.service.QueryServiceTest;
import zipgo.pet.domain.Breeds;
import zipgo.pet.domain.PetSize;
import zipgo.pet.domain.repository.BreedsRepository;
import zipgo.pet.domain.repository.PetSizeRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class PetQueryServiceTest extends QueryServiceTest {

    @Autowired
    private BreedsRepository breedsRepository;

    @Autowired
    private PetSizeRepository petSizeRepository;

    @Autowired
    private PetQueryService petQueryService;

    @Nested
    class 견종_조회시 {

        @Test
        void 첫번째_순서는_항상_믹스견이다() {
            // given
            PetSize 소형견 = 견종_크기_등록("소형견");
            견종_등록("포메라니안", 소형견);
            견종_등록("시츄", 소형견);
            견종_등록("푸들", 소형견);

            // when
            List<Breeds> 견종_목록 = petQueryService.readBreeds();

            // then
            assertThat(견종_목록.get(0).getName()).isEqualTo("믹스견");
        }

        @Test
        void 믹스견을_포함하여_조회한다() {
            // given
            PetSize 소형견 = 견종_크기_등록("소형견");
            PetSize 중형견 = 견종_크기_등록("중형견");
            PetSize 대형견 = 견종_크기_등록("대형견");
            견종_등록("푸들", 소형견);
            견종_등록("시츄", 중형견);
            견종_등록("말라뮤트", 대형견);

            // when
            List<Breeds> 견종_목록 = petQueryService.readBreeds();

            // then
            assertAll(
                    () -> assertThat(견종_목록.size()).isNotEqualTo(3),
                    () -> assertThat(견종_목록).hasSize(4),
                    () -> assertThat(견종_목록.get(0).getName()).isEqualTo("믹스견")
            );
        }

    }


    private PetSize 견종_크기_등록(String 견종_크기) {
        PetSize petSize = PetSize
                .builder()
                .name(견종_크기)
                .build();
        return petSizeRepository.save(petSize);
    }

    private Breeds 견종_등록(String 견종_이름, PetSize 크기) {
        Breeds 견종 = Breeds.builder()
                .name(견종_이름)
                .petSize(크기)
                .build();
        return breedsRepository.save(견종);
    }

}