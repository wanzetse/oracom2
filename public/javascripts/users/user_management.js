$(function () {
    var approve = [
        {Name: "YES", Id: true},
        {Name: "NO", Id: false}
    ];

    $.ajax({
        type: "GET",
        url: "/oracom/BranchesMaster"
    }).done(function (branches) {
        branches.unshift({Id: "0", BranchName: ""});

        $.ajax({
            type: "GET",
            url: "/oracom/ProfilesMaster"
        }).done(function (profiles) {
            $.ajax({
                type: "GET",
                url: "/oracom/UsersMaster"
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
                    deleteConfirm: "Do you really want to delete user?",
                    fields: [
                        {
                            name: "first_name",
                            title: "FirstName",
                            type: "text",
                            width: 100,
                            validate: "required",
                            filtering: true
                        },
                        {
                            name: "mid_name",
                            title: "MiddleName",
                            type: "text",
                            width: 100,
                            filtering: true,
                            validate: "required"
                        },
                        {
                            name: "last_name",
                            title: "LastName",
                            type: "text",
                            width: 100,
                            validate: "required",
                            filtering: true
                        },
                        {
                            name: "mobile_number",
                            title: "PhoneNumber",
                            type: "text",
                            width: 100,
                            filtering: true,
                            validate: {
                                message: "Phone Number Must be Specified", validator: function (value) {
                                    var phone_no = /^\d{10}$/;
                                    return phone_no.test(String(value).toLowerCase());
                                }
                            }
                        },
                        {
                            name: "email", title: "Email", type: "text", filtering: true, width: 150, validate: {
                                message: "Email Must be Specified", validator: function (value) {
                                    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
                                    return re.test(String(value).toLowerCase());
                                }
                            }
                        },
                        {
                            name: "RoleName",
                            title: "ProfileName",
                            type: "select",
                            width: 100,
                            items: profiles,
                            valueField: "id",
                            textField: "RoleName",
                            filtering: true,
                            validate: {
                                message: "Profile Must be Specified", validator: function (value) {
                                    return value > 0;
                                }
                            }
                        },
                        {
                            name: "BranchId",
                            title: "Branch",
                            type: "select",
                            width: 100,
                            items: branches,
                            valueField: "Id",
                            textField: "BranchName",
                            filtering: true,
                            validate: {
                                message: "Branch Must be Specified", validator: function (value) {
                                    return value > 0;
                                }
                            }
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
                                    && (!filter.RoleName || item.RoleName.indexOf(filter.RoleName) > -1)
                            });
                        },
                        insertItem: function (item) {
                            return $.ajax({
                                type: "POST",
                                url: "/oracom/SaveUser",
                                data: item,
                                success: function (data) {
                                    $("#jsGrid").jsGrid("refresh");
                                }
                            });
                        },
                        updateItem: function (item) {
                            return $.ajax({
                                type: "PUT",
                                url: "/oracom/EditUser",
                                data: item
                            });
                        },
                        deleteItem: function (item) {
                            return $.ajax({
                                type: "POST",
                                url: "/oracom/postDeleteUser",
                                data: item,
                                success: function (data) {
                                    $("#jsGrid").jsGrid("refresh");
                                }
                            });
                        }
                    }

                });
            });
        })


    });


});