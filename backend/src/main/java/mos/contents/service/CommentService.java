package mos.contents.service;

import lombok.RequiredArgsConstructor;
import mos.contents.dto.CommentsResponse;
import mos.contents.dto.CreateCommentRequest;
import mos.contents.entity.Comment;
import mos.contents.repository.CommentRepository;
import mos.member.entity.Member;
import mos.mogako.entity.Mogako;
import mos.mogako.repository.MogakoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class CommentService {
    @Autowired
    protected CommentRepository commentRepository;
    @Autowired
    protected MogakoRepository mogakoRepository;
    //TODO:memberRepository 완성되면  추가

    public CommentsResponse findAllByMogakoId (Long mogako_id, Pageable pageable) throws Exception{
        Mogako mogako = mogakoRepository.getReferenceById(mogako_id);
        Page<Comment> commentList =  commentRepository.findAllByMogako(mogako,pageable);
        return CommentsResponse.from(commentList);
    }

    public Long createComent(CreateCommentRequest request){
        Comment comment = null;

        Mogako mogako = mogakoRepository.getReferenceById(request.mogako_id());
        Member member = null;//TODO:member Repository 완성 수 getReperenceById 추가

        if(request.parent_id() == 0L){ //0depth댓글이면
            comment = Comment.createNewComment(mogako, member, request.contents());
        }
        else{
            Comment parents = commentRepository.getReferenceById(request.parent_id());
            comment = Comment.createNewChildComment(mogako, member, parents, request.contents());
        }

        return comment.getId();



    }



}
