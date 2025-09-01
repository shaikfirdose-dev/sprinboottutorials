package com.firdose.springSecurity.Security.service;

import com.firdose.springSecurity.Security.dto.PostDto;
import com.firdose.springSecurity.Security.entity.PostEntity;
import com.firdose.springSecurity.Security.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public PostDto createPost(PostDto postDto){
        PostEntity post = modelMapper.map(postDto, PostEntity.class);
        PostEntity savedPost = postRepository.save(post);
        return modelMapper.map(savedPost, PostDto.class);

    }
}
