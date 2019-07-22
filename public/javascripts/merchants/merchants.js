$(function () {
    $.ajax({
        type: "GET",
        url: "/MpayaWallet/getMerchants"
    }).done(function (data) {
        $("#jsGrid").jsGrid({
            height: "auto",
            width: "100%",
            filtering: true,
            inserting: true,
            editing: true,
            sorting: true,
            paging: true,
            autoload: true,
            insertRowLocation: "top",
            pageSize: 10,
            pageButtonCount: 5,
            deleteConfirm: "Do you really want to delete profile?",
            fields: [
                {
                    name: "firstName",
                    title: "FirstName",
                    type: "text",
                    width: 100,
                    validate: "required",
                    filtering: true,
                    sorting: "true",
                    sorter: "string",
                    autosearch: true
                },
                {name: "midName", title: "MidName", type: "text", width: 100, filtering: true, align: "center"},
                {name: "lastName", title: "LastName", type: "text", width: 100, filtering: true, align: "center"},
                {name: "phoneNumber", title: "PhoneNumber", type: "text", width: 100, filtering: true, align: "center"},
                {
                    name: "email_Address",
                    title: "Email_Address",
                    type: "text",
                    width: 100,
                    filtering: true,
                    align: "center"
                },
                {
                    name: "payBillNumber",
                    title: "MerchantAcc",
                    type: "text",
                    width: 100,
                    filtering: true,
                    align: "center"
                },
                {
                    name: "settlementAc",
                    title: "SettlementAcc",
                    type: "text",
                    width: 100,
                    filtering: true,
                    align: "center"
                },
                {type: "control"}
            ],
            controller: {
                loadData: function (filter) {
                    return $.grep(data, function (item) {
                        // client-side filtering below (be sure conditions are correct)
                        return (!filter.firstName || item.firstName.indexOf(filter.firstName) > -1)
                            && (!filter.midName || item.midName.indexOf(filter.midName) > -1)
                            && (!filter.lastName || item.lastName.indexOf(filter.lastName) > -1)
                            && (!filter.phoneNumber || item.phoneNumber.indexOf(filter.phoneNumber) > -1)
                            && (!filter.email_Address || item.email_Address.indexOf(filter.email_Address) > -1)
                            && (!filter.payBillNumber || item.payBillNumber.indexOf(filter.payBillNumber) > -1)
                            && (!filter.settlementAc || item.settlementAc.indexOf(filter.settlementAc) > -1)
                    });
                },
                insertItem: function (item) {
                    return $.ajax({
                        type: "POST",
                        url: "/MpayaWallet/postMerchant",
                        data: item,
                        success: function (data) {
                            $("#jsGrid").jsGrid("refresh");
                        }
                    });
                },
                updateItem: function (item) {
                    return $.ajax({
                        type: "POST",
                        url: "/MpayaWallet/editMerchant",
                        data: item
                    });
                },
                deleteItem: function (item) {
                    return $.ajax({
                        type: "DELETE",
                        url: "/clients/",
                        data: item
                    });
                }
            }

        });
    });
});