$(function () {
    /*
        $.ajax({
            type: "GET",
            url: "/UchumiWallet/BranchesMaster"
        }).done(function (branches) {
            branches.unshift({ Id: "0", BranchName: "" });
    */
    $.ajax({
        type: "GET",
        url: "/oracom/ProfilesMaster"
    }).done(function (profiles) {

        profiles.unshift({id: "0", RoleName: ""});

        $("#jsGrid").jsGrid({
            height: "auto",
            width: "100%",
            filtering: true,
            inserting: true,
            editing: true,
            sorting: true,
            paging: true,
            autoload: true,
            pageSize: 10,
            pageButtonCount: 5,
            deleteConfirm: "Do you really want to delete user?",
            controller: {
                loadData: function (filter) {
                    return $.ajax({
                        type: "GET",
                        url: "/oracom/UsersMaster",
                        data: filter
                    });
                },
                insertItem: function (item) {
                    return $.ajax({
                        type: "POST",
                        url: "/oracom/SaveUser",
                        data: item,
                        success: function (data) {
                            $("#jsGrid").jsGrid("render");
                        }
                    });
                },
                updateItem: function (item) {
                    return $.ajax({
                        type: "PUT",
                        url: "/oracom/EditUser",
                        data: item,
                        success: function (data) {
                            $("#jsGrid").jsGrid("render");
                        }
                    });
                },
                deleteItem: function (item) {
                    return $.ajax({
                        type: "POST",
                        url: "/oracom/postDeleteUser",
                        data: item,
                        success: function (data) {
                            $("#jsGrid").jsGrid("render");
                        }
                    });
                }
            },
            fields: [
                {name: "first_name", title: "FirstName", type: "text", width: 100, validate: "required"},
                {
                    name: "mid_name",
                    title: "MiddleName",
                    type: "text",
                    width: 100,
                    filtering: false,
                    validate: "required"
                },
                {name: "last_name", title: "LastName", type: "text", width: 100, validate: "required"},
                {
                    name: "mobile_number", title: "PhoneNumber", type: "text", width: 100, validate: {
                        message: "Phone Number Must be Specified", validator: function (value) {
                            var phone_no = /^\d{10}$/;
                            return phone_no.test(String(value).toLowerCase());
                        }
                    }
                },
                {
                    name: "email", title: "Email", type: "text", width: 200, validate: {
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
                    items: [
                        {RoleName: "", Id: 0},
                        {RoleName: "Admin", Id: 1},
                        {RoleName: "SuperAdmin", Id: 2},
                        {RoleName: "RootAdmin", Id: 3},
                        {RoleName: "user", Id: 4}
                    ],
                    valueField: "Id",
                    textField: "RoleName"
                    /*
                    validate: {
                        message: "Profile Must be Specified", validator: function (value) {
                            return value > 0;
                        }

                    }
                    */
                },
                /*
                {
                    name: "BranchId",
                    title: "Branch",
                    type: "select",
                    width: 100,
                    items: branches,
                    valueField: "Id",
                    textField: "BranchName",
                    validate: { message: "Branch Must be Specified", validator: function(value) { return value > 0; } }
                },
                */
                {type: "control"}
            ]
        });

    })


});