package mos.mogako.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mos.mogako.dto.CreateMogakoRequest;
import mos.mogako.dto.MogakoResponse;
import mos.mogako.dto.MogakosResponse;
import mos.mogako.dto.UpdateMogakoRequest;
import mos.mogako.entity.Mogako;
import mos.mogako.repository.MogakoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public MogakoResponse findMogako(Long id) {
        // todo : controllerAdvice 예외상황 처리 및 예외 정의
        Mogako mogako = mogakoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 모각코 id 입니다."));
        return MogakoResponse.from(mogako);
    }

    public Long updateMogako(Long mogakoId, UpdateMogakoRequest request) {
        // todo : controllerAdvice 예외상황 처리 및 예외 정의
        Mogako mogako = mogakoRepository.findById(mogakoId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 모각코 id 입니다."));
        mogako.update(request.categoryId(),
                request.name(), request.summary(),
                request.startDate(), request.endDate(),
                request.participantLimit(), request.minimumParticipantCount(),
                request.detailContent());

        return mogako.getId();
    }

    public MogakosResponse findAll(Pageable pageable) {
        Page<Mogako> mogakos = mogakoRepository.findAll(pageable);
        return MogakosResponse.from(mogakos);
    }
}