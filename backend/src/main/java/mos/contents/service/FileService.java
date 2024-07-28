package mos.contents.service;

import lombok.RequiredArgsConstructor;
import mos.contents.dto.CreateFileRequest;
import mos.contents.dto.FileResponse;
import mos.contents.dto.FilesResponse;
import mos.contents.entity.SavedFile;
import mos.contents.repository.FileRepository;
import mos.mogako.entity.Mogako;
import mos.mogako.repository.MogakoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Transactional
@Service
public class FileService {
    @Autowired
    protected FileRepository fileRepository;
    @Autowired
    protected MogakoRepository mogakoRepository;

    public FileResponse findFile(Long id) {
        SavedFile savedFile = fileRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 File id 입니다."));
        return FileResponse.from(savedFile);
    }

    public FilesResponse findFilesByMogakoId(Long mogako_id, Pageable pageable) {
        Mogako mogako = mogakoRepository.getReferenceById(mogako_id);
        Page<SavedFile> files = fileRepository.findAllByMogako(mogako, pageable);
        return FilesResponse.from(files);
    }

    public Long CreateFile(CreateFileRequest request, MultipartFile multipartFile) {
        SavedFile createdFile = null;
        Mogako mogako = mogakoRepository.getReferenceById(request.mogako_id());
        String url = null;

        url = uploadFile(multipartFile);

        SavedFile savedFile = SavedFile.createNewFile(mogako, request.fileName(), url);
        createdFile = fileRepository.save(savedFile);
        return savedFile.getId();
    }

    public String uploadFile(MultipartFile multipartFile) {
        //TODO: 파일 저장소 저장 방식 결정 필요
        return "fileurl";
    }
}
