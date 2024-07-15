package org.velog.api.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.Enumeration;

@Component
@Slf4j
public class LoggerFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        ContentCachingRequestWrapper req = new ContentCachingRequestWrapper((HttpServletRequest) servletRequest);
        ContentCachingResponseWrapper res = new ContentCachingResponseWrapper((HttpServletResponse) servletResponse);

        log.info("INIT URI : {}", req.getRequestURI());

        try{
            filterChain.doFilter(req, res);
        }finally {
            logRequestDetails(req);
            logResponseDetails(res, req.getRequestURI(), req.getMethod());
            res.copyBodyToResponse();
        }
    }

    private void logRequestDetails(ContentCachingRequestWrapper request){
        if(shouldLogRequest(request)){
            Enumeration<String> headerNames = request.getHeaderNames();
            StringBuilder requestHeaderValues = new StringBuilder();

            headerNames.asIterator().forEachRemaining(headerKey -> {
                String headerValue = request.getHeader(headerKey);

                requestHeaderValues
                        .append("[")
                        .append(headerKey)
                        .append(" : ")
                        .append(headerValue)
                        .append("] ");
            });

            String requestBody = new String(request.getContentAsByteArray());
            String uri = request.getRequestURI();
            String method = request.getMethod();

            log.info(">>>>> uri : {} , method : {} , header : {} , body : {}",
                    uri,
                    method,
                    requestHeaderValues,
                    limitBodySize(requestBody));
        }
    }

    private void logResponseDetails(ContentCachingResponseWrapper response, String requestUri, String method){
        if(shouldLogResponse(response)){
            StringBuilder responseHeaderValues = new StringBuilder();
            response.getHeaderNames().forEach(headerKey ->
                    responseHeaderValues
                            .append("[")
                            .append(headerKey)
                            .append(" : ")
                            .append(response.getHeader(headerKey))
                            .append("] ")
                    );

            String responseBody = new String(response.getContentAsByteArray());
            log.info("<<<<< uri : {} , method : {} , headers : {} , body : {}",
                    requestUri,
                    method,
                    response,
                    limitBodySize(responseBody));
        }
    }

    private boolean shouldLogRequest(ContentCachingRequestWrapper requestWrapper){
        return "POST".equalsIgnoreCase(requestWrapper.getMethod());
                //|| "GET".equalsIgnoreCase(requestWrapper.getMethod());
    }

    private boolean shouldLogResponse(ContentCachingResponseWrapper response) {
        return response.getStatus() >= 200;
    }

    private String limitBodySize(String requestBody){
        return requestBody.length() > 500 ? requestBody.substring(0, 500) + "..." :requestBody;
    }
}
/***
 * GET /api/example HTTP/1.1
 * Host: localhost:8080
 * User-Agent: PostmanRuntime/7.28.4
 * Accept: %/*
 * Cookie:sessionId=abc123;trackingId=xyz456
 *
 * headerName[EnumerationType] : value
 *
 * */
