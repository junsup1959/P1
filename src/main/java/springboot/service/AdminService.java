package springboot.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
}
