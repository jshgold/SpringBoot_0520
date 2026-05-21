package csh.back.post.post.controller;

import csh.back.post.post.entity.Post;
import csh.back.post.post.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test") // 테스트 환경에서는 test 프로파일을 활성화합니다.
@SpringBootTest // 스프링부트 테스트 클래스임을 나타냅니다.
@AutoConfigureMockMvc // MockMvc를 자동으로 설정합니다.
@Transactional // 각 테스트 메서드가 종료되면 롤백됩니다.
public class ApiV1PostControllerTest {
    @Autowired
    private MockMvc mvc; // MockMvc를 주입받습니다.

    @Autowired
    private PostService postService;

    // 회원가입 테스트
    @Test
    @DisplayName("글 쓰기")
    void t1() throws Exception {
        // 회원가입 요청을 보냅니다.
        ResultActions resultActions = mvc
                .perform(
                        post("/api/v1/posts/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                            "title": "제목",
                                            "content": "내용"
                                        }
                                        """)
                ).andDo(print()); // 응답결과를 출력합니다.

        Post post = postService.findLatest().get();
        long totalCount = postService.count();

        // 201 Created 상태코드 검증
        resultActions
                .andExpect(handler().handlerType(ApiV1PostController.class))
                .andExpect(handler().methodName("write"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.resultCode").value("201-1"))
                .andExpect(jsonPath("$.msg").value("%d번 글이 작성되었습니다.".formatted(post.getId())))
                .andExpect(jsonPath("$.data.totalCount").value(totalCount))
                .andExpect(jsonPath("$.data.post.id").value(post.getId()));
    }


    // 회원가입 테스트
    @Test
    @DisplayName("글 수정")
    void editTest() throws Exception {
        // 회원가입 요청을 보냅니다.
        ResultActions resultActions = mvc
                .perform(
                        put("/api/v1/posts/edit/{id}", 1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                            "title": "수정제목",
                                            "content": "수정내용"
                                        }
                                        """)
                ).andDo(print()); // 응답결과를 출력합니다.
        // 201 Created 상태코드 검증
        resultActions
                .andExpect(status().isCreated());
    }
}