//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package mos.contents.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mos.contents.dto.CreateFileRequest;
import mos.contents.dto.FileResponse;
import mos.contents.dto.FilesResponse;
import mos.contents.service.FileService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@Tag(name = "파일 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping({"/api/mogakos/{mogako_id}/files"})
public class FilesController {

    private final FileService fIleService;

    @Operation(summary = "파일 리스트 조회")
    @GetMapping("/")
    public ResponseEntity<FilesResponse> findFiles(@PathVariable Long mogako_id, Pageable pageable) {
        FilesResponse filesResponse = fIleService.findFilesByMogakoId(mogako_id, pageable);
        return ResponseEntity.ok(filesResponse);
    }

    @Operation(summary = "단일 파일 상세정보 조회")
    @GetMapping("/{file_id}")
    public ResponseEntity<FileResponse> findFile(@PathVariable Long mogako_id, @PathVariable Long file_id, Pageable pageable) {
        FileResponse fileResponse = fIleService.findFile(mogako_id);
        return ResponseEntity.ok(fileResponse);
    }

    @Operation(summary = "파일 업로드")
    @PostMapping("/upload")
    public ResponseEntity<Long> uploadFile(@PathVariable Long mogako_id, @RequestPart MultipartFile multipartFile, @RequestPart CreateFileRequest createFileRequest) {
        Long createdFileId = fIleService.CreateFile(createFileRequest, multipartFile);
        return ResponseEntity.created(URI.create("api/mogakos/" + mogako_id + "/files/" + createdFileId)).build();
    }

}
