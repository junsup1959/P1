package springboot.model.admin;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<AdminEntity,Long> {

}
