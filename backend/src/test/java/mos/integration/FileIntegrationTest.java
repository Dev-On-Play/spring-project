package mos.integration;

import mos.category.entity.Category;
import mos.contents.dto.CreateFileRequest;
import mos.contents.dto.FileResponse;
import mos.contents.dto.FilesResponse;
import mos.contents.entity.SavedFile;
import mos.mogako.entity.Mogako;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import static org.assertj.core.api.Assertions.assertThat;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;


import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FileIntegrationTest extends  IntegrationTest{

    private Category category;
    private Mogako mogako;

    private SavedFile file1;
    private SavedFile file2;

    @BeforeEach
    void setUp(){
        category = Category.createCategory("카테고리1");
        mogako = Mogako.createNewMogako("samplemogakp","summary", category,
                LocalDateTime.now().plusDays(1L), LocalDateTime.now().plusDays(2L),
                8, 2,
                "detailcontent");
        file1 = SavedFile.createNewFile(mogako,"filename1","fileurl");
        file2 = SavedFile.createNewFile(mogako,"filename2","fileurl2");
        entityManager.persist(category);
        entityManager.persist(mogako);
        entityManager.persist(file1);
        entityManager.persist(file2);
        entityManager.flush();
        entityManager.clear();
    }
    @Test
    void 파일_생성_테스트() throws Exception{
        //given
        MockMultipartFile multipartFile = new MockMultipartFile("multipartFile","filename","png","<<<<<<<<FILEDATA>>>>>>>".getBytes());

        CreateFileRequest request = new CreateFileRequest(mogako.getId(),"fileName");
        String jsonRequest = objectMapper.writeValueAsString(request);

        Long beforeFileCount = entityManager.createQuery("select count(*) from SavedFile s", Long.class).getSingleResult();

        this.mockMvc.perform(multipart("/api/mogakos/"+mogako.getId()+"/files/upload")
                .file(multipartFile)

                .file(new MockMultipartFile("createFileRequest","","application/json",jsonRequest.getBytes(StandardCharsets.UTF_8)))
                .contentType(MULTIPART_FORM_DATA)
                .accept(APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isCreated());

        Long afterFileCount= entityManager.createQuery("select count(*) from SavedFile s", Long.class)
                .getSingleResult();
        assertThat(afterFileCount).isEqualTo(beforeFileCount+1);

    }
    @Test
    void 전체_파일_조회_테스트() throws Exception{
        //given
        LinkedMultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        int pageNum = 0;
        int pageSize = 1;
        params.add("page",String.valueOf(pageNum));
        params.add("size",String.valueOf(pageSize));

        //when
        MvcResult result = this.mockMvc.perform(get("/api/mogakos/"+mogako.getId()+"/files/")
                .queryParams(params)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        FilesResponse response = objectMapper.readValue(json,FilesResponse.class);
        List<FileResponse> files = response.files();

        SoftAssertions.assertSoftly((softly) -> {
            softly.assertThat(response.pageNumber()).isEqualTo(pageNum);
            softly.assertThat((files.size())).isEqualTo(pageSize);
        });

    }

    @Test
    void 파일_단건_조회_테스트() throws Exception{
        //given
        MvcResult result = this.mockMvc.perform(get("/api/mogakos/{mogako_id}/files/{file_id}",mogako.getId(),file1.getId())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        FileResponse response = objectMapper.readValue(json,FileResponse.class);

        SoftAssertions.assertSoftly((softly) -> {
            softly.assertThat(response.id()).isEqualTo(file1.getId());
            softly.assertThat(response.url()).isEqualTo(file1.getUrl());
        });
    }

}
