package com.petbooking.bookingapp.core.filters;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter @Setter
@NoArgsConstructor
public class GenericFilters {

    public static final int DEFAULT_PAGE = 1;
    public static final int DEFAULT_SIZE = 10;
    public static final int MAX_SIZE     = 100;
    public static final String DEFAULT_SORT_BY = "id";
    public static final Sort.Direction DEFAULT_SORT_DIR = Sort.Direction.DESC;


    private Integer page;
    private Integer size;
    private Integer pageSize;

    private String sortBy;
    private Sort.Direction sortDirection;


    public int pageIndex() {
        int p = (page == null ? DEFAULT_PAGE : page);
        if (p < 1) p = 1;
        return p - 1;
    }

    public int safeSize() {
        Integer s = (size != null ? size : pageSize);
        if (s == null) s = DEFAULT_SIZE;
        if (s < 1) s = 1;
        if (s > MAX_SIZE) s = MAX_SIZE;
        return s;
    }

    public Sort buildSort() {
        String by = (sortBy == null || sortBy.isBlank()) ? DEFAULT_SORT_BY : sortBy;
        Sort.Direction dir = (sortDirection == null) ? DEFAULT_SORT_DIR : sortDirection;
        return Sort.by(new Sort.Order(dir, by));
    }

    public Pageable toPageable() {
        return PageRequest.of(pageIndex(), safeSize(), buildSort());
    }
}
