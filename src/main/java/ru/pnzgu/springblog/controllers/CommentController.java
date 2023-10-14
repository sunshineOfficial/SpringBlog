package ru.pnzgu.springblog.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pnzgu.springblog.dto.comment.ChangeCommentContentRequest;
import ru.pnzgu.springblog.dto.comment.CreateCommentRequest;
import ru.pnzgu.springblog.dto.comment.GetCommentResponse;
import ru.pnzgu.springblog.dto.common.PageDto;
import ru.pnzgu.springblog.services.CommentService;

import java.util.Optional;

/**
 * Контроллер комментариев.
 */
@RestController
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * Создает новый комментарий. 
     *
     * @param request запрос на создание комментария 
     * @return ответ с кодом состояния 200 (ОК) и идентификатором созданного комментария 
     */
    @PostMapping
    public ResponseEntity<Integer> create(@Valid @RequestBody CreateCommentRequest request) {
        return ResponseEntity.ok(commentService.create(request));
    }

    /**
     * Получает комментарий по указанному идентификатору. 
     *
     * @param id идентификатор комментария 
     * @return ответ с кодом состояния 200 (ОК), содержащим комментарий с указанным идентификатором 
     */
    @GetMapping("/{id}")
    public ResponseEntity<GetCommentResponse> getById(@PathVariable int id) {
        return ResponseEntity.ok(commentService.getById(id));
    }

    /**
     * Возвращает список комментариев с возможностью фильтрации по параметрам. 
     *
     * @param pageNumber номер страницы 
     * @param pageSize   количество комментариев на странице 
     * @param userId     идентификатор пользователя, чьи комментарии нужно отобразить 
     * @param postId     идентификатор поста, к которому относятся комментарии 
     * @param published  флаг, указывающий, опубликованы ли комментарии 
     * @return ответ с кодом состояния 200 (ОК), содержащим список комментариев с учетом фильтрации 
     */
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

    /**
     * Изменяет содержимое комментария по заданному идентификатору. 
     *
     * @param id      идентификатор комментария 
     * @param request объект запроса на изменение содержимого комментария 
     * @return ответ с кодом состояния 200 (ОК), содержащим измененный комментарий
     */
    @PutMapping("/{id}")
    public ResponseEntity<GetCommentResponse> changeContent(@PathVariable int id, @Valid @RequestBody ChangeCommentContentRequest request) {
        return ResponseEntity.ok(commentService.changeContent(id, request));
    }

    /**
     * Выполняет публикацию комментария с указанным идентификатором. 
     *
     * @param id идентификатор комментария 
     * @return ответ с кодом состояния 200 (ОК), содержащим опубликованный комментарий
     */
    @PutMapping("/{id}/publish")
    public ResponseEntity<GetCommentResponse> publish(@PathVariable int id) {
        return ResponseEntity.ok(commentService.publish(id));
    }

    /**
     * Удаляет комментарий по указанному идентификатору. 
     *
     * @param id идентификатор комментария 
     * @return ответ с сообщением об успешном удалении комментария 
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        commentService.delete(id);
        return ResponseEntity.ok("Comment deleted successfully");
    }
}
