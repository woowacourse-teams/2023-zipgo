package zipgo.common.error;

import lombok.Getter;

@Getter
public class ZipgoException extends RuntimeException {

    private final ErrorCode errorCode;

    public ZipgoException(final ErrorCode errorCode) {
        super(errorCode.message());
        this.errorCode = errorCode;
    }

}
