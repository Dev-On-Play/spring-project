package mos.mogako.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mos.category.entity.Category;
import mos.category.exception.CategoryNotFoundException;
import mos.category.repository.CategoryRepository;
import mos.mogako.dto.CreateMogakoRequest;
import mos.mogako.dto.MogakoResponse;
import mos.mogako.dto.MogakosResponse;
import mos.mogako.dto.UpdateMogakoRequest;
import mos.mogako.entity.Mogako;
import mos.mogako.exception.MogakoNotFoundException;
import mos.mogako.repository.MogakoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Transactional
@Service
public class MogakoService {

    private final MogakoRepository mogakoRepository;
    private final CategoryRepository categoryRepository;

    public Long createMogako(CreateMogakoRequest request) {
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(CategoryNotFoundException::new);

        Mogako createdMogako = Mogako.createNewMogako(request.name(), request.summary(), category,
                request.startDate(), request.endDate(),
                request.participantLimit(), request.minimumParticipantCount(),
                request.detailContent());
        mogakoRepository.save(createdMogako);

        return createdMogako.getId();
    }

    public MogakoResponse findMogako(Long id) {
        Mogako mogako = mogakoRepository.findById(id)
                .orElseThrow(MogakoNotFoundException::new);
        return MogakoResponse.from(mogako);
    }

    public Long updateMogako(Long mogakoId, UpdateMogakoRequest request) {
        Mogako mogako = mogakoRepository.findById(mogakoId)
                .orElseThrow(MogakoNotFoundException::new);
        // todo : 카테고리의 변경이 없으면 db 조회 안하는 방향으로 리팩토링
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(CategoryNotFoundException::new);

        mogako.update(request.name(), request.summary(), category,
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
