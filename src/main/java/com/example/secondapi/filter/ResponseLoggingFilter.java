package com.example.secondapi.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Component
@Order(2)
public class ResponseLoggingFilter implements Filter {
    Logger LOGGER = LoggerFactory.getLogger(ResponseLoggingFilter.class);
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        LOGGER.info("Inside response " + response.toString());
        chain.doFilter(request,response);
    }
}
