package springboot.model.board;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.lang.NonNullFields;
import springboot.model.admin.AdminEntity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BoardDTO implements Serializable {

    private static Long serialVersionUID  = 362498820763181265L;

    @Min(value = 1 ,message = "시퀀스 값이 없습니다")
    private Long seq;

    @NonNull
    private String subject;

    @NonNull
    private String body;

    private List<String> fileNames;

    @NonNull
    private String regUser;

    private LocalDateTime createDate;

    private LocalDateTime modiDate;

    public BoardDTO(BoardEntity entity){
        this.seq = entity.getSeq();
        this.subject = entity.getSubject();
        this.fileNames = entity.getFileNames();
        this.body = entity.getBody();
        this.createDate = entity.getCreateDate();
        this.modiDate = entity.getModiDate();
        this.regUser = entity.getRegUser();
    }

    public BoardEntity toEntity(){
      return   BoardEntity.builder().seq(this.seq).subject(this.subject).body(this.body).fileNames(this.fileNames).regUser(this.regUser).build();
    }
}
