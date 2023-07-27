package zipgo.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoOAuthResponse implements OAuthResponse {

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAcount;

    @Override
    public String getEmail() {
        return kakaoAcount.getEmail();
    }

    @Override
    public String getName() {
        return kakaoAcount.getProfile().getNickname();
    }

}

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
class KakaoAccount {

    private Profile profile;
    private String email;

}

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
class Profile {

    private String nickname;

}
