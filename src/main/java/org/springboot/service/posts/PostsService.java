package org.springboot.service.posts;


import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springboot.domain.posts.PostsRepository;
import org.springboot.web.dto.PostsSaveRequestDto;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }


}
