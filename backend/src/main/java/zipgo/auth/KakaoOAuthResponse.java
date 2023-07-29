package zipgo.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class KakaoOAuthResponse implements OAuthResponse {

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAcount;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class KakaoAccount {
        private Profile profile;
        private String email;
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Profile {
        private String nickname;
        @JsonProperty("thumbnail_image_url")
        private String picture;
    }

    @Override
    public String getEmail() {
        return kakaoAcount.getEmail();
    }

    @Override
    public String getNickName() {
        return kakaoAcount.getProfile().getNickname();
    }

    @Override
    public String getPicture() {
        return kakaoAcount.getProfile().getPicture();
    }

}
