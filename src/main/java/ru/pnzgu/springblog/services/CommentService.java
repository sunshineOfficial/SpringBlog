package ru.pnzgu.springblog.services;

import ru.pnzgu.springblog.dto.comment.*;
import ru.pnzgu.springblog.dto.common.PageDto;

public interface CommentService {
    int create(CreateCommentRequest request);
    GetCommentResponse getById(int id);
    PageDto<GetCommentResponse> getAll(int pageNumber, int pageSize);
    PageDto<GetCommentResponse> getAllByPublished(int pageNumber, int pageSize, boolean published);
    PageDto<GetCommentResponse> getAllByUserId(int pageNumber, int pageSize, int userId);
    PageDto<GetCommentResponse> getAllByUserIdAndPublished(int pageNumber, int pageSize, int userId, boolean published);
    PageDto<GetCommentResponse> getAllByPostId(int pageNumber, int pageSize, int postId);
    PageDto<GetCommentResponse> getAllByPostIdAndPublished(int pageNumber, int pageSize, int postId, boolean published);
    PageDto<GetCommentResponse> getAllByUserIdAndPostId(int pageNumber, int pageSize, int userId, int postId);
    PageDto<GetCommentResponse> getAllByUserIdAndPostIdAndPublished(int pageNumber, int pageSize, int userId, int postId, boolean published);
    GetCommentResponse changeContent(int id, ChangeCommentContentRequest request);
    GetCommentResponse publish(int id);
    void delete(int id);
}
