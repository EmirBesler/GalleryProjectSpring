package com.codeSlayer.galleryProject.jwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthEntryPoint implements AuthenticationEntryPoint {


    //Bu sınıf niye var==> authorization ile ilgili bir hata standart olarak piyasada 401 olarak gelir,
    //fakat spring'te bu durumu config etmezsek 403 olarak geliyor,burada 403'ü 401'e (unauthorized) çeviriyoruz.
    //tabi bu sınıfı yazmış olmakla iş bitmiyor,sonrasında App.Config'te bu sınıfa Autowired ile erişip gerekli yere
    //ekliyoruz.
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        System.out.println(authException.getMessage());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
    }
}

