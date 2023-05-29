package springboot.model.admin;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminDTO {

    private String email;
    private String passwd;
    private String firstName;
    private String lastName;
    private String confirmpasswd;


    public AdminEntity toentitiy(){
       return AdminEntity.builder().email(this.getEmail()).firstName(this.getFirstName()).lastName(this.getLastName())
                .passwd(this.getPasswd()).build();
    }
}
