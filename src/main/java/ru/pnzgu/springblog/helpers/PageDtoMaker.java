package ru.pnzgu.springblog.helpers;

import org.springframework.data.domain.Page;
import ru.pnzgu.springblog.dto.common.PageDto;

import java.util.function.Function;

/**
 * @param <E> Entity
 * @param <D> DTO
 */
public interface PageDtoMaker<E, D> {
    PageDto<D> makePageDto(Page<E> page, Function<E, D> mapFunction);
}
