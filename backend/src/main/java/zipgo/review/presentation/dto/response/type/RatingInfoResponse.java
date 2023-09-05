package zipgo.review.presentation.dto.response.type;

public record RatingInfoResponse(
        String name,
        int percentage
) {

    public static RatingInfoResponse of(Integer name, int percentage) {
        return new RatingInfoResponse(String.valueOf(name), percentage);
    }

}
