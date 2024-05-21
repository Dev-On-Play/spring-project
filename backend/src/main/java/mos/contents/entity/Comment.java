package mos.contents.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mos.common.BaseTimeEntity;
import mos.member.entity.Member;
import mos.mogako.entity.Mogako;

@Entity
@Getter
@RequiredArgsConstructor
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Mogako mogako;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Comment parent;

    @OneToMany(mappedBy = "parent")
    private List<Comment> comments = new ArrayList<>();

    private String contents;


    public Comment(Mogako mogako, Member member, Comment parent, String contents) {
        this.mogako = mogako;
        this.member = member;
        this.parent = parent;
        this.contents = contents;
    }

    public Comment(Mogako mogako, Member member, String contents) {
        this.mogako = mogako;
        this.member = member;
        this.contents = contents;
    }

    public static Comment createNewChildComment(Mogako mogako, Member member, Comment parent, String contents){
        return new Comment(mogako,member,parent,contents);
    }

    public static Comment createNewComment(Mogako mogako, Member member,String contents){
        return new Comment(mogako, member, contents);
    }
}
