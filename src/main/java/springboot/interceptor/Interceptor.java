package springboot.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j

public class Interceptor implements HandlerInterceptor {

    @Override
     public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String url = request.getRequestURI();

        if(url.indexOf("/register") > -1 && url.indexOf("/login") > -1){
            if(request.getSession().getAttribute("adminLogin") != null){
                return true;
            }
            return false;
        }

        return true;
    }
}
