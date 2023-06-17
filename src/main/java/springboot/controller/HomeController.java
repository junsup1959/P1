package springboot.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;
import springboot.model.admin.AdminDTO;
import springboot.model.admin.AdminEntity;
import springboot.service.AdminService;
import springboot.service.BoardService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;


@Log4j2
@RequestMapping("/")
@Controller
@RequiredArgsConstructor
public class HomeController extends BaseController {



    private final AdminService adminService;
    @GetMapping("")
    public ModelAndView main(ModelAndView mv){

        String vewName = super.getusession() == null ? "redirect:/login" : "redirect:/index";

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
    public ModelAndView postregister(ModelAndView mv, AdminDTO admin)throws Exception{

        String view = "";
        Field[] fields = admin.getClass().getDeclaredFields();

        for(Field field : fields){
            field.setAccessible(true);
            if(field.getType() == String.class){
                if(StringUtils.isEmpty((String)field.get(admin)) ||((String) field.get(admin)).trim().equals("")){
                    view = "/admin/register";
                    mv.addObject("alert","값이 비었습니다.");
                    mv.setViewName(view);
                    return mv;
                }
           }
        }


        if(admin.getConfirmpasswd().equals(admin.getPasswd())){
            AdminEntity adminEntity = adminService.insert(admin);
            if(adminEntity != null) {
                view = "redirect:/login";
            }else {
                view = "/admin/register";
                mv.addObject("alert","등록에 실패하였습니다.");
            }
        }else {
            view = "/admin/register";
            mv.addObject("alert","password and confirmpassword doesn't match");
        }
        mv.setViewName(view);
        return mv;
    }

    @GetMapping("login")
    public ModelAndView login(ModelAndView mv){

        String vewName = super.getusession() == null ? "/admin/login" : "redirect:/index";
        mv.setViewName(vewName);

        return mv;
    }

    @PostMapping("login")
    public ModelAndView postlogin(ModelAndView mv, AdminDTO admin, HttpServletRequest request){

        AdminEntity adminEntity = adminService.find(admin);
        mv.setViewName("/admin/login");
        mv.addObject("alert","로그인에 실패하였습니다");
        if(adminEntity != null){
            mv.clear();
            super.setusession(adminEntity);
            mv.setViewName("redirect:/index");
        }
        return mv;
    }
    @GetMapping("password")
    public  ModelAndView password(ModelAndView mv){

        mv.setViewName("/admin/password");

        return mv;
    }

    @PostMapping("password")
    public  ModelAndView password(ModelAndView mv,@RequestParam(required = true) String email){

       /* AdminEntity entity =adminService.findByEmail(email);
        if(entity ==null){
            mv.addObject("alert","존재하지 않는 이메일입니다.");
            mv.addObject("script","history.back();");
        }else {
            mv.addObject("alert","임의이 비밀번호로 변경되었습니다. 이메일을 확인하세요");
            mv.addObject("script","window.location.href='/login';");
        }*/
        mv.addObject("alert","개발중!!");
        mv.addObject("script","window.location.href='/login';");
        mv.setViewName("/alert/alert");

        return mv;
    }


    @GetMapping("tables")
    public ModelAndView tables(ModelAndView mv){

        mv.setViewName("/admin/tables");

        return mv;
    }

    @GetMapping("layout-static")
    public ModelAndView layout(ModelAndView mv){

        mv.setViewName("/admin/layout-static");

        return mv;
    }
    @GetMapping("layout-sidenav-light")
    public ModelAndView layout2(ModelAndView mv){

        mv.setViewName("/admin/layout-sidenav-light");

        return mv;
    }
}
