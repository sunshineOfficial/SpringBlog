package ru.pnzgu.springblog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pnzgu.springblog.dto.common.PageDto;
import ru.pnzgu.springblog.dto.post.CreatePostRequest;
import ru.pnzgu.springblog.dto.post.GetPostResponse;
import ru.pnzgu.springblog.dto.post.UpdatePostRequest;
import ru.pnzgu.springblog.services.PostService;

import java.util.Optional;

@RestController
@RequestMapping("/api/post")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }
    
    @PostMapping
    public ResponseEntity<Integer> create(@RequestBody CreatePostRequest request) {
        return ResponseEntity.ok(postService.create(request));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<GetPostResponse> getById(@PathVariable int id) {
        return ResponseEntity.ok(postService.getById(id));
    }
    
    @GetMapping
    public ResponseEntity<PageDto<GetPostResponse>> getAll(
            @RequestParam(defaultValue = "0", required = false) int pageNumber,
            @RequestParam(defaultValue = "10", required = false) int pageSize,
            @RequestParam Optional<Integer> userId,
            @RequestParam Optional<Boolean> published
    ) {
        if (userId.isPresent() && published.isPresent())
            return ResponseEntity.ok(
                    postService.getAllByUserIdAndPublished(pageNumber, pageSize, userId.get(), published.get()));
        
        if (userId.isPresent())
            return ResponseEntity.ok(postService.getAllByUserId(pageNumber, pageSize, userId.get()));
        
        if (published.isPresent())
            return ResponseEntity.ok(postService.getAllByPublished(pageNumber, pageSize, published.get()));
        
        return ResponseEntity.ok(postService.getAll(pageNumber, pageSize));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<GetPostResponse> update(@PathVariable int id, @RequestBody UpdatePostRequest request) {
        return ResponseEntity.ok(postService.update(id, request));
    }
    
    @PutMapping("/{id}/publish")
    public ResponseEntity<GetPostResponse> publish(@PathVariable int id) {
        return ResponseEntity.ok(postService.publish(id));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        postService.delete(id);
        return ResponseEntity.ok("Post deleted successfully");
    }
}
