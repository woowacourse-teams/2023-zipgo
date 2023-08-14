package zipgo.pet.exception;

public class PetException extends RuntimeException {

    public PetException(String message) {
        super(message);
    }

    public static class AgeNotFound extends PetException {

        public AgeNotFound() {
            super("분류에 속하지 않는 나이입니다.");
        }

    }

    public static class GenderNotFound extends PetException {

        public GenderNotFound() {
            super("존재하지 않는 성별입니다.");
        }
    }

}
