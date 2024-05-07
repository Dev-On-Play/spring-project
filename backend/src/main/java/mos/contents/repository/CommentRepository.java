package mos.contents.repository;

import mos.contents.entity.Comment;
import mos.mogako.entity.Mogako;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    Page<Comment> findAllByMogako(Mogako mogako, Pageable pageable);

}
