package mos.contents.service;

import lombok.RequiredArgsConstructor;
import mos.contents.dto.CreateFileRequest;
import mos.contents.dto.FileResponse;
import mos.contents.dto.FilesResponse;
import mos.contents.entity.SavedFile;
import mos.contents.exception.FileNotFoundException;
import mos.contents.repository.FileRepository;
import mos.mogako.entity.Mogako;
import mos.mogako.repository.MogakoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
                .orElseThrow(FileNotFoundException::new);
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

        url = uploadFile(multipartFile,request.mogako_id());

        SavedFile savedFile = SavedFile.createNewFile(mogako, request.fileName(), url);
        createdFile = fileRepository.save(savedFile);
        return createdFile.getId();
    }

    private String uploadFile(MultipartFile multipartFile, Long mogako_id) {
        //TODO: 파일 저장소 저장 방식 결정 필요
        String fileDir = "/upload/" + mogako_id;

        Path directoryPath = Paths.get(fileDir).toAbsolutePath().normalize();
        try {            // 디렉토리 생성
            Files.createDirectories(directoryPath);
            System.out.println(directoryPath);
        }catch (IOException e)
        {
            e.printStackTrace();
        }

        String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        String storePath = directoryPath.toAbsolutePath() +"/"+ filename;

        try {
            FileCopyUtils.copy(multipartFile.getInputStream(), new FileOutputStream(storePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return storePath;
    }

    public Resource loadFileAsResource(String fileurl) {
        try {
            Path filePath = Paths.get(fileurl).toAbsolutePath().normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if(resource.exists()) {
                return resource;
            }else {
                throw new FileNotFoundException();
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
