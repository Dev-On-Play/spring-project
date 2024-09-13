package mos.mogako.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mos.category.entity.Category;
import mos.category.exception.CategoryNotFoundException;
import mos.category.repository.CategoryRepository;
import mos.hashtag.entity.Hashtag;
import mos.hashtag.repository.HashtagRepository;
import mos.member.entity.Member;
import mos.member.exception.MemberNotFoundException;
import mos.member.repository.MemberRepository;
import mos.mogako.dto.CreateMogakoRequest;
import mos.mogako.dto.MogakoResponse;
import mos.mogako.dto.MogakosResponse;
import mos.mogako.dto.UpdateMogakoRequest;
import mos.mogako.entity.Mogako;
import mos.mogako.exception.MogakoNotFoundException;
import mos.mogako.repository.MogakoRepository;
import mos.participant.entity.Participant;
import mos.participant.repository.ParticipantRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class MogakoService {

    private final MemberRepository memberRepository;
    private final MogakoRepository mogakoRepository;
    private final CategoryRepository categoryRepository;
    private final HashtagRepository hashtagRepository;
    private final ParticipantRepository participantRepository;


    public Long createMogako(Long authMemberId, CreateMogakoRequest request) {
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(CategoryNotFoundException::new);
        List<Hashtag> hashtags = hashtagRepository.findAllById(request.hashtagIds());

        Mogako createdMogako = Mogako.createNewMogako(request.name(), request.summary(), category, hashtags,
                request.startDate(), request.endDate(),
                request.participantLimit(), request.minimumParticipantCount(),
                request.detailContent());
        mogakoRepository.save(createdMogako);
        createHost(authMemberId, createdMogako);

        return createdMogako.getId();
    }

    private void createHost(Long authMemberId, Mogako createdMogako) {
        Member authMember = memberRepository.findById(authMemberId)
                .orElseThrow(MemberNotFoundException::new);
        Participant host = Participant.createNewParticipant(authMember, createdMogako, true);
        participantRepository.save(host);
    }

    public MogakoResponse findMogako(Long id) {
        Mogako mogako = mogakoRepository.findById(id)
                .orElseThrow(MogakoNotFoundException::new);
        return MogakoResponse.from(mogako);
    }

    public Long updateMogako(Long mogakoId, UpdateMogakoRequest request) {
        // todo : mogakoHashtag fetchJoin 해오기
        Mogako mogako = mogakoRepository.findById(mogakoId)
                .orElseThrow(MogakoNotFoundException::new);
        Category updatedCategory = categoryRepository.findById(request.categoryId())
                .orElseThrow(CategoryNotFoundException::new);
        List<Hashtag> updatedHashtags = hashtagRepository.findAllById(request.hashtagIds());

        mogako.update(request.name(), request.summary(), updatedCategory, updatedHashtags,
                request.startDate(), request.endDate(),
                request.participantLimit(), request.minimumParticipantCount(),
                request.detailContent());

        return mogako.getId();
    }

    public MogakosResponse findAllWithFiltering(List<Long> categoryIds, List<Long> hashtagIds, Pageable pageable) {
        Page<Mogako> mogakos = mogakoRepository.findAllWithFiltering(categoryIds, hashtagIds, pageable);
        return MogakosResponse.from(mogakos);
    }
}
