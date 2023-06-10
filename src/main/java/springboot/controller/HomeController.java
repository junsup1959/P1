package springboot.controller;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;
import springboot.model.admin.AdminDTO;
import springboot.model.admin.AdminEntity;
import springboot.model.board.BoardDTO;
import springboot.model.board.BoardEntity;
import springboot.service.AdminService;
import springboot.service.BoardService;

import javax.websocket.server.ServerEndpoint;
import java.lang.reflect.Field;
import java.util.List;


@Log4j2
@RequestMapping("/")
@Controller
@RequiredArgsConstructor
public class HomeController extends BaseController {


    private final AdminService adminService;
    private final BoardService boardService;
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

        for(Field filed : fields){
            filed.setAccessible(true);
            Object value = filed.get(admin);
            if(value.getClass() == String.class){
                if(StringUtils.isEmpty((String)value)){
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
            super.setusession(adminEntity);
            mv.setViewName("redirect:/index");
        }
        return mv;
    }


    @GetMapping("board")
    public  ModelAndView board(ModelAndView mv,@PageableDefault(size = 10, page = 0) @SortDefault(sort = {"seq"}, direction = Sort.Direction.DESC) Pageable pageable){

        Page<BoardDTO> page = boardService.findPage(pageable);

        List<BoardDTO> list = page.getContent();

        int totalPages = page.getTotalPages();

        int currentNum = page.getNumber();


        mv.addObject("currentPage",currentNum);
        mv.addObject("totalPage",totalPages);
        mv.addObject("list",list);

        for (BoardDTO dto : list){
            System.out.println(dto.getSeq());
        }


        mv.setViewName("/admin/board");



        return mv;
    };
}
