package com.example.yamp.usersvc.security;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.header.HeaderWriter;

public class TraceHeaderWriter implements HeaderWriter {
    private final Logger logger = LoggerFactory.getLogger(TraceHeaderWriter.class);

    @Override
    public void writeHeaders(HttpServletRequest request, HttpServletResponse response) {
        String correlationId = request.getHeader("X-Correlation-ID");
        if(correlationId == null){
            logger.warn("X-Correlation-ID is missing in the request");
            return;
        }
        response.addHeader( "X-Correlation-ID", correlationId);
        logger.debug("Service received request with correlation-id: {}", correlationId);
    }
}
