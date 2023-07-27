package zipgo.brand.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class BrandTest {

    @Test
    void 아이디가_같으면_동등하다() {
        Brand 브랜드_1 = Brand.builder()
                .id(1L)
                .name("사료만드는 삼성")
                .nation("대한민국")
                .foundedYear(1999)
                .hasResearchCenter(true)
                .hasResidentVet(false)
                .build();

        Brand 브랜드_2 = Brand.builder()
                .id(1L)
                .name("사료만드는 삼성")
                .nation("대한민국")
                .foundedYear(1999)
                .hasResearchCenter(true)
                .hasResidentVet(false)
                .build();

        assertThat(브랜드_1).isEqualTo(브랜드_2);
    }

    @Test
    void 아이디가_다르면_동등하지않다() {
        Brand 브랜드_1 = Brand.builder()
                .id(1L)
                .name("사료만드는 삼성")
                .nation("대한민국")
                .foundedYear(1999)
                .hasResearchCenter(true)
                .hasResidentVet(false)
                .build();

        Brand 브랜드_2 = Brand.builder()
                .id(2L)
                .name("사료만드는 삼성")
                .nation("대한민국")
                .foundedYear(1999)
                .hasResearchCenter(true)
                .hasResidentVet(false)
                .build();

        assertThat(브랜드_2).isNotEqualTo(브랜드_1);
    }

}
