package mos.contents.dto;

import java.util.List;

public record FilesResponse(Integer totalPage, List<FileResponse> files) {
}
