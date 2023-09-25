package zipgo.auth.dto;

public record Tokens (
        String accessToken,
        String refreshToken
) {

    public static Tokens of(String accessToken, String refreshToken) {
        return new Tokens(
                accessToken,
                refreshToken
        );
    }

}
