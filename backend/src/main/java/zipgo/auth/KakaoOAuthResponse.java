package zipgo.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zipgo.member.domain.Member;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoOAuthResponse implements OAuthResponse {

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @Getter
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class KakaoAccount {
        private Profile profile;
        private String email;
    }

    @Getter
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Profile {
        private String nickname;
        @JsonProperty("thumbnail_image_url")
        private String picture;
    }

    @Override
    public String getEmail() {
        return kakaoAccount.getEmail();
    }

    @Override
    public String getNickName() {
        return kakaoAccount.getProfile().getNickname();
    }

    @Override
    public String getPicture() {
        return kakaoAccount.getProfile().getPicture();
    }

    @Override
    public Member createMember() {
        return Member.builder()
                .name(getNickName())
                .email(getEmail())
                .profileImgUrl(getPicture())
                .build();
    }

}

