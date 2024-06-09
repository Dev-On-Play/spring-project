package mos.contents.repository;

import mos.contents.entity.Comment;
import mos.mogako.entity.Mogako;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findAllByMogako(Mogako mogako, Pageable pageable);

}
