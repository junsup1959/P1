package springboot.interceptor;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import springboot.model.admin.AdminEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.function.Predicate;

@Log4j2
@Component
public class Interceptor implements HandlerInterceptor {

    @Autowired
    private HttpSession session;


    private final String[] skipurl = {"/register","/login","/password"};

    @Override
     public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String Rurl = request.getRequestURI();

        Predicate predicate = T -> Rurl.contains(T.toString());

        Long count = Arrays.stream(skipurl).filter(predicate).count();

        AdminEntity adminEntity = (AdminEntity)session.getAttribute("login");



        if(Rurl.equals("/") || count > 0){
            return true;
        }else {
            if(adminEntity == null){
                response.sendRedirect("/login");
                return false;
            }
        }


        return true;
    }
}
