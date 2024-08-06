package mos.contents.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import mos.category.entity.Category;
import mos.contents.dto.CommentsResponse;
import mos.contents.dto.CreateFileRequest;
import mos.contents.dto.FileResponse;
import mos.contents.dto.FilesResponse;
import mos.contents.entity.Comment;
import mos.contents.entity.SavedFile;
import mos.mogako.entity.Mogako;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@Transactional
@SpringBootTest
public class FileServiceTest {

    @Autowired
    private FileService fileService;
    @Autowired
    private EntityManager entityManager;
    Mogako mogako;
    Category category;
    private SavedFile savedFile;


    @BeforeEach
    void setup(){
        category = Category.createCategory("category1");
        mogako = Mogako.createNewMogako("samplemogakp","summary", category,
                LocalDateTime.now().plusDays(1L), LocalDateTime.now().plusDays(2L),
                8, 2,
                "detailcontent");
        savedFile = SavedFile.createNewFile(mogako,"파일이름","fileurl");
        entityManager.persist(category);
        entityManager.persist(mogako);
        entityManager.persist(savedFile);
        entityManager.flush();
        entityManager.clear();
    }
    @Test
    void 파일_생성() throws IOException {
        //given
        CreateFileRequest request = new CreateFileRequest(mogako.getId(),"filename");
        final String filepath = "C:\\Users\\soobi\\Pictures\\XL.jpg"; //로컬 파일 경로
        FileInputStream fileInputStream = new FileInputStream(filepath);
        MockMultipartFile image1 = new MockMultipartFile(
                "images", //name
                "XL.jpg", //originalFilename
                "jpg",
                fileInputStream
        );
        //when
        Long result = fileService.CreateFile(request,image1);

        assertThat(result).isNotNull();

    }
    @Test
    void 파일_단건_조회() throws Exception{
        FileResponse response = fileService.findFile(savedFile.getId());
        assertSoftly((softly)->{
            softly.assertThat(response.id()).isEqualTo(savedFile.getId());
        });
    }
    @Test
    void 모각코_id로_파일목록조회() throws Exception{
        int pageNumber = 1;
        int pageSize = 2;
        int totalElements = 10;

        for (int i = 1; i < totalElements; i++) {
            //TODO:member entity 완성 이후 service 수정하여 다시 테스트 예정
            entityManager.persist(SavedFile.createNewFile(mogako,"filename"+i,"fileurl"));
        }
        entityManager.flush();
        entityManager.clear();

        PageRequest request = PageRequest.of(pageNumber, pageSize);

        //when
        FilesResponse response = fileService.findFilesByMogakoId(mogako.getId(),request);

        //then
        assertSoftly((softly) -> {
            softly.assertThat(response.pageNumber()).isEqualTo(pageNumber);
            softly.assertThat(response.files().size()).isEqualTo(pageSize);
            softly.assertThat(response.totalPage()).isEqualTo(totalElements / pageSize);
        });

    }

}
