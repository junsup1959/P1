package springboot.model.board;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import springboot.StringListConverter;
import springboot.model.BaseTimeEntity;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "boardInfo")
public class BoardEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq")
    private Long seq;

    @Column(name = "subject")
    private String subject;

    @Column(name = "body" ,columnDefinition = "TEXT")
    private String body;

    @Convert(converter = StringListConverter.class)
    @Column(name = "fileNames")
    private List<String> fileNames;

    @Column(name = "regUser")
    private String regUser;
    @Builder
    public BoardEntity(Long seq,String subject, String body, List<String> fileNames,String regUser){
        this.seq = seq;
        this.subject =subject;
        this.body = body;
        this.fileNames = fileNames;
        this.regUser = regUser;
    }


    public void update(String subject,String body,List<String> fileNames){
        this.body = body;
        this.subject = subject;
        this.fileNames = fileNames;
    }
}
