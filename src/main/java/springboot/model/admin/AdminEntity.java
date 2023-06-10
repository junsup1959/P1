package springboot.model.admin;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import springboot.model.BaseTimeEntity;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "adminMember")
public class AdminEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq")
    private Long seq;

    @Column(name = "email", unique = true ,length = 30, nullable = false)
    private String email;

    @Column(name = "passwd" , length = 255, nullable = false)
    private String passwd;

    @Column(name = "firstName" , length = 12, nullable = false)
    private String firstName;

    @Column(name = "lastName" ,length = 12, nullable = false)
    private String lastName;


    @Builder
    public AdminEntity(String email,String passwd,String firstName,String lastName){
        this.email =email;
        this.passwd = passwd;
        this.firstName = firstName;
        this.lastName = lastName;
    }
/*
    public void update(String email,String passwd){
        this.email = email;
        this.passwd = passwd;
    }*/
}
