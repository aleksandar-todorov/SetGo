package com.goodpeople.setgo.web.interceptors;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TitleInterceptor extends HandlerInterceptorAdapter {

    private final String SETGO_APP = "SetGo App - ";
    private final String USERS = "users";
    private final String TITLE = "title";


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        if (modelAndView == null) {
            modelAndView = new ModelAndView();
        } else {
            String[] a = modelAndView.getViewName().split("/");
            String title = SETGO_APP;
            if (USERS.equals(a[0])) {
                if (a.length == 1) {
                    title += USERS;
                } else {
                    title += a[1];
                }
            } else {
                title += a[0];
            }
            modelAndView.addObject(TITLE, title);
        }


    }
}
