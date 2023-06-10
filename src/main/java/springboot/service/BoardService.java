package springboot.service;


import groovy.util.logging.Log4j2;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import springboot.model.board.BoardDTO;
import springboot.model.board.BoardEntity;
import springboot.model.board.BoardRepository;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository repository;

    public Page<BoardDTO> findPage(Pageable pageable){
        return repository.findAll(pageable).map(boardEntity -> {
            BoardDTO dto = new BoardDTO();
            dto.setSeq(boardEntity.getSeq());
            dto.setBody(boardEntity.getBody());
            dto.setFileNames(boardEntity.getFileNames());
            dto.setSubjuect(boardEntity.getSubject());
            return dto;
        });
    }
}
