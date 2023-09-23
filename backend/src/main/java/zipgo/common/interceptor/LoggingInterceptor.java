package zipgo.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import zipgo.aspect.QueryCounter;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoggingInterceptor implements HandlerInterceptor {

    private static final String QUERY_COUNT_LOG = "METHOD: {}, URL: {}, STATUS_CODE: {}, QUERY_COUNT: {}";
    private static final String QUERY_COUNT_WARN_LOG = "쿼리가 {}번 이상 실행되었습니다!!!";
    private static final int WARN_QUERY_COUNT= 8;

    private final QueryCounter queryCounter;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) {
        int queryCount = queryCounter.getCount();
        log.info(QUERY_COUNT_LOG, request.getMethod(), request.getRequestURI(), response.getStatus(), queryCount);
        if (queryCount >= WARN_QUERY_COUNT) {
            log.warn(QUERY_COUNT_WARN_LOG, WARN_QUERY_COUNT);
        }
    }

}
