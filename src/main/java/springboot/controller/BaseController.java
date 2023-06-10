package springboot.controller;



import org.springframework.beans.factory.annotation.Autowired;
import springboot.model.admin.AdminDTO;
import springboot.model.admin.AdminEntity;

import javax.servlet.http.HttpSession;


public abstract class BaseController {

    @Autowired
    protected HttpSession httpSession;

    public HttpSession getession(){
        return httpSession;
    }

    public AdminEntity getusession(){
        return (AdminEntity)httpSession.getAttribute("login");
    }

    public void setusession(AdminEntity adminEntity){
        httpSession.setAttribute("login",adminEntity);
    }
}
