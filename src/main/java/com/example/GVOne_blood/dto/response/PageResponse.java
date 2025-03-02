package com.example.GVOne_blood.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class PageResponse<T> implements Serializable {
    public int pageNo;
    public int pageSize;
    public int totalPage;
    public T items;
}
