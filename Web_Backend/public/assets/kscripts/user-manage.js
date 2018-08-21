var UserManage = function () {

    var row_obj;
    var userTable;

    var handleAccept = function() {
        $("#user_table").delegate(".btn-user-accept", "click", function(e) {
            e.preventDefault();
            $("#user_accept").modal();
            row_obj = $(e.target).parent();
        });

        $(".btn-accept-ok").click(function(e) {
            e.preventDefault();
            $.ajax({
                url:"/users/" + row_obj.data('id'), 
                type:"PUT",
                data: {
                    check_flag: 1
                }, 
                success: function(res) {
                    if (JSON.parse(res).status) {
                        row_obj.find("a.btn-user-accept").hide();
                        row_obj.find("a.btn-user-reject").show();
                        $("#user_accept").modal('hide');
                    } else {                 
                        alert("Failure! Try again, please.");
                    }
                }
            })            
        })

    }

    var handleReject = function() {

        $("#user_table").delegate(".btn-user-reject", "click", function(e) {
            e.preventDefault();
            $("#user_reject").modal();
            row_obj = $(e.target).parent();
        });

        $(".btn-reject-ok").click(function(e) {
            e.preventDefault();
            $.ajax({
                url:"/users/" + row_obj.data('id'), 
                type:"PUT",
                data: {
                    check_flag: 0
                }, 
                success: function(res) {
                    if (JSON.parse(res).status) {
                        row_obj.find("a.btn-user-accept").show();
                        row_obj.find("a.btn-user-reject").hide();
                        $("#user_reject").modal('hide');
                    } else {                 
                        alert("Failure! Try again, please.");
                    }
                }
            })                    
        })        
    }

    var handleProtocol = function() {
        $("#user_table").delegate('tr', 'dblclick', function(e) {
            var email = userTable.fnGetData($(this)[0])[2];
            var userName = userTable.fnGetData($(this)[0])[1];
            $.ajax({
                url:"/user-protocol/" + email,
                type: "POST",
                success:function(res) {
                    var result = JSON.parse(res);
                    if (result.status) {
                        $("#protocol_info").modal();
                        $("#userName").val(userName);
                        $("#userEmail").val(email);
                        $("#channel_name").val(result.data[0].channel_name);
                        $("#protocol").val(result.data[0].protocol);
                        $("#destination").val(result.data[0].destination);
                        $("#jitterBuffer").val(result.data[0].j_buf);
                        $("#dJitterBuffer").val(result.data[0].dj_buf);
                        $("#format").val(result.data[0].format);
                        $("#bitrate").val(result.data[0].bitrate);
                        $("#samplerate").val(result.data[0].samplerate);
                        $("#created_at").val(result.data[0].created_at);
                        $("#updated_at").val(result.data[0].updated_at);
                    } else {
                        alert('Data Loading Failure. Try again, please.');
                    }

                }
            })
        });
    }

    return {

        //main function to initiate the module
        init: function () {
            function restoreRow(userTable, nRow) {
                var aData = userTable.fnGetData(nRow);
                var jqTds = $('>td', nRow);

                for (var i = 0, iLen = jqTds.length; i < iLen; i++) {
                    userTable.fnUpdate(aData[i], nRow, i, false);
                }

                userTable.fnDraw();
            }

            function editRow(userTable, nRow) {
                var aData = userTable.fnGetData(nRow);
                var jqTds = $('>td', nRow);
                jqTds[0].innerHTML = '<input type="text" class="form-control input-small" value="' + aData[0] + '">';
                //jqTds[1].innerHTML = '<input type="text" class="form-control input-small" value="' + aData[1] + '">';
                jqTds[2].innerHTML = '<input type="text" class="form-control input-small" value="' + aData[2] + '">';
                jqTds[3].innerHTML = '<input type="text" class="form-control input-small" value="' + aData[3] + '">';
                jqTds[4].innerHTML = '<a class="edit" href="">Save</a>';
                jqTds[5].innerHTML = '<a class="cancel" href="">Cancel</a>';
            }

            function saveRow(userTable, nRow) {
                var jqInputs = $('input', nRow);
                userTable.fnUpdate(jqInputs[0].value, nRow, 0, false);
                //userTable.fnUpdate(jqInputs[1].value, nRow, 1, false);
                userTable.fnUpdate(jqInputs[2].value, nRow, 2, false);
                userTable.fnUpdate(jqInputs[3].value, nRow, 3, false);
                userTable.fnUpdate('<a class="edit" href="">Edit</a>', nRow, 4, false);
                userTable.fnUpdate('<a class="delete" href="">Delete</a>', nRow, 5, false);
                userTable.fnDraw();
            }

            function cancelEditRow(userTable, nRow) {
                var jqInputs = $('input', nRow);
                userTable.fnUpdate(jqInputs[0].value, nRow, 0, false);
                //userTable.fnUpdate(jqInputs[1].value, nRow, 1, false);
                userTable.fnUpdate(jqInputs[2].value, nRow, 2, false);
                userTable.fnUpdate(jqInputs[3].value, nRow, 3, false);
                userTable.fnUpdate('<a class="edit" href="">Edit</a>', nRow, 4, false);
                userTable.fnDraw();
            }

            userTable = $('#user_table').dataTable({
                "aLengthMenu": [
                    [10, 20, 40, -1],
                    [10, 20, 40, "All"] // change per page values here
                ],
                // set the initial value
                "iDisplayLength": 10,
                
                "sPaginationType": "bootstrap",
                "oLanguage": {
                    "sLengthMenu": "_MENU_ records",
                    "oPaginate": {
                        "sPrevious": "Prev",
                        "sNext": "Next"
                    }
                },
                "aoColumnDefs": [{
                        'bSortable': false,
                        'aTargets': [7]
                    }
                ]
            });

            jQuery('#user_table_wrapper .dataTables_filter input').addClass("form-control input-medium"); // modify table search input
            jQuery('#user_table_wrapper .dataTables_length select').addClass("form-control input-small"); // modify table per page dropdown
            jQuery('#user_table_wrapper .dataTables_length select').select2({
                showSearchInput : false //hide search box with special css class
            }); // initialize select2 dropdown

            var nEditing = null;

            $('#sample_editable_1_new').click(function (e) {
                e.preventDefault();
                var aiNew = userTable.fnAddData(['', '', '', '',
                        '<a class="edit" href="">Edit</a>', '<a class="cancel" data-mode="new" href="">Cancel</a>'
                ]);
                var nRow = userTable.fnGetNodes(aiNew[0]);
                editRow(userTable, nRow);
                nEditing = nRow;
            });

            $('#sample_editable_1 a.delete').live('click', function (e) {
                e.preventDefault();

                if (confirm("Are you sure to delete this row ?") == false) {
                    return;
                }

                var nRow = $(this).parents('tr')[0];
                userTable.fnDeleteRow(nRow);
                alert("Deleted! Do not forget to do some ajax to sync with backend :)");
            });

            $('#sample_editable_1 a.cancel').live('click', function (e) {
                e.preventDefault();
                if ($(this).attr("data-mode") == "new") {
                    var nRow = $(this).parents('tr')[0];
                    userTable.fnDeleteRow(nRow);
                } else {
                    restoreRow(userTable, nEditing);
                    nEditing = null;
                }
            });

            $('#sample_editable_1 a.edit').live('click', function (e) {
                e.preventDefault();

                /* Get the row as a parent of the link that was clicked on */
                var nRow = $(this).parents('tr')[0];

                if (nEditing !== null && nEditing != nRow) {
                    /* Currently editing - but not this row - restore the old before continuing to edit mode */
                    restoreRow(userTable, nEditing);
                    editRow(userTable, nRow);
                    nEditing = nRow;
                } else if (nEditing == nRow && this.innerHTML == "Save") {
                    /* Editing this row and want to save it */
                    saveRow(userTable, nEditing);
                    nEditing = null;
                    alert("Updated! Do not forget to do some ajax to sync with backend :)");
                } else {
                    /* No edit in progress - let's start one */
                    editRow(userTable, nRow);
                    nEditing = nRow;
                }
            });

            handleAccept();
            handleReject();
        }

    };

}();