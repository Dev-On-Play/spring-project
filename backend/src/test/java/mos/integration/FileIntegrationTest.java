package mos.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import mos.category.entity.Category;
import mos.contents.dto.CreateFileRequest;
import mos.contents.dto.FileResponse;
import mos.contents.dto.FilesResponse;
import mos.contents.entity.SavedFile;
import mos.hashtag.entity.Hashtag;
import mos.integration.dto.MemberDto;
import mos.member.entity.Member;
import mos.mogako.entity.Mogako;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

public class FileIntegrationTest extends IntegrationTest {

    private Category category;
    private Mogako mogako;
    private MemberDto member1;

    private SavedFile file1;
    private SavedFile file2;

    @BeforeEach
    void setUp() throws Exception {
        category = Category.createCategory("카테고리1");
        Hashtag hashtag1 = Hashtag.createNewHashtag("hashtag1");
        Hashtag hashtag2 = Hashtag.createNewHashtag("hashtag2");
        List<Hashtag> hashtags = List.of(hashtag1, hashtag2);
        mogako = Mogako.createNewMogako("samplemogakp", "summary", category, hashtags,
                LocalDateTime.now().plusDays(1L), LocalDateTime.now().plusDays(2L),
                8, 2,
                "detailcontent");
        file1 = SavedFile.createNewFile(mogako,"filename1","C:\\Users\\soobi\\Pictures\\KakaoTalk_20230409_005121546_02.jpg");
        file2 = SavedFile.createNewFile(mogako, "filename2", "fileurl2");
        member1 = createMember("member1");

        entityManager.persist(category);
        entityManager.persist(hashtag1);
        entityManager.persist(hashtag2);
        entityManager.persist(mogako);
        entityManager.persist(file1);
        entityManager.persist(file2);
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void 파일_생성_테스트() throws Exception {
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

        MockMultipartFile multipartFile = new MockMultipartFile("multipartFile","filename","png","<<<<<<<<FILEDATA>>>>>>>".getBytes());

        String jsonRequest = objectMapper.writeValueAsString(request);

        Long beforeFileCount = entityManager.createQuery("select count(*) from SavedFile s", Long.class).getSingleResult();

        this.mockMvc.perform(multipart("/api/mogakos/" + mogako.getId() + "/files/upload")
                        .file(multipartFile)

                        .file(new MockMultipartFile("createFileRequest", "", "application/json", jsonRequest.getBytes(StandardCharsets.UTF_8)))
                        .contentType(MULTIPART_FORM_DATA)
                        .accept(APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .header(HttpHeaders.AUTHORIZATION, member1.createAuthorizationHeader()))
                .andExpect(status().isCreated());

        Long afterFileCount = entityManager.createQuery("select count(*) from SavedFile s", Long.class)
                .getSingleResult();
        assertThat(afterFileCount).isEqualTo(beforeFileCount + 1);

    }

    @Test
    void 전체_파일_조회_테스트() throws Exception {
        //given
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        int pageNum = 0;
        int pageSize = 1;
        params.add("page", String.valueOf(pageNum));
        params.add("size", String.valueOf(pageSize));

        //when
        MvcResult result = this.mockMvc.perform(get("/api/mogakos/" + mogako.getId() + "/files/")
                        .queryParams(params)
                        .contentType(APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, member1.createAuthorizationHeader()))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        FilesResponse response = objectMapper.readValue(json, FilesResponse.class);
        List<FileResponse> files = response.files();

        SoftAssertions.assertSoftly((softly) -> {
            softly.assertThat(response.pageNumber()).isEqualTo(pageNum);
            softly.assertThat((files.size())).isEqualTo(pageSize);
        });

    }

    @Test
    void 파일_단건_조회_테스트() throws Exception {
        //given
        MvcResult result = this.mockMvc.perform(get("/api/mogakos/{mogako_id}/files/{file_id}", mogako.getId(), file1.getId())
                        .contentType(APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, member1.createAuthorizationHeader()))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        FileResponse response = objectMapper.readValue(json, FileResponse.class);

        SoftAssertions.assertSoftly((softly) -> {
            softly.assertThat(response.id()).isEqualTo(file1.getId());
            softly.assertThat(response.url()).isEqualTo(file1.getUrl());
        });
    }
    @Test
    void 파일_다운로드_테스트() throws Exception {
        mockMvc.perform(get("/api/mogakos/{mogako_id}/files/download/{file_id}", mogako.getId(),file1.getId())
                        .header(HttpHeaders.AUTHORIZATION, member1.createAuthorizationHeader()))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"KakaoTalk_20230409_005121546_02.jpg\""))
                .andExpect(content().bytes(Files.readAllBytes(Paths.get("C:\\Users\\soobi\\Pictures\\KakaoTalk_20230409_005121546_02.jpg"))));
    }


}
