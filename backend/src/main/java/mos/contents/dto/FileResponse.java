package mos.contents.dto;

import mos.contents.entity.SavedFile;

public record FileResponse(Long id, Long mogako_id, String filename, String url) {

    public static FileResponse from(SavedFile savedFile) {
        return new FileResponse(savedFile.getId(), savedFile.getMogako().getId(), savedFile.getFilename(), savedFile.getUrl());
    }
}
