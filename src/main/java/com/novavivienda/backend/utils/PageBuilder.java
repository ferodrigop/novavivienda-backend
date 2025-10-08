package com.novavivienda.backend.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class PageBuilder {
    public static PageRequest buildPageRequest(
            Integer pageNumber,
            Integer pageSize,
            String sortBy,
            Sort.Direction sortDirection
    ) {
        int queryPageNumber;
        int queryPageSize;

        if (pageNumber != null && pageNumber > 0) {
            queryPageNumber = pageNumber - 1;
        } else {
            queryPageNumber = 0;
        }

        if (pageSize == null) {
            queryPageSize = 25;
        } else {
            if (pageSize > 1000) {
                queryPageSize = 1000;
            } else {
                queryPageSize = pageSize;
            }
        }

        return (sortBy == null || sortBy.isEmpty()) ?
                PageRequest.of(queryPageNumber, queryPageSize) :
                PageRequest.of(queryPageNumber, queryPageSize, Sort.by(sortDirection, sortBy));
    }
}
