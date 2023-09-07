package zipgo.review.presentation.dto.response.type;

public record RatingInfoResponse(
        String rating,
        int percentage
) {

    public static RatingInfoResponse of(Integer rating, int percentage) {
        return new RatingInfoResponse(String.valueOf(rating), percentage);
    }

}
