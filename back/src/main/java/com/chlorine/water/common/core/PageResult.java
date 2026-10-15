package com.chlorine.water.common.core;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
/**
 * @author chlorine
 * @version 1.0
 * @Date 2026-10-15
 * @Description
 */

@Data
public class PageResult<T> implements Serializable {
    private List<T> records;
    private long total;
    private long size;
    private long current;
    private long pages;

    public PageResult() {}

    public PageResult(List<T> records, long total, long size, long current, long pages) {
        this.records = records;
        this.total = total;
        this.size = size;
        this.current = current;
        this.pages = pages;
    }

}