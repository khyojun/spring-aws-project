package org.springboot.domain.posts;

import static org.junit.Assert.*;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @After
    public void cleanup(){
        postRepository.deleteAll();
    }


    @Test
    public void 게시글_저장_불러오기(){
        //given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        postRepository.save(Posts.builder().
            title(title).
            content(content).
            author("khyojun1104@gmail.com").
            build());

        //when
        List<Posts> all = postRepository.findAll();

        //then

        Posts posts = all.get(0);
        Assertions.assertThat(posts.getTitle()).isEqualTo(title);
        Assertions.assertThat(posts.getContent()).isEqualTo(content);

    }


}