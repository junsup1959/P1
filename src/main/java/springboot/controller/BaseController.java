package springboot.controller;



import org.springframework.beans.factory.annotation.Autowired;
import springboot.model.admin.AdminDTO;
import springboot.model.admin.AdminEntity;

import javax.servlet.http.HttpSession;


public abstract class BaseController {

    @Autowired
    protected HttpSession httpSession;

    public HttpSession getSession(){
        return httpSession;
    }

    public AdminEntity getAdminsession(){
        return (AdminEntity)httpSession.getAttribute("adminLogin");
    }

    public void setAdminsession(AdminEntity adminEntity){
        httpSession.setAttribute("adminLogin",adminEntity);
    }
}
