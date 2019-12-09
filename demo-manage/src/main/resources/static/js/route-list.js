var sideModalObj;
$(document).ready(function () {
    $.sideModalFun();
    routeList();
});
//新增
$('#add-btn').click(function () {
    var options = {
        headText: "新增",//头部文本
        callback: {
            clickConfirm: function () {
                routeSave();
            },
        }
    };
    sideModalObj = $.sideModalFun(options);
    sideModalObj.showSideModal();
    sideModalObj.addHtml($('#template').html());
    $(".designer_detail").find("form").attr("id", "routeForm");
})
//同步
$('#sync-btn').click(function () {
	$.ajax({
		type : "GET",
		url : ctxPath + "/routes/sync",
		success : function(data) {
			if (data.code == 0) {
				if (undefined != data.message && '' != data.message) {
	                   $.message({
	                       message: data.message,
	                       type: 'success'
	                   });
	               }
           } else {
               if (undefined != data.message && '' != data.message) {
                   $.message({
                       message: data.message,
                       type: 'error'
                   });
               }
           }
		}
	});
})

//服务列表
function routeList() {
	$.ajax({
		type : "GET",
		url : ctxPath + "/gatewayroutes",
		success : function(data) {
			 if (data.code == 0) {
				 var data = data.data;
				 getcreatable(data);
            } else {
                if (undefined != data.message && '' != data.message) {
                    $.message({
                        message: data.message,
                        type: 'error'
                    });
                }
            }
		}
	});
}

function getcreatable(data) {
	$("#routeTable").bootstrapTable('destroy');
	$("#routeTable").bootstrapTable({
		data: data,
		dataType: "json",
		classes: 'table-no-bordered',
		sortable: true,//是否启用排序
		cache: false, // 不缓存
		pagination: true, // 开启分页功能
		search: false, // 开启搜索功能
		showColumns: false, // 开启自定义列显示功能
		showRefresh: false, // 开启刷新功能
		showToggle: false,
		pageNumber: 1, //初始化加载第一页，默认第一页
		pageSize: 10, //每页的记录行数（*）
		pageList: [10, 25, 50, 100], //可供选择的每页的行数（*）
		paginationDetailHAlign: 'left',
		paginationLoop: false,
		showJumpto: true,
//		paginationPreText: '<i class="fa fa-angle-left left-bcloud-arrow" aria-hidden="true"></i>',
//        paginationNextText: '<i class="fa fa-angle-right left-bcloud-arrow" aria-hidden="true"></i>',
		formatRecordsPerPage: function (pageNumber) {
			return '' + pageNumber + '';
		},
		formatShowingRows: function (pageFrom, pageTo, totalRows) {
			return '共' + totalRows + '条';
		},
		sidePagination: "client", //分页方式：client客户端分页，server服务端分页（*）
		columns: [{
            field: 'routeId',
            title: '路由id',
            align: 'left',
            valign: 'middle',
            sortable : true,
        }, {
            field: 'filters',
            title: '过滤器集合配置',
            align: 'center',
            valign: 'middle',
            sortable : true,
            formatter: function (value, row, index) {
    	        var html = '';
    	        var filters = JSON.parse(row.filters);
    	        $(filters).each(function(index,item) {
    	        	html += item.name;
				});
    	        return html;
    	    }
        }, {
            field: 'predicates',
            title: '断言集合配置',
            align: 'center',
            valign: 'middle',
            sortable : true,
            formatter: function (value, row, index) {
    	        var html = '';
    	        var predicates = JSON.parse(row.predicates);
    	        $(predicates).each(function(index,item) {
    	        	html += item.name;
				});
    	        return html;
    	    }
        }, {
            field: 'routeUri',
            title: '规则转发的目标uri',
            align: 'center',
            valign: 'middle',
            sortable : true,
        }, {
            field: 'routeOrder',
            title: '执行顺序',
            align: 'center',
            valign: 'middle',
            sortable : true,
        }, 
        {
            field: '',
            title: '操作',
            align: 'right',
            formatter: operateFormatter
        }]
	});
}

