package zipgo.member.exception;

import zipgo.common.error.ErrorCode;
import zipgo.common.error.ZipgoException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class MemberNotFoundException extends ZipgoException {

    public MemberNotFoundException() {
        super(new ErrorCode(NOT_FOUND, "회원을 찾을 수 없습니다. 알맞은 회원인지 확인해주세요."));
    }

}
