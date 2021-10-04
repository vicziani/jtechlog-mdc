package hello;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.turbo.TurboFilter;
import ch.qos.logback.core.spi.FilterReply;
import org.slf4j.MDC;
import org.slf4j.Marker;

public class UserAgentMdcFilter extends TurboFilter {

    private String prefix;

    @Override
    public FilterReply decide(Marker marker, Logger logger, Level level, String format, Object[] params, Throwable t) {
        var userAgent = MDC.get(TraceFilter.USER_AGENT_MDC_KEY);
        if (userAgent != null && userAgent.toLowerCase().startsWith(prefix.toLowerCase())) {
            return FilterReply.DENY;
        }
        return FilterReply.NEUTRAL;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
