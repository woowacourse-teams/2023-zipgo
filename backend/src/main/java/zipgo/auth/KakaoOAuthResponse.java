package zipgo.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoOAuthResponse implements OAuthResponse {

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAcount;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class KakaoAccount {
        private Profile profile;
        private String email;
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Profile {
        private String nickname;
        private String imgUrl;
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
    public String getImageUrl() {
        return kakaoAcount.getProfile().getImgUrl();
    }

}
