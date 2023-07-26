package zipgo.petfood.exception;

public class PetFoodException extends RuntimeException {

    public PetFoodException(String message) {
        super(message);
    }

    public static class NotFound extends PetFoodException {

        public NotFound(Long id) {
            super(String.format("아이디가 %d 인 상품을 찾을 수 없습니다.", id));
        }

    }

}
