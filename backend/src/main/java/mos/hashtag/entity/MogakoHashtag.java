package mos.hashtag.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mos.mogako.entity.Mogako;

@Entity
@Getter
@RequiredArgsConstructor
public class MogakoHashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Mogako mogako;

    @ManyToOne(fetch = FetchType.LAZY)
    private Hashtag hashtag;
}
