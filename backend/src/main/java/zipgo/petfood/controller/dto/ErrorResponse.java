package zipgo.petfood.controller.dto;

public record ErrorResponse(
        String message
) {

    public static ErrorResponse of(Exception exception) {
        return new ErrorResponse(exception.getMessage());
    }

}
