package mos.mogako.dto;

import mos.mogako.entity.Mogako;
import org.springframework.data.domain.Page;

import java.util.List;

public record MogakosResponse(List<MogakoResponse> mogakos, int totalPages, long totalElements, int pageNumber) {

    public static MogakosResponse from(Page<Mogako> page) {
        Page<MogakoResponse> responses = page.map(MogakoResponse::from);
        return new MogakosResponse(responses.getContent(),
                responses.getTotalPages(), responses.getTotalElements(), responses.getNumber());
    }
}
