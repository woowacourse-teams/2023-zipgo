package zipgo.brand.domain.fixture;

import zipgo.brand.domain.Brand;

public class BrandFixture {

    public static Brand 오리젠() {
        return Brand.builder()
                .name("오리젠")
                .imageUrl(
                        "https://www.nestle.com/sites/default/files/styles/brand_logo/public/purina-logo-square-2023.png?h=a7e6d17b&itok=k6CCv7Sr")
                .nation("캐나")
                .foundedYear(1985)
                .hasResearchCenter(true)
                .hasResidentVet(true)
                .build();
    }

    public static Brand 퓨리나() {
        return Brand.builder()
                .name("퓨리나")
                .imageUrl(
                        "https://www.nestle.com/sites/default/files/styles/brand_logo/public/purina-logo-square-2023.png?h=a7e6d17b&itok=k6CCv7Sr")
                .nation("대한민국")
                .foundedYear(1923)
                .hasResearchCenter(false)
                .hasResidentVet(false)
                .build();
    }

    public static Brand 아카나() {
        return Brand.builder()
                .name("아카나")
                .imageUrl("https://intl.acana.com/wp-content/themes/acana2019/img/logo.png")
                .nation("캐나다")
                .foundedYear(1977)
                .hasResearchCenter(true)
                .hasResidentVet(false)
                .build();
    }

    public static Brand 인스팅트() {
        return Brand.builder()
                .name("인스팅트")
                .imageUrl("https://instinctpetfood.com/wp-content/uploads/2021/08/instinct-logo.jpg")
                .nation("미국")
                .foundedYear(2003)
                .hasResearchCenter(false)
                .hasResidentVet(true)
                .build();
    }

}
