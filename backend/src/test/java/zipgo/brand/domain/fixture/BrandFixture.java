package zipgo.brand.domain.fixture;

import zipgo.brand.domain.Brand;

public class BrandFixture {

    public static Brand 식품_브랜드_생성하기() {
        return Brand.builder()
                .name("오리젠")
                .nation("미국")
                .foundedYear(1985)
                .hasResearchCenter(true)
                .hasResidentVet(true)
                .build();
    }

}
