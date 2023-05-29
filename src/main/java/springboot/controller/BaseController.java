package springboot.controller;



import org.springframework.beans.factory.annotation.Autowired;
import springboot.model.admin.AdminDTO;

import javax.servlet.http.HttpSession;


public abstract class BaseController {

    @Autowired
    protected HttpSession httpSession;

    public HttpSession getSession(){
        return httpSession;
    }

    public AdminDTO getAdminsession(){

        return (AdminDTO)httpSession.getAttribute("adminLogin");
    }
}
