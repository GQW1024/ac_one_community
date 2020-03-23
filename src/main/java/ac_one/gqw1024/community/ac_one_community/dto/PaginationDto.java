package ac_one.gqw1024.community.ac_one_community.dto;

import lombok.Data;

import java.util.List;

/**
 * 存放当前页面的信息
 */
@Data
public class PaginationDto {
    List<QuestionDto> questionDtoList;
    Integer page;
    Integer pageCount;
    List<Integer> pages;
    boolean showPrevious;
    boolean showNext;
    boolean showFirstpage;
    boolean showEndPage;

    public void setPaginationDto(Integer totalCount, Integer page,Integer pageSize) {
        //计算总页数
        Integer pageCount;             //默认每页显示五条记录
        if((pageCount = totalCount%pageSize) != 0){//如果求余为零，则条件判断失败，页数不加1，当前页数已经足够
            pageCount = pageCount+1;//取余取不尽表示有多余数据，所以多加一页
        }
        this.page = page;//存入当前页数
        this.pageCount = pageCount;//存入总页数

        if (page == 1){//如果当前页数为第一页，则不显示【上一页】按钮
            this.showPrevious = false;
        }else{
            this.showPrevious = true;
        }
        if (page <= 5){//如果当前页数在前五页之中，则不显示【第一页】按钮
            this.showFirstpage = false;
        }else{
            this.showFirstpage = true;
        }
        if (page == this.pageCount){//如果当前页数为最后一页，则不显示【下一页】按钮
            this.showNext = false;
        }else{
            this.showNext = true;
        }
        if (page+5 <= this.pageCount){//如果当前页数在最后五页之中，则不显示【最后一页】按钮
            this.showEndPage = false;
        }else{
            this.showEndPage = true;
        }
    }
}
