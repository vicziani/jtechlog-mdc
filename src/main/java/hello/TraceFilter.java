package hello;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Component
public class TraceFilter extends OncePerRequestFilter {

    public static final String TRACE_ID_MDC_KEY = "traceId";

    public static final String POSTMAN_TOKEN_MDC_KEY = "postmanToken";

    public static final String USER_AGENT_MDC_KEY = "userAgent";

    public static final String POSTMAN_TOKEN_HEADER_NAME = "Postman-Token";

    public static final String USER_AGENT_HEADER_NAME = "User-Agent";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var traceId = UUID.randomUUID().toString();
        var postmanToken = request.getHeader(POSTMAN_TOKEN_HEADER_NAME);
        var userAgent = request.getHeader(USER_AGENT_HEADER_NAME);

        MDC.put(TRACE_ID_MDC_KEY, traceId);
        MDC.put(POSTMAN_TOKEN_MDC_KEY, postmanToken);
        MDC.put(USER_AGENT_MDC_KEY, userAgent);

        try {
            filterChain.doFilter(request, response);
        } finally {
            MDC.remove(TRACE_ID_MDC_KEY);
            MDC.remove(POSTMAN_TOKEN_MDC_KEY);
            MDC.remove(USER_AGENT_MDC_KEY);
        }

    }
}
