package com.buiquoctrieu.blog.payloads.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PagingResponse {
    private List<?> content;

    private int pageNumber;

    private int pageSize;

    private int totalPages;

    private long totalElements;

    private boolean lastPage;
}