//操作DOM
function operateFormatter(value, row, index) {
    var html = '';
//    html += '<div class="expand-option"><span class="expand-option-btn"></span><div class="option-btn-container">';
    html += '<button type="button" class="btn btn-default btn-xs btn-beconsole-small service-edit" onclick="getServiceEdit(\'' + row.id + '\')">编辑</button>' +
    '<button type="button" class="btn btn-default btn-xs btn-beconsole-small service-delete" onclick="getServiceDel(\'' + row.id + '\')">删除</button>';
//    html += '</div></div>';
    return html;
}

//创建/修改
function routeSave() {
    var id = $("#routeForm input[name='id']").val();
    var routeId = $("#routeForm input[name='routeId']").val();
    var isEbl = $("#routeForm input[name='isEbl']:checked").val();
    var routeUri = $("#routeForm input[name='routeUri']").val();
    var routeOrder = $("#routeForm input[name='routeOrder']").val();
    var predicates = $("#routeForm #predicates").val();
    var filters = $("#routeForm #filters").val();
    var data = {
            "id": id,
            "routeId": routeId,
            "ebl": isEbl,
            "routeUri": routeUri,
            "routeOrder": routeOrder,
            "predicates": predicates,
            "filters": filters,
        }
    var type = 'PUT';
    if (id == '') {
    	type = 'POST';
    }
    $.ajax({
        url: ctxPath + "/routes",
        type: type,
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (data) {
        	sideModalObj.hideSideModal();
            if (data.code == '0') {
            	routeList();
            } else {
                if (undefined != data.message && '' != data.message) {
                    $.message({
                        message: data.message,
                        type: 'error'
                    });
                }
            }
        }
    })
}

//修改
function getServiceEdit(id) {
    var options = {
        headText: "编辑",
        callback: {
            clickConfirm: function () {
                routeSave();
            },
        }
    };
    sideModalObj = $.sideModalFun(options);
    sideModalObj.showSideModal();
    sideModalObj.addHtml($('#template').html());
    $(".designer_detail").find("form").attr("id", "routeForm");
    assignToModal(id);
}

//删除
function getServiceDel(id) {
    $('#deleteConfirmModal').modal('show');
    $('#deleteConfirmModal .btn-bcloud-blue').unbind("click");
    $('#deleteConfirmModal .btn-bcloud-blue').click(function () {
        $.ajax({
            url: ctxPath + "/routes?id=" + id,
            type: 'DELETE',
            contentType: "application/json",
            success: function (req) {
                if (req.code == 0) {
                    $.message({
                        message: '删除成功',
                        type: 'success'
                    });
                    routeList();
                } else {
                    $.message({
                        message: req.message,
                        type: 'error'
                    });
                }
            }
        });
        $('#deleteConfirmModal').modal('hide');
    });
}

//修改外部服务模态框赋值
function assignToModal(id) {
    $.ajax({
        url: ctxPath + "/routes/" + id,
        type: 'GET',
        contentType: "application/json",
        success: function (data) {
            if (data.code == '0') {
                var data = data.data;
                $("#routeForm input[name='id']").val(data.id);
                $("#routeForm input[name='routeId']").val(data.routeId);
                $("#routeForm input[name='isEbl'][value="+data.ebl+"]").attr("checked",true);
                $("#routeForm input[name='routeOrder']").val(data.routeOrder);
                $("#routeForm input[name='routeUri']").val(data.routeUri);
                $("#routeForm #predicates").val(data.predicates);
                $("#routeForm #filters").val(data.filters);
            } else {
                if (undefined != data.message && '' != data.message) {
                    $.message({
                        message: data.message,
                        type: 'error'
                    });
                }
            }
        }
    })
}