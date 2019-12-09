/**
 * Bootstrap Table Chinese translation
 * Author: Zhixin Wen<wenzhixin2010@gmail.com>
 */
(function ($) {
    'use strict';


    $.fn.bootstrapTable.locales['zh-CN'] = {
        formatLoadingMessage: function () {
            return '<div class="loading-table">' +
            '    <div><img src="'+ ctxPath +'/img/loading.gif"></div>' +
            '</div>';
        },
        formatRecordsPerPage: function (pageNumber) {
            // return '每页显示 ' + pageNumber + ' 条记录';
            return '' + pageNumber + '';
        },
        formatShowingRows: function (pageFrom, pageTo, totalRows) {
            // return '显示第 ' + pageFrom + ' 到第 ' + pageTo + ' 条记录，总共 ' + totalRows + ' 条记录';
            return '共' + totalRows + '条';
        },
        formatSearch: function () {
            return '搜索';
        },
        formatNoMatches: function () {
            return '<div class="nodata-table">' +
                '    <div><img src="'+ ctxPath +'/img/nodata.png">' +
                '    <span class="nodata-text">暂无数据</span></div>'+
                '</div>';
            
        },
        formatPaginationSwitch: function () {
            return '隐藏/显示分页';
        },
        formatRefresh: function () {
            return '刷新';
        },
        formatToggle: function () {
            return '切换';
        },
        formatColumns: function () {
            return '列';
        },
        formatExport: function () {
            return '导出数据';
        },
        formatClearFilters: function () {
            return '清空过滤';
        }
    };

    $.extend($.fn.bootstrapTable.defaults, $.fn.bootstrapTable.locales['zh-CN']);

})(jQuery);