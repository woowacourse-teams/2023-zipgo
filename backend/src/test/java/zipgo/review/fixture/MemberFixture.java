package zipgo.review.fixture;

import zipgo.member.domain.Member;

public class MemberFixture {

    public static Member 무민() {
        return Member.builder().email("moomin@moomin.com").profileImgUrl("무민사진").name("무민").build();
    }

    public static Member 멤버_이름(String uniqueName) {
        return Member.builder()
                .email(uniqueName + "@zipgo.pet")
                .name(uniqueName)
                .profileImgUrl("프로필사진")
                .build();
    }

}
