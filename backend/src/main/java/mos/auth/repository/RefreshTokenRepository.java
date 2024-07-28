package mos.auth.repository;

import mos.auth.entity.RefreshToken;
import mos.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByUuid(UUID uuid);

    Optional<RefreshToken> findByMember(Member member);

    boolean existsByUuid(UUID uuid);
}
