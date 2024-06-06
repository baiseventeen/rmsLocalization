package cn.gst.query;

import lombok.Data;

@Data
public class BaseQuery {
    private Integer pageNow = 1;
    private Integer pageSize = 5;
    private Integer startIndex;

    /**
     * 在getter方法中进行计算
     * sql中通过#{startIndex}来取值时会调用这个方法
     * 得到计算之后的结果
     * @return
     */
    public Integer getStartIndex() {
        return (pageNow-1)*pageSize;
    }
}
