package zipgo.petfood.domain.fixture;

import zipgo.petfood.domain.Functionality;

public class FunctionalityFixture {

    public static Functionality 기능성_다이어트() {
        Functionality 다이어트 = Functionality.builder()
                .name("다이어트")
                .build();
        return 다이어트;
    }

    public static Functionality 기능성_튼튼() {
        Functionality 튼튼 = Functionality.builder()
                .name("튼튼")
                .build();
        return 튼튼;
    }

    public static Functionality 기능성_짱짱() {
        Functionality 짱짱 = Functionality.builder()
                .name("짱짱")
                .build();
        return 짱짱;
    }

}