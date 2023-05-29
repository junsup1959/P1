package springboot.controller;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import springboot.model.admin.AdminDTO;
import springboot.model.admin.AdminEntity;
import springboot.service.AdminService;


@Log4j2
@RequestMapping("/")
@Controller
@RequiredArgsConstructor
public class HomeController extends BaseController {


    private final AdminService adminService;

    @GetMapping("")
    public ModelAndView main(ModelAndView mv){

        String vewName = super.getAdminsession() == null ? "redirect:/login" : "redirect:/index";

        mv.setViewName(vewName);

        return mv;
    }
    
    
    @GetMapping("index")
    public ModelAndView home(ModelAndView mv){

        mv.setViewName("/admin/index");

        return mv;
    }

    @GetMapping("contact")
    public ModelAndView concact(ModelAndView mv){

        mv.setViewName("/admin/contact");

        return mv;
    }

    @GetMapping("register")
    public ModelAndView register(ModelAndView mv){

        mv.setViewName("/admin/register");

        return mv;
    }

    @PostMapping("register")
    public ModelAndView postregister(ModelAndView mv, AdminDTO admin){

        System.out.println("ㅎㅇㅎㅇ");
        String view = "redirect:index";
        if(admin.getConfirmpasswd().equals(admin.getPasswd())){
            AdminEntity adminEntity = adminService.insert(admin);
            view = adminEntity == null ? "redirect:index" : "redirect:index";
        }

        mv.setViewName(view);
        return mv;
    }

    @GetMapping("login")
    public ModelAndView resume(ModelAndView mv){

        mv.setViewName("/admin/login");

        return mv;
    }
}
