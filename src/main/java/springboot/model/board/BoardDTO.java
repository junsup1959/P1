package springboot.model.board;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import springboot.model.admin.AdminEntity;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BoardDTO implements Serializable {

    private static Long serialVersionUID  = 362498820763181265L;

    private Long seq;

    private String subjuect;

    private String body;

    private List<String> fileNames;


    public BoardEntity toEntity(){
      return   BoardEntity.builder().seq(this.seq).subject(this.subjuect).body(this.body).fileNames(this.fileNames).build();
    }
}
