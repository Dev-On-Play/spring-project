package mos.mogako.dto;

import java.util.List;

public record MogakosResponse(Integer totalPage, List<MogakoResponse> mogakos) {
}
