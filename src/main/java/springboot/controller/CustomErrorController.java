package springboot.controller;


import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String Errorhandle(HttpServletRequest request,Model model){
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object excetion = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        Object message = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        Object excetionType = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION_TYPE);
        Object errorserlvetName = request.getAttribute(RequestDispatcher.ERROR_SERVLET_NAME);
        Object path = request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);

        model.addAttribute("exception",excetion);
        model.addAttribute("message",message);
        model.addAttribute("status",status);
        model.addAttribute("excetionType",excetionType);
        model.addAttribute("errorserlvetName",errorserlvetName);
        model.addAttribute("path",path);
        if(status != null){
            int statusCode = Integer.valueOf(status.toString());

            if(statusCode == HttpStatus.NOT_FOUND.value()){
                return this.getErrorPath() + "404";
            }
            if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()){
                return this.getErrorPath() + "500";
            }
        }
        return "error";
    }

    @Override
    public String getErrorPath(){
        return "/error/";
    }


}
