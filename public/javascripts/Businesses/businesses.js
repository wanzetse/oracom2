$(function () {
    var approve = [
        {Name: "YES", Id: true},
        {Name: "NO", Id: false}
    ];

    $.ajax({
        type: "GET",
        url: "/MpayaWallet/loadHeadOffices"
    }).done(function (headOffices) {
        $.ajax({
            type: "GET",
            url: "/MpayaWallet/LoadBusinesses"
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
                deleteConfirm: "Do you really want to delete Business?",

                fields: [
                    {
                        name: "business",
                        title: "BusinessName",
                        type: "text",
                        width: 130,
                        validate: "required",
                        filtering: true
                    },
                    {
                        name: "headOfficeName",
                        title: "HeadOffice",
                        type: "select",
                        items: headOffices,
                        valueField: "Id",
                        filtering: true,
                        textField: "headOfficeName"
                    },
                    {
                        name: "subCounty",
                        title: "SubCounty",
                        type: "text",
                        width: 100,
                        filtering: true,
                        validate: "required"
                    },
                    {
                        name: "town",
                        title: "Town",
                        type: "text",
                        width: 100,
                        validate: "required",
                        filtering: true
                    },
                    {
                        name: "bizType",
                        title: "BusinessType",
                        type: "text",
                        width: 100,
                        validate: "required",
                        filtering: true
                    },
                    {
                        name: "regNo",
                        title: "RegNo.",
                        type: "text",
                        width: 100,
                        validate: "required",
                        filtering: true
                    },
                    {
                        name: "regType",
                        title: "RegType.",
                        type: "text",
                        width: 100,
                        validate: "required",
                        filtering: true
                    }, {
                        name: "krapin",
                        title: "KRANPIN.",
                        type: "text",
                        width: 100,
                        validate: "required",
                        filtering: true
                    },
                    {
                        name: "msisdn",
                        title: "MSISDN",
                        type: "text",
                        width: 100,
                        filtering: true,
                        validate: {
                            message: "Phone Number Must be Specified", validator: function (value) {
                                var phone_no = /^\d{10}$/;
                                return phone_no.test(String(value).toLowerCase());
                            }
                        }
                    }, {
                        name: "createdby",
                        title: "CreatedBy.",
                        type: "text",
                        width: 100,
                        validate: "required",
                        filtering: true
                    }, {
                        name: "createDate",
                        title: "CreateDate.",
                        type: "text",
                        width: 100,
                        validate: "required",
                        filtering: true
                    },
                    {
                        name: "CreateApproved",
                        title: "Approve",
                        type: "select",
                        items: approve,
                        valueField: "Id",
                        filtering: true,
                        textField: "Name"
                    },
                    {type: "control"}
                ],
                controller: {
                    loadData: function (filter) {
                        return $.grep(data, function (item) {
                            // client-side filtering below (be sure conditions are correct)
                            return (!filter.first_name || item.first_name.indexOf(filter.first_name) > -1)
                                && (!filter.mid_name || item.mid_name.indexOf(filter.mid_name) > -1)
                                && (!filter.last_name || item.last_name.indexOf(filter.last_name) > -1)
                                && (!filter.mobile_number || item.mobile_number.indexOf(filter.mobile_number) > -1)
                                && (!filter.email || item.email.indexOf(filter.email) > -1)
                        });
                    },
                    insertItem: function (item) {
                        return $.ajax({
                            type: "POST",
                            url: "/MpayaWallet/saveBusinesses",
                            data: item,
                            success: function (data) {
                                $("#jsGrid").jsGrid("refresh");
                            }
                        });
                    },
                    updateItem: function (item) {
                        return $.ajax({
                            type: "POST",
                            url: "/MpayaWallet/postEditBusiness",
                            data: item
                        });
                    },
                    deleteItem: function (item) {
                        return $.ajax({
                            type: "POST",
                            url: "/clients/",
                            data: item
                        });
                    }
                }

            });
        });
    })


});