package ru.pnzgu.springblog.services;

import ru.pnzgu.springblog.dto.common.PageDto;
import ru.pnzgu.springblog.dto.post.CreatePostRequest;
import ru.pnzgu.springblog.dto.post.GetPostResponse;
import ru.pnzgu.springblog.dto.post.UpdatePostRequest;

public interface PostService {
    int create(CreatePostRequest request);
    GetPostResponse getById(int id);
    PageDto<GetPostResponse> getAll(int pageNumber, int pageSize);
    PageDto<GetPostResponse> getAllByPublished(int pageNumber, int pageSize, boolean published);
    PageDto<GetPostResponse> getAllByUserId(int pageNumber, int pageSize, int userId);
    PageDto<GetPostResponse> getAllByUserIdAndPublished(int pageNumber, int pageSize, int userId, boolean published);
    GetPostResponse update(int id, UpdatePostRequest request);
    GetPostResponse publish(int id);
    void delete(int id);
}
