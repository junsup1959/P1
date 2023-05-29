package springboot.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.model.admin.AdminDTO;
import springboot.model.admin.AdminEntity;
import springboot.model.admin.AdminRepository;

@RequiredArgsConstructor
@Service
public class AdminService {

    private final AdminRepository repository;

    @Transactional
    public AdminEntity insert(AdminDTO admin){
        return repository.save(admin.toentitiy());
    }
}
