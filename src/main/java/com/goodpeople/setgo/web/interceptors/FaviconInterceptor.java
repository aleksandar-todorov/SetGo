package com.goodpeople.setgo.web.interceptors;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class FaviconInterceptor extends HandlerInterceptorAdapter {

    private static final String URL = "https://www.tronviggroup.com/wp-content/uploads/2015/05/Strategy-Primer-300x254.png";
    private static final String FAVICON = "favicon";

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

        if(modelAndView != null){
            modelAndView.addObject(FAVICON , URL);
        }

    }
}
