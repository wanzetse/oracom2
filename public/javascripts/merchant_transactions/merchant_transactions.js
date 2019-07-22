$(document).ready(function () {

    $('#transactions').dataTable(

        {
            "scrollY": 200,
            "scrollX": true,
            scrollCollapse: true,
            paging:         true,
            fixedColumns:   {
                leftColumns: 2
            },

            ajax: {
                url: '/MpayaWallet/load_merchant_transactions',
                dataSrc: ''
            },
            columns: [
                { data: 'firstName', "sWidth": "20%",
                    "defaultContent": "<i>Not Provided</i>"},
                { data: 'middleName', "sWidth": "20%",
                    "defaultContent": "<i>Not Provided</i>"},
                { data: 'lastName', "sWidth": "20%",
                    "defaultContent": "<i>Not Provided</i>"},
                { data: 'settlementAc', "sWidth": "20%",
                    "defaultContent": "<i>Not Provided</i>"},
                { data: 'msisdn', "sWidth": "20%",
                    "defaultContent": "<i>Not Provided</i>"},
                { data: 'transactionType',
                    "defaultContent": "<i>Not Provided</i>"},
                { data: 'transID', "sWidth": "20%",
                    "defaultContent": "<i>Not Provided</i>"},
                { data: 'transTime', "sWidth": "20%",
                    "defaultContent": "<i>Not Provided</i>"},
                { data: 'transAmount', "sWidth": "20%",
                    "defaultContent": "<i>Not Provided</i>"},
                { data: 'businessShortCode',
                    "defaultContent": "<i>Not Provided</i>"}



            ],
            dom: 'Bfrtip',
            buttons: [
                'copyHtml5',
                'excelHtml5',
                'csvHtml5',
                {
                    extend: 'pdfHtml5',
                    orientation: 'landscape',
                    pageSize: 'LEGAL'
                },
                {
                    extend: 'print',
                    customize: function ( win ) {
                        $(win.document.body)
                            .css( 'font-size', '10pt' )
                            .prepend(
                                '<img src="../../images/logo/empire-logo.png" style="position:absolute; top:0; left:0;" />'
                            );

                        $(win.document.body).find( 'table' )
                            .addClass( 'compact' )
                            .css( 'font-size', 'inherit' );
                    }
                }
            ]
        }
    );
});