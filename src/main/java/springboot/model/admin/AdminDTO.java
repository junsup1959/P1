package springboot.model.admin;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class AdminDTO implements Serializable {

    private static Long serialVersionUID  = 362498820763181265L;

    private String email;
    private String passwd;
    private String firstName;
    private String lastName;
    private String confirmpasswd;


    public AdminEntity toentitiy(){
        System.out.println(this.email);
        System.out.println(this.passwd);
        System.out.println(this.firstName);
        System.out.println(this.lastName);
        System.out.println(this.confirmpasswd);

       return AdminEntity.builder().email(this.email).firstName(this.firstName).lastName(this.lastName)
                .passwd(this.passwd).build();
    }
}
