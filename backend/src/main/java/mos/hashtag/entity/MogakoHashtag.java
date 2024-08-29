package mos.hashtag.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mos.common.entity.BaseTimeEntity;
import mos.mogako.entity.Mogako;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MogakoHashtag extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Mogako mogako;

    @ManyToOne(fetch = FetchType.LAZY)
    private Hashtag hashtag;

    private MogakoHashtag(Mogako mogako, Hashtag hashtag) {
        this.mogako = mogako;
        this.hashtag = hashtag;
    }

    public static MogakoHashtag of(Mogako mogako, Hashtag hashtag) {
        return new MogakoHashtag(mogako, hashtag);
    }
}
