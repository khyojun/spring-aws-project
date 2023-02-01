package org.springboot.web;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = HelloController.class)
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc;


    @Test
    public void hello_리턴() throws Exception {
        String hello = "hello";
        mvc.perform(get("/hello")).
            andExpect(status().isOk()).
            andExpect(content().string(hello));
    }

    @Test
    public void responseDto_테스트() throws Exception {
        String name="hello";
        int amount=10000;
        mvc.perform(get("/hello/dto").param("name", name).param("amount", String.valueOf(amount))).
            andExpect(status().isOk()).
            andExpect(jsonPath("$.name").value(name)).
            andExpect(jsonPath("$.amount").value(amount));

        // 책에서의 내용과 달라짐 andExpect(jsonPath(("$.name"), is(name))) ->  andExpect(jsonPath("$.name").value(name)).
        // 레퍼런스 참고 : https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/result/JsonPathResultMatchers.html
        // 언젠지는 모르지만 is로 확인하지 않고 value로 확인해서 resultMatcher를 이용하여서 하는 방식으로 전환됨.
    }




}
