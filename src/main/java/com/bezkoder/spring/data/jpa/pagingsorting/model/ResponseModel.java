package com.bezkoder.spring.data.jpa.pagingsorting.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class ResponseModel<T> implements Serializable {
    private List<T> elements;
    private int pageSize;
    private long pageNumber;
    private long totalPages;
    private long totalElements;
}
