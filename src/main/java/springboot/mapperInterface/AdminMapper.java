package springboot.mapperInterface;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import springboot.model.admin.AdminDTO;

@Mapper
@Repository
public interface AdminMapper {

    @Select("SELECT * FROM admin_member WHERE email ='${email}' AND passwd ='${passwd}' ")
    AdminDTO findUser(AdminDTO dto);
}
