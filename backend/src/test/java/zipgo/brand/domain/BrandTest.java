package zipgo.brand.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class BrandTest {

    @Test
    void 아이디가_같으면_동등하다() {
        Brand 브랜드_1 = new Brand(2L, "사료만드는 삼성", "대한민국", 1999L, true, false);
        Brand 브랜드_2 = new Brand(2L, "사료만드는 토스", "대한민국", 2090L, true, false);

        assertThat(브랜드_2).isEqualTo(브랜드_1);
    }

    @Test
    void 아이디가_다르면_동등하지않다() {
        Brand 브랜드_1 = new Brand(1L, "사료만드는 삼성", "대한민국", 1999L, true, false);
        Brand 브랜드_2 = new Brand(2L, "사료만드는 토스", "대한민국", 2090L, true, false);

        assertThat(브랜드_2).isNotEqualTo(브랜드_1);
    }

}
