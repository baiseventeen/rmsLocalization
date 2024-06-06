package cn.gst.vo;

import lombok.Data;

import java.util.List;

/**
 * 分页统一返回类型
 * 分页时请使用pagehelper
 * @author lzx
 */
@Data
public class PageBean<T> {
    private Integer totalPages;//总页数
    private Long totalRows;//总数据行数
    private List<T> rowData;//分页数据，泛型T
}
