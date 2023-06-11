package springboot.model.admin;


import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class AdminDTO implements Serializable {

    private static Long serialVersionUID  = 362498820763181265L;

    @NonNull
    private String email;
    @NonNull
    private String passwd;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private String confirmpasswd;


    public AdminEntity toentitiy(){
       /* System.out.println(this.email);
        System.out.println(this.passwd);
        System.out.println(this.firstName);
        System.out.println(this.lastName);
        System.out.println(this.confirmpasswd);*/

       return AdminEntity.builder().email(this.email).firstName(this.firstName).lastName(this.lastName)
                .passwd(this.passwd).build();
    }
}
