package zipgo.pet.exception;

public class PetException extends RuntimeException {

    public PetException(String message) {
        super(message);
    }

    public static class AgeBound extends PetException {

        public AgeBound() {
            super("분류에 속하지 않는 나이입니다.");
        }

    }

}
