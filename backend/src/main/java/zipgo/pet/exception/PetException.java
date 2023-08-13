package zipgo.pet.exception;

public class PetException extends RuntimeException {

    public PetException(String message) {
        super(message);
    }

    public static class NotFound extends PetException {

        public NotFound() {
            super("분류에 속하지 않는 나이입니다.");
        }

    }

}
