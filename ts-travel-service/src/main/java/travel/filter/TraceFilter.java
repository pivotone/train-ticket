package travel.filter;

import brave.Span;
import brave.Tracer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * @author pivot
 * @Date 2023/12/6 16:57
 */
@Component
@PropertySource("classpath:info.properties")
public class TraceFilter extends GenericFilterBean {
    @Value("${version}")
    private String version;

    private final Tracer tracer;

    TraceFilter(Tracer tracer) {
        this.tracer = tracer;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        Span currentSpan = this.tracer.currentSpan();
        // do nothing if current span is null
        if (currentSpan == null) {
            chain.doFilter(request, response);
            return;
        }
        // add parameter to tag
        ObjectMapper mapper = new ObjectMapper();
        currentSpan.tag("version", version);
        currentSpan.tag("Http.parameter", mapper.writeValueAsString(request.getParameterMap()));
        chain.doFilter(request, response);
    }
}
