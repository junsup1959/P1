package springboot.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import springboot.mapperInterface.AdminMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.model.admin.AdminDTO;
import springboot.model.admin.AdminEntity;
import springboot.model.admin.AdminRepository;


@Log4j2
@RequiredArgsConstructor
@Service
public class AdminService{

    private final AdminRepository repository;

    private final AdminMapper adminMapper;

    @Transactional
    public AdminEntity insert(AdminDTO admin) {
        try {

            return repository.save(admin.toentitiy());
        }catch (Exception e){
            log.error(e);
            return null;
        }
    }
    public AdminEntity find(AdminDTO admin){
        return repository.findByEmailAndPasswd(admin.getEmail(),admin.getPasswd());
    }

  /*  public AdminEntity findByEmail(String email){
        AdminEntity entity= repository.findByEmail(email);
        if(entity == null)
            return null;
        String uid = UUID.randomUUID().toString().substring(0,7);
        entity.update(entity.getEmail(),uid);
        String subject = "비밀번호 초기화 입니다";
        String body = "변경된 임의의 비밀번호 : " + uid;
        MailUtil mail = new MailUtil(entity.getEmail(),subject,body);
        mail.Sendmail();


        return entity;
    }*/

    public AdminDTO findT(AdminDTO admin){
        return adminMapper.findUser(admin);
    }
}
