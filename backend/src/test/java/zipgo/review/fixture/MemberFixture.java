package zipgo.review.fixture;

import zipgo.member.domain.Member;

public class MemberFixture {

    public static Member 무민() {
        return Member.builder().name("무민").build();
    }

}
