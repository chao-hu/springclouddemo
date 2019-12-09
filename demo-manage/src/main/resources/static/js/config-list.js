var sideModalObj;
$(document).ready(function () {
    $.sideModalFun();
    configList();
});
$('#add-config-btn').click(function () {
    var options = {
        headText: "新增",//头部文本
        callback: {
            clickConfirm: function () {
                configSave();
            },
        }
    };
    sideModalObj = $.sideModalFun(options);
    sideModalObj.showSideModal();
    sideModalObj.addHtml($('#template').html());
    $(".designer_detail").find("form").attr("id", "configForm");
})

//服务列表
function configList() {
	$.ajax({
		type : "GET",
		url : ctxPath + "v1/config",
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
	$("#configTable").bootstrapTable('destroy');
	$("#configTable").bootstrapTable({
		data: data,
		dataType: "json",
		classes: 'table-no-bordered',
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
            field: 'key',
            title: 'key',
            align: 'left',
            valign: 'middle',
            sortable : true,
        }, {
            field: 'value',
            title: 'value',
            align: 'center',
            sortable : true,
            valign: 'middle',
        }, {
            field: 'application',
            title: 'application',
            align: 'center',
            sortable : true,
            valign: 'middle',
        }, {
            field: 'profile',
            title: 'profile',
            align: 'center',
            sortable : true,
            valign: 'middle',
        }, {
            field: 'label',
            title: 'label',
            align: 'center',
            sortable : true,
            valign: 'middle',
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
function configSave() {
    var id = $("#configForm input[name='id']").val();
    var key = $("#configForm input[name='key']").val();
    var value = $("#configForm input[name='value']").val();
    var application = $("#configForm input[name='application']").val();
    var profile = $("#configForm input[name='profile']").val();
    var label = $("#configForm input[name='label']").val();
    var data = {
            "id": id,
            "key": key,
            "value": value,
            "application": application,
            "profile": profile,
            "label": label,
        }
    var type = 'PUT';
    if (id == '') {
    	type = 'POST';
    }
    $.ajax({
        url: ctxPath + "/v1/config/",
        type: type,
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (data) {
        	sideModalObj.hideSideModal();
            if (data.code == '0') {
            	configList();
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
                configSave();
            },
        }
    };
    sideModalObj = $.sideModalFun(options);
    sideModalObj.showSideModal();
    sideModalObj.addHtml($('#template').html());
    $(".designer_detail").find("form").attr("id", "configForm");
    assignToModal(id);
}

//删除
function getServiceDel(id) {
    $('#deleteConfirmModal').modal('show');
    $('#deleteConfirmModal .btn-bcloud-blue').unbind("click");
    $('#deleteConfirmModal .btn-bcloud-blue').click(function () {
        $.ajax({
            url: ctxPath + "/v1/config/" + id,
            type: 'DELETE',
            contentType: "application/json",
            success: function (req) {
                if (req.code == 0) {
                    $.message({
                        message: req.message,
                        type: 'success'
                    });
                    configList();
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
        url: ctxPath + "/v1/config/" + id,
        type: 'GET',
        contentType: "application/json",
        success: function (data) {
            if (data.code == '0') {
                var data = data.data;
                $("#configForm input[name='id']").val(data.id);
                $("#configForm input[name='key']").val(data.key);
                $("#configForm input[name='value']").val(data.value);
                $("#configForm input[name='application']").val(data.application);
                $("#configForm input[name='profile']").val(data.profile);
                $("#configForm input[name='label']").val(data.label);
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