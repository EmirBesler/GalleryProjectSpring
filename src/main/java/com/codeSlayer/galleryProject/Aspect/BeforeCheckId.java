package com.codeSlayer.galleryProject.Aspect;


import com.codeSlayer.galleryProject.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.BadRequestException;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class BeforeCheckId {

    @Autowired
    JwtService jwtService;


    @Before(value = "@annotation(com.codeSlayer.galleryProject.Annotations.CheckId) && args(id,request)", argNames = "id,request")
    public void beforeIdCheck(Object id, HttpServletRequest request) throws Exception {
        Long pathId = (Long)id;
        Long tokenId = jwtService.getIdFromToken(request);
        System.out.println(tokenId);
        if(tokenId==pathId){
            System.out.println("id's are equal,OK");
        }
        else{
            if(tokenId==2){
                System.out.println("id's are not equal,but you are admin,OK");
            }
            else{
                throw new Exception("id's are not equal");
            }
        }
    }
}
