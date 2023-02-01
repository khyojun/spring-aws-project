package org.springboot.service.posts;


import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springboot.domain.posts.PostRepository;
import org.springboot.web.dto.PostsSaveRequestDto;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostRepository postRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        return postRepository.save(requestDto.toEntity()).getId();
    }


}
