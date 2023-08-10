package zipgo.member.domain.fixture;

import zipgo.member.domain.Member;

public class MemberFixture {

    public static Member 식별자_없는_멤버() {
        return Member.builder().email("이메일").profileImgUrl("사진").name("이름").build();
    }

    public static Member 식별자_없는_멤버(String email) {
        return Member.builder().email(email).profileImgUrl("사진").name("이름").build();
    }

    public static Member 식별자_있는_멤버() {
        return Member.builder().id(1L).email("이메일").profileImgUrl("사진").name("이름").build();
    }

}
