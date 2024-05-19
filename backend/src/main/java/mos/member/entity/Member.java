package mos.member.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mos.common.BaseTimeEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;
    private String email;
    private String introduction;
    private String profile;
    private Double credibility;

    public Member(String nickname, String email, String introduction, String profile, Double credibility) {
        this.nickname = nickname;
        this.email = email;
        this.introduction = introduction;
        this.profile = profile;
        this.credibility = credibility;
    }

    public static Member createNewMember(String nickname, String email, String introduction,
                                         String profile, double credibility) {
        return new Member(nickname, email, introduction, profile, credibility);
    }

}
