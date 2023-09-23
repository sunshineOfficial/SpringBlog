package ru.pnzgu.springblog.services;

import ru.pnzgu.springblog.dto.comment.CreateCommentRequest;
import ru.pnzgu.springblog.dto.comment.DeleteCommentRequest;
import ru.pnzgu.springblog.dto.comment.GetCommentResponse;
import ru.pnzgu.springblog.dto.comment.PublishCommentRequest;
import ru.pnzgu.springblog.dto.common.PageDto;

public interface CommentService {
    int create(CreateCommentRequest request);
    GetCommentResponse getById(int userId, int postId);
    PageDto<GetCommentResponse> getAll(int pageNumber, int pageSize);
    PageDto<GetCommentResponse> getAllByPublished(int pageNumber, int pageSize, boolean published);
    PageDto<GetCommentResponse> getAllByUserId(int pageNumber, int pageSize, int userId);
    PageDto<GetCommentResponse> getAllByUserIdAndPublished(int pageNumber, int pageSize, int userId, boolean published);
    PageDto<GetCommentResponse> getAllByPostId(int pageNumber, int pageSize, int postId);
    PageDto<GetCommentResponse> getAllByPostIdAndPublished(int pageNumber, int pageSize, int postId, boolean published);
    GetCommentResponse changeContent(String content);
    GetCommentResponse publish(PublishCommentRequest request);
    void delete(DeleteCommentRequest request);
}
