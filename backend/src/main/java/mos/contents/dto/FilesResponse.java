package mos.contents.dto;

import mos.contents.entity.SavedFile;
import org.springframework.data.domain.Page;

import java.util.List;

public record FilesResponse(int totalPage,int pageNumber, List<FileResponse> files) {
    public static FilesResponse from(Page<SavedFile> page){
        Page<FileResponse> responses = page.map(FileResponse::from);
        return new FilesResponse(responses.getTotalPages(), responses.getNumber(),responses.getContent()) ;
    }
}
