package utils;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

//@WebFilter("/*")
public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        String loginURI = req.getContextPath() + "/login.xhtml";

        // Check if user is logged in
        boolean loggedIn = (session != null && session.getAttribute("loginBean") != null);
        boolean loginRequest = req.getRequestURI().equals(loginURI);
        boolean resourceRequest = req.getRequestURI().contains("jakarta.faces.resource");

        if (loggedIn || loginRequest || resourceRequest) {
            chain.doFilter(request, response); // Let them through
        } else {
            res.sendRedirect(loginURI); // Kick them back to login
        }
    }
}
