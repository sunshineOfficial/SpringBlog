package ru.pnzgu.springblog.helpers;

import org.springframework.data.domain.Page;
import ru.pnzgu.springblog.dto.common.PageDto;

import java.util.function.Function;

/**
 * Интерфейс для создания PageDto.
 * 
 * @param <E> Entity
 * @param <D> DTO
 */
public interface PageDtoMaker<E, D> {
    /**
     * Создает PageDto.
     * 
     * @param page        объект Page, представляющий страницу
     * @param mapFunction функция маппинга сущности в DTO
     * @return PageDto, содержащий объекты типа T
     */
    PageDto<D> makePageDto(Page<E> page, Function<E, D> mapFunction);
}
