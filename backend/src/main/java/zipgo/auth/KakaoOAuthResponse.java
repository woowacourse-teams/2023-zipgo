package zipgo.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoOAuthResponse implements OAuthResponse {

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAcount;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class KakaoAccount {
        private Profile profile;
        private String email;
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Profile {
        private String nickname;
    }

    @Override
    public String getEmail() {
        return kakaoAcount.getEmail();
    }

    @Override
    public String getName() {
        return kakaoAcount.getProfile().getNickname();
    }

}
