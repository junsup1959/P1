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

        String view = "redirect:/login";
        if(admin.getConfirmpasswd().equals(admin.getPasswd())){
            AdminEntity adminEntity = adminService.insert(admin);
            if(adminEntity != null) {
                view = "redirect:/login";
            }else {
                view = "/admin/register";
                mv.addObject("alert","등록에 실패하였습니다.");
            }
        }
        mv.setViewName(view);
        return mv;
    }

    @GetMapping("login")
    public ModelAndView login(ModelAndView mv){

        mv.setViewName("/admin/login");

        return mv;
    }

    @PostMapping("login")
    public ModelAndView postlogin(ModelAndView mv,AdminDTO admin){

        AdminEntity adminEntity = adminService.find(admin);
        mv.setViewName("/admin/login");
        mv.addObject("alert","로그인에 실패하였습니다");
        if(adminEntity != null){
            mv.clear();
            super.setAdminsession(adminEntity);
            mv.setViewName("redirect:/index");
        }
        return mv;
    }
}
