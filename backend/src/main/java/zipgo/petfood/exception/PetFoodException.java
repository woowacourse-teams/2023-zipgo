package zipgo.petfood.exception;

import static java.lang.String.format;

public class PetFoodException extends RuntimeException {

    public PetFoodException(String message) {
        super(message);
    }

    public static class NotFound extends PetFoodException {

        public NotFound(Long id) {
            super(format("아이디가 %d 인 상품을 찾을 수 없습니다.", id));
        }

    }

}
