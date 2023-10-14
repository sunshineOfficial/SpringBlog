package ru.pnzgu.springblog.helpers.impl;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import ru.pnzgu.springblog.dto.common.PageDto;
import ru.pnzgu.springblog.helpers.PageDtoMaker;

import java.util.function.Function;

/**
 * Класс для создания PageDto.
 *
 * @param <E> Entity
 * @param <D> DTO
 */
@Component
public class PageDtoMakerImpl<E, D> implements PageDtoMaker<E, D> {
    /**
     * Создает PageDto.
     *
     * @param page        объект Page, представляющий страницу
     * @param mapFunction функция маппинга сущности в DTO
     * @return PageDto, содержащий объекты типа T
     */
    @Override
    public PageDto<D> makePageDto(Page<E> page, Function<E, D> mapFunction) {
        var content = page.getContent().stream().map(mapFunction).toList();
        
        return new PageDto<>(content, page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages(), page.isLast());
    }
}
