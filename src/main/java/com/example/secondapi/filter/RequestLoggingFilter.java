package com.example.secondapi.filter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import java.io.IOException;

@Component
@Order(1)
public class RequestLoggingFilter extends HttpFilter {
    Logger LOGGER = LoggerFactory.getLogger(RequestLoggingFilter.class);
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        LOGGER.info("Inside request ");
        chain.doFilter(request,response);
    }
}
