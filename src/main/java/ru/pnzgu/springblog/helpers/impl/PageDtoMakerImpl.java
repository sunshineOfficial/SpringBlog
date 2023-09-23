package ru.pnzgu.springblog.helpers.impl;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import ru.pnzgu.springblog.dto.common.PageDto;
import ru.pnzgu.springblog.helpers.PageDtoMaker;

import java.util.function.Function;

@Component
public class PageDtoMakerImpl<E, D> implements PageDtoMaker<E, D> {
    @Override
    public PageDto<D> makePageDto(Page<E> page, Function<E, D> mapFunction) {
        var content = page.getContent().stream().map(mapFunction).toList();
        
        return new PageDto<>(content, page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages(), page.isLast());
    }
}
