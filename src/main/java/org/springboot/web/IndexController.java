package org.springboot.web;

import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springboot.config.auth.LoginUser;
import org.springboot.config.auth.dto.SessionUser;
import org.springboot.service.posts.PostsService;
import org.springboot.web.dto.PostsResponseDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user){
        model.addAttribute("posts", postsService.findAllDesc());

        if(user!=null){
            model.addAttribute("user", user.getName()); // window의 경우 userName으로 넘기게 되면 window의 환경변수 userName이 넘어가기 때문에 로컬의 user 이름이 넘어감.
            // 그래서 userName이 아닌 다른 이름의 변수를 사용하여 넘겨줘야함.
        }

        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model){
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);
        return "posts-update";
    }


}
