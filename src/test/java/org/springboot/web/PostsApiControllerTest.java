package org.springboot.web;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springboot.domain.posts.Posts;
import org.springboot.domain.posts.PostsRepository;
import org.springboot.web.dto.PostsSaveRequestDto;
import org.springboot.web.dto.PostsUpdateRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;


    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup(){
        mvc= MockMvcBuilders.webAppContextSetup(context).
            apply(springSecurity()).build();
    }


    @After
    public void tearDown() throws Exception{
        postsRepository.deleteAll();
    }


    @Test
    @WithMockUser(roles="USER")
    public void Posts_등록() throws Exception{
        //given
        String title = "title";
        String content ="content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder().
            title(title).
            content(content).
            author("author").
            build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        //when
        /*ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto,
            Long.class);*/
             mvc.perform(post(url)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(new ObjectMapper().writeValueAsString(requestDto))).andExpect(status().isOk());

        //then
        /*Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseEntity.getBody()).isGreaterThan(0L);*/

        List<Posts> all = postsRepository.findAll();
        Assertions.assertThat(all.get(0).getTitle()).isEqualTo(title);
        Assertions.assertThat(all.get(0).getContent()).isEqualTo(content);

    }


    @Test
    @WithMockUser(roles="USER")
    public void Posts_수정() throws Exception{
        //given
        Posts savedPosts = postsRepository.save(Posts.builder().
            title("title").
            content("content").
            author("author").
            build()
        );

        Long updatedId = savedPosts.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder().
            title(expectedTitle).
            content(expectedContent).
            build();

        String url = "http://localhost:"+ port + "/api/v1/posts/" + updatedId;

        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(
            requestDto);

        //when
        mvc.perform(put(url)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(new ObjectMapper().writeValueAsString(requestDto))).andExpect(status().isOk());






       /* ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity,
            Long.class);*/

        //then

       /* Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseEntity.getBody()).isGreaterThan(0L);*/

        List<Posts> all = postsRepository.findAll();
        Assertions.assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        Assertions.assertThat(all.get(0).getContent()).isEqualTo(expectedContent);

    }

    @Test
    @WithMockUser(roles="USER")
    public void Posts_삭제() throws Exception {
        //given
        Posts savedPosts = postsRepository.save(Posts.builder().
            title("title").
            content("content").
            author("author").
            build()
        );

        Long deletedId = savedPosts.getId();
        String url = "http://localhost:"+ port + "/api/v1/posts/" + deletedId;

        //when
        /*restTemplate.delete(url);*/

        mvc.perform(delete(url)).andExpect(status().isOk());

        //then
        List<Posts> all = postsRepository.findAll();
        Assertions.assertThat(all.size()).isEqualTo(0);
    }

}