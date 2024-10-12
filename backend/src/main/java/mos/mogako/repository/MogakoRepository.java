package mos.mogako.repository;

import jakarta.persistence.LockModeType;
import mos.mogako.entity.Mogako;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MogakoRepository extends JpaRepository<Mogako, Long>, MogakoRepositoryCustom {

    @Query("select m from Mogako m join fetch m.participants as p where m.id = :mogakoId")
    Optional<Mogako> findByIdWithParticipants(Long mogakoId);

    @Lock(LockModeType.OPTIMISTIC)
    @Query("select m from Mogako m where m.id = :mogakoId")
    Optional<Mogako> findByIdWithOptimisticLock(Long mogakoId);
}
