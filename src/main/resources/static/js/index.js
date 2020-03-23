$(function () {
    $(document).ready(function () {
        console.log("the index page load start");
        var pageCount = '${Page.pageCount}';//总页数
        var currentPage = '${Page.currentPage}';//当前页数
        var html = "";
        $("#pageSelect").html(html);
        if (currentPage > 5){
            html+="<li>\n" +
                "            <a href=\"\?pageNo=1\" aria-label=\"Previous\">\n" +
                "                <span aria-hidden=\"true\">&laquo;</span>\n" +
                "            </a>\n" +
                "        </li>  " ;
        }
        if (currentPage >= 2){
            html+="<li>\n" +
                "            <a href=\"\?pageNo="+(currentPage-1)+"\" aria-label=\"Previous\">\n" +
                "                <span aria-hidden=\"true\"><</span>\n" +
                "            </a>\n" +
                "        </li>";
        }
        if (currentPage+5>=currentPage+10){

        }
        html+="";
        console.log("the index page load complete");
    })
})