package springboot.service;


import groovy.util.logging.Log4j2;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.thymeleaf.util.StringUtils;
import springboot.model.board.BoardDTO;
import springboot.model.board.BoardEntity;
import springboot.model.board.BoardRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service
public class BoardService extends BaseService{

    @Value("${filePath}")
    private String uploadPath;
    private final BoardRepository repository;



    public Page<BoardDTO> findPage(Pageable pageable){
        return repository.findAll(pageable).map(boardEntity -> {
            BoardDTO dto = new BoardDTO();
            dto.setSeq(boardEntity.getSeq());
            dto.setBody(boardEntity.getBody());
            dto.setRegUser(boardEntity.getRegUser());
            dto.setFileNames(boardEntity.getFileNames());
            dto.setSubject(boardEntity.getSubject());
            dto.setCreateDate(boardEntity.getCreateDate());
            dto.setModiDate(boardEntity.getModiDate());
            return dto;
        });
    }

    @Transactional
    public BoardEntity insert(BoardDTO dto, MultipartHttpServletRequest request) throws Exception{

        List<MultipartFile> list = request.getFiles("files");

        List<String> fileNames = new ArrayList<>();
       /* System.out.println(list ==null);
        System.out.println(list.size());*/
        if(list != null && list.size() >0) {
            for (MultipartFile file : list) {
                if(StringUtils.isEmpty(file.getOriginalFilename()))
                    continue;
                String filePath = uploadPath + file.getOriginalFilename();
                fileNames.add(file.getOriginalFilename());
                File savefile = new File(filePath);
                savefile.mkdirs();
                file.transferTo(savefile);
            }
            dto.setFileNames(fileNames);
        }

        return repository.saveAndFlush(dto.toEntity());
    }


    public BoardDTO findone(Long id){
        BoardEntity entity = repository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id="+id));
        BoardDTO dto = new BoardDTO(entity);
        return dto;
    }

    @Transactional
    public BoardEntity update(BoardDTO dto,MultipartHttpServletRequest request) throws Exception{
        Long id = dto.getSeq();
        BoardEntity entity = repository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시글이 업습니다. id="+id));

        if(!entity.getRegUser().equals(super.getusession().getEmail())){
            throw new Exception("아이디가 달라");
        }

        List<String>requestFileNmaes = dto.getFileNames();
        List<String>requestFileNmae1 = dto.getFileNames();

        List<MultipartFile> list = request.getFiles("files");

        if(list != null && list.size() >0) {
            if(requestFileNmaes ==null)
                requestFileNmaes = new ArrayList<>();
            for (MultipartFile file : list) {
                if(StringUtils.isEmpty(file.getOriginalFilename()))
                    continue;
                String filePath = uploadPath + file.getOriginalFilename();
                requestFileNmaes.add(file.getOriginalFilename());
                File savefile = new File(filePath);
                savefile.mkdirs();
                file.transferTo(savefile);
            }
            dto.setFileNames(requestFileNmaes);
        }

        if(dto.getFileNames() != null && dto.getFileNames().size() >3){
            return null;
        }


        List<String>fileNames = entity.getFileNames();
        if(requestFileNmae1 != null && requestFileNmae1.size() > 0) {
            List<String> deleteFileNames = fileNames.stream().filter(s -> !requestFileNmae1.contains(s)).collect(Collectors.toList());
            if (deleteFileNames != null && deleteFileNames.size() > 0) {
                for(String fileName : deleteFileNames){
                    File file = new File(uploadPath+fileName);
                    if(file.exists()){
                        file.delete();
                    }
                }
            }
        }else {
            if (fileNames != null && fileNames.size() > 0) {
                for(String fileName : fileNames){
                    File file = new File(uploadPath+fileName);
                    if(file.exists()){
                        file.delete();
                    }
                }
            }
        }

        entity.update(dto.getSubject(),dto.getBody(),dto.getFileNames());

        return entity;
    }

    public void delete(Long id)throws Exception{
        BoardDTO dto = this.findone(id);
    if(!dto.getRegUser().equals(super.getusession().getEmail())){
            throw new Exception("아이디가 달라");
        }
        repository.deleteById(id);
    }
}
