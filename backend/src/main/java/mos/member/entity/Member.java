package mos.member.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mos.common.entity.BaseTimeEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    public static final String DEFAULT_INTRODUCTION = "안녕하세요. %s입니다.";
    public static final double DEFAULT_CREDIBILITY = 36.5;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;
    private String email;
    private String introduction;
    private String profile;
    private Double credibility;

    private Member(String nickname, String email,
                   String introduction, String profile, Double credibility) {
        this.nickname = nickname;
        this.email = email;
        this.introduction = introduction;
        this.profile = profile;
        this.credibility = credibility;
    }

    public static Member createNewMember(String nickname, String email, String profile) {
        return new Member(nickname, email, String.format(DEFAULT_INTRODUCTION, nickname),
                profile, DEFAULT_CREDIBILITY);
    }

    public void updateProfileImageUri(String updatedProfileImageUri) {
        if (!this.profile.equals(updatedProfileImageUri)) {
            this.profile = updatedProfileImageUri;
        }
    }

    public void update(String nickname, String introduction, String profile) {
        this.nickname = nickname;
        this.introduction = introduction;
        this.profile = profile;
    }
}
