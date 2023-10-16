package ru.pnzgu.springblog.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pnzgu.springblog.dto.common.PageDto;
import ru.pnzgu.springblog.dto.post.CreatePostRequest;
import ru.pnzgu.springblog.dto.post.GetPostResponse;
import ru.pnzgu.springblog.dto.post.UpdatePostRequest;
import ru.pnzgu.springblog.services.PostService;

import java.io.IOException;
import java.util.Optional;

/**
 * Контроллер постов.
 */
@RestController
@RequestMapping("/api/post")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    /**
     * Создает новый пост. 
     *
     * @param request запрос на создание поста 
     * @return ответ с кодом состояния 200 (ОК) и идентификатором созданного поста 
     */
    @PostMapping
    public ResponseEntity<Integer> create(@Valid @ModelAttribute CreatePostRequest request) throws IOException {
        return ResponseEntity.ok(postService.create(request));
    }

    /**
     * Получает пост по указанному идентификатору. 
     *
     * @param id идентификатор поста 
     * @return ответ с кодом состояния 200 (ОК), содержащим пост с указанным идентификатором 
     */
    @GetMapping("/{id}")
    public ResponseEntity<GetPostResponse> getById(@PathVariable int id) {
        return ResponseEntity.ok(postService.getById(id));
    }

    /**
     * Получает изображение поста.
     * 
     * @param id идентификатор поста
     * @return ответ с кодом состояния 200 (ОК), содержащим изображение поста с указанным идентификатором 
     */
    @GetMapping(value = "/{id}/image", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getPostImage(@PathVariable int id) {
        return ResponseEntity.ok(postService.getPostImage(id));
    }

    /**
     * Возвращает список постов с возможностью фильтрации по параметрам. 
     *
     * @param pageNumber номер страницы 
     * @param pageSize   количество постов на странице 
     * @param userId     идентификатор пользователя, чьи посты нужно отобразить 
     * @param published  флаг, указывающий, опубликованы ли посты 
     * @return ответ с кодом состояния 200 (ОК), содержащим список постов с учетом фильтрации 
     */
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

    /**
     * Обновляет пост по заданному идентификатору. 
     *
     * @param id      идентификатор поста 
     * @param request объект запроса на обновление поста
     * @return ответ с кодом состояния 200 (ОК), содержащим обновленный пост
     */
    @PutMapping("/{id}")
    public ResponseEntity<GetPostResponse> update(@PathVariable int id, @Valid @RequestBody UpdatePostRequest request) {
        return ResponseEntity.ok(postService.update(id, request));
    }

    /**
     * Выполняет публикацию поста с указанным идентификатором. 
     *
     * @param id идентификатор поста 
     * @return ответ с кодом состояния 200 (ОК), содержащим опубликованный пост
     */
    @PutMapping("/{id}/publish")
    public ResponseEntity<GetPostResponse> publish(@PathVariable int id) {
        return ResponseEntity.ok(postService.publish(id));
    }

    /**
     * Удаляет пост по указанному идентификатору. 
     *
     * @param id идентификатор поста 
     * @return ответ с сообщением об успешном удалении поста 
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        postService.delete(id);
        return ResponseEntity.ok("Post deleted successfully");
    }
}
