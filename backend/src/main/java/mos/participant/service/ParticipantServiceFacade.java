package mos.participant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ParticipantServiceFacade {

    private final ParticipantService participantService;

    public Long participate(Long memberId, Long mogakoId) {
        while (true) {
            try {
                return participantService.participate(memberId, mogakoId);
            } catch (ObjectOptimisticLockingFailureException le) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
        return -1L;
    }
}
