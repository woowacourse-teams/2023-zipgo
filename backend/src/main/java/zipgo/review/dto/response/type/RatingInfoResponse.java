package zipgo.review.dto.response.type;

public record RatingInfoResponse(
        String name,
        int percentage
) {

    public static RatingInfoResponse of(Integer rating, int percentage) {
        return new RatingInfoResponse(String.valueOf(rating), percentage);
    }

}
