
$(document).ready(function () {
    $.ajax({
        url: '/MpayaWallet/LoadBusinesses',
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            var table = $('#transactions').DataTable({
                "dom": 'C<"clear">lfrtip',
                "bAutoWidth": false,
                "aaData": data,
                "aaSorting": [],
                "aoColumnDefs": [
                    {
                        "aTargets": [0],
                        "mData": "headOffice"
                    },
                    {
                        "aTargets": [1],
                        "mData": "msisdn"
                    },
                    {
                        "aTargets": [2],
                        "mData": "business"
                    },
                    {
                        "aTargets": [3],
                        "mData": "subCounty"
                    },
                    {
                        "aTargets": [4],
                        "mData": "bizType"
                    },
                    {
                        "aTargets": [5],
                        "mData": "regNo"
                    },
                    {
                        "aTargets": [6],
                        "mData": "regType"
                    },
                    {
                        "aTargets": [7],
                        "mData": null,
                        "bSortable": false,
                        "mRender": function(data, type, row) {
                            return '<div class="btn-group"> <button value="'+row.id+'" type="button" class="btn btn-info btn-xs dt-view" style="margin-right:16px;"><span class="glyphicon glyphicon-eye-open glyphicon-info-sign" aria-hidden="true"></span></button>  <button type="button" class="btn btn-primary btn-xs dt-edit" style="margin-right:16px;"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></button><button type="button" class="btn btn-danger btn-xs dt-delete"><span class="glyphicon glyphicon-remove glyphicon-trash" aria-hidden="true"></span></button></div>';
                        }
                    }

                ],
                "order": [[1, 'asc']]
            });
            table.on('click', '.btn.btn-info', function(){

                var rowData = table.row($(this).parents('tr')).data();
                alert(rowData.id);
                // swal({
                //     title: "Confirm Till Request",
                //     icon: "warning",
                //     buttons: true,
                //     dangerMode: true,
                // }).then((willDelete) => {
                //     if (willDelete) {
                //         swal("Additional Till Request submitted", {
                //             icon: "success",
                //         });
                //     }
                // });
            });
        }
    });


});