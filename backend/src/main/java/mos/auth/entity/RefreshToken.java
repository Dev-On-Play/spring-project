package mos.auth.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mos.common.BaseTimeEntity;
import mos.member.entity.Member;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class RefreshToken extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(columnDefinition = "BINARY(16)")
    private UUID uuid;

    private LocalDateTime expireDateTime;

    public RefreshToken(Member member, UUID uuid, LocalDateTime expireDateTime) {
        this.member = member;
        this.uuid = uuid;
        this.expireDateTime = expireDateTime;
    }

    public static RefreshToken of(Member member, long expireLength) {
        return new RefreshToken(member, UUID.randomUUID(),
                LocalDateTime.now().plus(expireLength, ChronoUnit.MILLIS));
    }

    public void validateExpired() {
        if (expireDateTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException(); //todo : RefreshTokenExpiredException
        }
    }

    public RefreshToken updateExpireDateTime(long expireLength) {
        this.expireDateTime = LocalDateTime.now().plus(expireLength, ChronoUnit.MILLIS);
        return this;
    }

    public void updateUuidAndExpireDateTime(long expireLength) {
        this.uuid = UUID.randomUUID();
        this.expireDateTime = LocalDateTime.now().plus(expireLength, ChronoUnit.MILLIS);
    }
}

