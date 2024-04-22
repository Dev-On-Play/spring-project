package mos.mogako.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mos.mogako.dto.CreateMogakoRequest;
import mos.mogako.entity.Mogako;
import mos.mogako.repository.MogakoRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Transactional
@Service
public class MogakoService {

    private final MogakoRepository mogakoRepository;

    public Long createMogako(CreateMogakoRequest request) {
        Mogako createdMogako = Mogako.createNewMogako(request.name(), request.summary(),
                request.startDate(), request.endDate(),
                request.participantLimit(), request.minimumParticipantCount(),
                request.detailContent());
        mogakoRepository.save(createdMogako);

        return createdMogako.getId();
    }
}
