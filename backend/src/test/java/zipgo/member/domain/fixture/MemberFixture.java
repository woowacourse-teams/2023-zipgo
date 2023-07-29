package zipgo.member.domain.fixture;

import zipgo.member.domain.Member;

public class MemberFixture {

    public static Member 멤버_생성() {
        return Member.builder().email("이메일").profileImgUrl("사진").name("이름").build();
    }

}
