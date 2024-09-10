package mos.participant.repository;

import mos.participant.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    List<Participant> findByMogakoId(Long mogakoId);

}
