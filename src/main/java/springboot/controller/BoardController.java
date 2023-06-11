package springboot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;
import springboot.model.board.BoardDTO;
import springboot.model.board.BoardEntity;
import springboot.service.BoardService;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.lang.reflect.Field;
import java.util.List;


@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class BoardController extends BaseController {

    private final BoardService boardService;

    @GetMapping("")
    public ModelAndView board(ModelAndView mv, @PageableDefault(size = 10, page = 1) @SortDefault(sort = {"seq"}, direction = Sort.Direction.DESC) Pageable pageable){

        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber() -1 , pageable.getPageSize(), pageable.getSort());
        Page<BoardDTO> boardList = boardService.findPage(pageRequest);

        mv.addObject("list",boardList);

        mv.setViewName("/board/list");

        return mv;
    };


    @GetMapping("/write")
    public ModelAndView boardwirte(ModelAndView mv,@RequestParam int page) {

        mv.setViewName("/board/write");
        mv.addObject("page",page);
        return mv;
    }

    @PostMapping("/proc")
    public ModelAndView boardProc(ModelAndView mv, @RequestParam(required = true) int page,BoardDTO boardDTO, MultipartHttpServletRequest request, HttpServletResponse response) throws Exception{
        String status =(String)request.getParameter("boardstatus");



        Field[] fields = boardDTO.getClass().getDeclaredFields();
        for(Field field : fields){
            field.setAccessible(true);
            if(field.getType() == String.class){
                if(field.get(boardDTO) ==null || field.get(boardDTO).toString().trim().equals("")){
                    mv.addObject("alert","빈값입니다.");
                    mv.addObject("script","history.back();");
                    mv.setViewName("/alert/alert");
                    return mv;
                }
            }
        }

        if(boardDTO.getFileNames() != null && boardDTO.getFileNames().size() > 0){
            List<String>list = boardDTO.getFileNames();
            for(String i : list){
                System.out.println(i);
            }
        }

        BoardEntity entity = null;
        switch (status){
            case "i" :
                entity = boardService.insert(boardDTO,request);
                break;
            case "u":
                entity = boardService.update(boardDTO,request);
            default:
                break;
        }
        mv.setViewName("redirect:/board/view?page="+page+"&id="+entity.getSeq());
        return mv;
    }

    @GetMapping("/view")
    public ModelAndView view(ModelAndView mv,@RequestParam(required = true) int page, @RequestParam(required = true)Long id) throws Exception{

        BoardDTO dto = boardService.findone(id);
        mv.addObject("view",dto);
        mv.setViewName("/board/view");
        return mv;
    }

    @GetMapping("/update")
    public ModelAndView update(ModelAndView mv,@RequestParam(required = true) int page, @RequestParam(required = true)Long id) throws Exception{

        BoardDTO dto = boardService.findone(id);
        if(!dto.getRegUser().equals(super.getusession().getEmail())){
            mv.addObject("alert","작성자가 아닙니다.");
            mv.addObject("script","history.back();");
            mv.setViewName("/alert/alert");
            return mv;
        }
        mv.addObject("view",dto);
        mv.setViewName("/board/update");
        return mv;
    }

    @PostMapping("/delete")
    public ModelAndView delete(ModelAndView mv,@RequestParam(required = true) int page, @RequestParam(required = true)Long id){
        try {
            boardService.delete(id);
            mv.setViewName("redirect:/board?page="+page);
            return mv;
        }catch (Exception e){
            mv.addObject("alert",e.getMessage());
            mv.addObject("script","history.back();");
            mv.setViewName("/alert/alert");
            return mv;
        }

    }
}
