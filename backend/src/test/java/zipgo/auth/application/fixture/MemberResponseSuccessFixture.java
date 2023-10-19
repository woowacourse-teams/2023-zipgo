package zipgo.auth.application.fixture;

import zipgo.auth.application.dto.OAuthMemberResponse;
import zipgo.member.domain.Member;

public class MemberResponseSuccessFixture implements OAuthMemberResponse {

    @Override
    public String getEmail() {
        return "이메일";
    }

    @Override
    public String getNickName() {
        return "이름";
    }

    @Override
    public String getPicture() {
        return "사진";
    }

    @Override
    public Member toMember() {
        return Member.builder()
                .name(getNickName())
                .profileImgUrl(getPicture())
                .email(getEmail())
                .build();
    }

}
