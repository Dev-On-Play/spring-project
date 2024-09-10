package mos.integration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.Cookie;
import jakarta.transaction.Transactional;
import mos.auth.domain.GoogleOauthWebClient;
import mos.auth.dto.OauthAccessTokenResponse;
import mos.auth.dto.OauthLoginRequest;
import mos.auth.dto.OauthUserProfileResponse;
import mos.auth.dto.TokenResponse;
import mos.integration.dto.LoginResponse;
import mos.integration.dto.MemberDto;
import mos.member.dto.MemberResponse;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
@AutoConfigureMockMvc
@Transactional
@SpringBootTest
class IntegrationTest {

    @Autowired
    protected EntityManager entityManager;

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    protected GoogleOauthWebClient googleOauthWebClient;

    @Autowired
    protected ObjectMapper objectMapper;

    public LoginResponse 구글_로그인(String name) throws Exception {
        OauthLoginRequest request = new OauthLoginRequest("google", "oauthLoginCode");
        String jsonRequest = objectMapper.writeValueAsString(request);

        given(googleOauthWebClient.requestOauthAccessToken(any(String.class)))
                .willReturn(new OauthAccessTokenResponse("mock-token-type", "mock-access-token",
                        "mock-scope"));
        given(googleOauthWebClient.requestOauthUserProfile(any(String.class)))
                .willReturn(new OauthUserProfileResponse(name, "mock-email", "mock-picture"));

        MvcResult result = mockMvc.perform(
                        post("/api/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequest))
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Cookie refreshToken = result.getResponse().getCookie("refreshToken");
        TokenResponse tokenResponse = objectMapper.readValue(jsonResponse, TokenResponse.class);
        return new LoginResponse(tokenResponse.accessToken(), refreshToken);
    }

    protected MemberDto createMember(String name) throws Exception {
        LoginResponse loginResponse = 구글_로그인(name);

        MvcResult result = mockMvc.perform(get("/api/members/me")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, loginResponse.createAuthorizationHeader())
        ).andReturn();

        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        MemberResponse response = objectMapper.readValue(jsonResponse, MemberResponse.class);

        return new MemberDto(response.id(), loginResponse);
    }
}
