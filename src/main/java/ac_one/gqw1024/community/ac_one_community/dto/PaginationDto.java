package ac_one.gqw1024.community.ac_one_community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 存放当前页面的信息
 * List<QuestionDto> questionDtoList;   问题与问题作者的信息列表
 * List<CommentDto> commentDtoList;   扩展，用户与用户的回复列表
 *     Integer page;                    当前的页码
 *     Integer pageCount;               总页数
 *     List<Integer> pages;             当前页面中的页码
 *     boolean showPrevious;            是否显示【上一页】按钮
 *     boolean showNext;                是否显示【下一页】按钮
 *     boolean showFirstpage;           是否显示【第一页】按钮
 *     boolean showEndPage;             是否显示【最后一页】按钮
 */
@Data
public class PaginationDto {
    List<QuestionDto> questionDtoList;
    List<CommentDto> commentDtoList;
    Integer page;
    Integer totalPage;
    List<Integer> pages = new ArrayList<>();
    boolean showPrevious;
    boolean showNext;
    boolean showFirstpage;
    boolean showEndPage;

    /**
     * 设置后端实现页面分页
     * @param totalCount
     * @param page
     * @param pageSize
     */
    public void setPaginationDto(Integer totalCount, Integer page,Integer pageSize) {
        //计算总页数
        if(totalCount != 0) {
            Integer totalPage;             //默认每页显示五条记录
            if (totalCount % pageSize != 0) {//如果求余为零，则条件判断失败，页数不加1，当前页数已经足够
                totalPage = totalCount / pageSize + 1;//取余取不尽表示有多余数据，所以多加一页
            } else {
                totalPage = totalCount / pageSize;
            }

            this.totalPage = totalPage;//存入总页数

            //设置当前页码 并做 一些容错处理
            if(page>0&&page<=totalPage){
                this.page = page;
            }else if(page<1){
                this.page = 1;
            }else if(page>totalPage){
                this.page = totalPage;
            }

            //计算当前页面中需要显示的页码表
            this.pages.add(this.page);//为页码列表添加当前页
            for (int i = 1; i <=3 ; i++) {//总共最多可以显示的页码数为【当前页】加【当前页的前三页】加【当前页的后三页】，在此基础上再判断首页和尾页，从而确定当前需要显示的页码
                if(this.page-i>0){//计算【当前页】前面的页码
                    this.pages.add(0,this.page-i);//【当前页】前面的页数始终从队列头部添加，保证整体队列的有序
                }
                if(this.page+i<=totalPage){//计算【当前页】后面的页码
                    this.pages.add(this.page+i);//列表默认插入元素在尾部
                }
            }

            //是否显示上一页与下一页
            if (this.page == 1){//如果当前页数为第一页，则不显示【上一页】按钮
                this.showPrevious = false;
            }else{
                this.showPrevious = true;
            }
            if (this.page == this.totalPage){//如果当前页数为最后一页，则不显示【下一页】按钮
                this.showNext = false;
            }else{
                this.showNext = true;
            }

            //是否显示首页和尾页
            if (pages.contains(1)){//如果当前页面中的页码包含第一页，则不显示【第一页】按钮
                this.showFirstpage = false;
            }else{
                this.showFirstpage = true;
            }
            if (pages.contains(totalPage)){//如果当前页面中的页码包含最后一页，则不显示【最后一页】按钮
                this.showEndPage = false;
            }else{
                this.showEndPage = true;
            }
        }

    }
}
