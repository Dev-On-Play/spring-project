package mos.contents.repository;

import mos.contents.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    Page<Comment> findAllByMogako_Id(Long mogako_id, Pageable pageable);

}
