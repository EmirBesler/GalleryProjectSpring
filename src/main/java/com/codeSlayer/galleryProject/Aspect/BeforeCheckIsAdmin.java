package com.codeSlayer.galleryProject.Aspect;

import com.codeSlayer.galleryProject.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class BeforeCheckIsAdmin {

    @Autowired
    JwtService jwtService;


    @Before("@annotation(com.codeSlayer.galleryProject.Annotations.CheckIsAdmin) && args(request)")
    public void checkAdmin(HttpServletRequest request) throws Exception {
        if(jwtService.getRoleFromUser(request).equals("ADMIN")){
            System.out.println("no problem, admin can do it");
        }
        else{
            throw new Exception("no access for user");
        }
    }
}
