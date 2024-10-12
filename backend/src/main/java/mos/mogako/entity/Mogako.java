package mos.mogako.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mos.category.entity.Category;
import mos.common.entity.BaseTimeEntity;
import mos.hashtag.entity.Hashtag;
import mos.hashtag.entity.MogakoHashtag;
import mos.mogako.exception.ParticipantLimitExceededException;
import mos.participant.entity.Participant;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Mogako extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "mogako", orphanRemoval = true)
    @Cascade({CascadeType.PERSIST, CascadeType.REMOVE})
    private final List<Participant> participants = new ArrayList<>();

    // todo : 카테고리 엔티티 작업 완료되면 엔티티 구조 변경 및 연관관계 추가 + 테스트 수정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "mogako", orphanRemoval = true)
    @Cascade({CascadeType.PERSIST, CascadeType.REMOVE})
    private final List<MogakoHashtag> mogakoHashtags = new ArrayList<>();

    private String name;
    private String summary;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer participantLimit;
    private Integer currentParticipantCount;
    private Integer minimumParticipantCount;
    private String detailContent;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Version
    private Long version;

    private Mogako(String name, String summary, Category category, List<Hashtag> hashtags,
                   LocalDateTime startDate, LocalDateTime endDate,
                   Integer participantLimit, Integer currentParticipantCount, Integer minimumParticipantCount,
                   String detailContent, Status status) {
        this.category = category;
        addMogakoHashtags(hashtags);
        this.name = name;
        this.summary = summary;
        this.startDate = startDate;
        this.endDate = endDate;
        this.participantLimit = participantLimit;
        this.currentParticipantCount = currentParticipantCount;
        this.minimumParticipantCount = minimumParticipantCount;
        this.detailContent = detailContent;
        this.status = status;
    }

    public static Mogako createNewMogako(String name, String summary, Category category, List<Hashtag> hashtags,
                                         LocalDateTime startDate, LocalDateTime endDate,
                                         Integer participantLimit, Integer minimumParticipantCount,
                                         String detailContent) {
        return new Mogako(name, summary, category, hashtags, startDate, endDate,
                participantLimit, 0, minimumParticipantCount, detailContent, Status.RECRUITING);
    }

    private void addMogakoHashtags(List<Hashtag> hashtags) {
        hashtags.stream()
                .map(hashtag -> MogakoHashtag.of(this, hashtag))
                .forEach(this.mogakoHashtags::add);
    }

    public void update(String name, String summary, Category category, List<Hashtag> hashtags,
                       LocalDateTime startDate, LocalDateTime endDate,
                       int participantLimit, int minimumParticipantCount, String detailContent) {
        // todo : 해시태그 엔티티 수정 이후 해시태그 연관관계 변경도 추가하기
        this.name = name;
        this.summary = summary;
        this.category = category;
        updateMogakoHashtags(hashtags);
        this.startDate = startDate;
        this.endDate = endDate;
        this.participantLimit = participantLimit;
        this.minimumParticipantCount = minimumParticipantCount;
        this.detailContent = detailContent;
    }

    private void updateMogakoHashtags(List<Hashtag> updatedHashtags) {
        this.mogakoHashtags.clear();
        addMogakoHashtags(updatedHashtags);
    }

    public List<Hashtag> getHashtags() {
        return mogakoHashtags.stream()
                .map(MogakoHashtag::getHashtag)
                .toList();
    }

    public void participate(Participant participant) {
        if (currentParticipantCount >= this.participantLimit) {
            throw new ParticipantLimitExceededException();
        }
        currentParticipantCount++;
        this.participants.add(participant);
    }

    public int getParticipantCount() {
        return this.participants.size();
    }
}
