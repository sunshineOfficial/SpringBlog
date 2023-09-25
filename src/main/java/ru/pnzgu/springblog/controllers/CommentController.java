package ru.pnzgu.springblog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pnzgu.springblog.dto.comment.ChangeCommentContentRequest;
import ru.pnzgu.springblog.dto.comment.CreateCommentRequest;
import ru.pnzgu.springblog.dto.comment.GetCommentResponse;
import ru.pnzgu.springblog.dto.common.PageDto;
import ru.pnzgu.springblog.services.CommentService;

import java.util.Optional;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    
    @PostMapping
    public ResponseEntity<Integer> create(@RequestBody CreateCommentRequest request) {
        return ResponseEntity.ok(commentService.create(request));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<GetCommentResponse> getById(@PathVariable int id) {
        return ResponseEntity.ok(commentService.getById(id));
    }
    
    @GetMapping
    public ResponseEntity<PageDto<GetCommentResponse>> getAll(
            @RequestParam(defaultValue = "0", required = false) int pageNumber,
            @RequestParam(defaultValue = "10", required = false) int pageSize,
            @RequestParam Optional<Integer> userId,
            @RequestParam Optional<Integer> postId,
            @RequestParam Optional<Boolean> published
    ) {
        if (userId.isPresent() && postId.isPresent() && published.isPresent())
            return ResponseEntity.ok(commentService.getAllByUserIdAndPostIdAndPublished(
                    pageNumber, pageSize, userId.get(), postId.get(), published.get()
            ));
        
        if (userId.isPresent() && postId.isPresent())
            return ResponseEntity.ok(commentService.getAllByUserIdAndPostId(
                    pageNumber, pageSize, userId.get(), postId.get()
            ));
        
        if (userId.isPresent() && published.isPresent())
            return ResponseEntity.ok(commentService.getAllByUserIdAndPublished(
                    pageNumber, pageSize, userId.get(), published.get()
            ));
        
        if (postId.isPresent() && published.isPresent())
            return ResponseEntity.ok(commentService.getAllByPostIdAndPublished(
                    pageNumber, pageSize, postId.get(), published.get()
            ));
        
        if (userId.isPresent())
            return ResponseEntity.ok(commentService.getAllByUserId(
                    pageNumber, pageSize, userId.get()
            ));
        
        if (postId.isPresent())
            return ResponseEntity.ok(commentService.getAllByPostId(
                    pageNumber, pageSize, postId.get()
            ));
        
        if (published.isPresent())
            return ResponseEntity.ok(commentService.getAllByPublished(
                    pageNumber, pageSize, published.get()
            ));
        
        return ResponseEntity.ok(commentService.getAll(pageNumber, pageSize));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<GetCommentResponse> changeContent(@PathVariable int id, @RequestBody ChangeCommentContentRequest request) {
        return ResponseEntity.ok(commentService.changeContent(id, request));
    }
    
    @PutMapping("/{id}/publish")
    public ResponseEntity<GetCommentResponse> publish(@PathVariable int id) {
        return ResponseEntity.ok(commentService.publish(id));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        commentService.delete(id);
        return ResponseEntity.ok("Comment deleted successfully");
    }
}
