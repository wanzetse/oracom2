$(function () {
    var approve = [
        {Name: "YES", Id: true},
        {Name: "NO", Id: false}
    ];

    $.ajax({
        type: "GET",
        url: "/MpayaWallet/loadHeadOffices"
    }).done(function (headOffices) {
        headOffices.unshift({Id: "0", BranchName: ""});

        $.ajax({
            type: "GET",
            url: "/MpayaWallet/LoadBusinesses"
        }).done(function (businesses) {
         
                $("#jsGrid").jsGrid({
                    height: "auto",
                    width: "auto",
                    filtering: true,
                    inserting: true,
                    editing: true,
                    sorting: true,
                    paging: true,
                    pageLoading:true,
                    autoload: true,
                    insertRowLocation: "top",
                    pageSize: 10,
                    pageButtonCount: 5,
                    deleteConfirm: "Do you really want to delete user?",
                    fields: [
                        {
                            name: "firstName",
                            title: "FirstName",
                            type: "text",
                            width: 100,
                            validate: "required",
                            filtering: true
                        },
                        {
                            name: "middleName",
                            title: "MiddleName",
                            type: "text",
                            width: 100,
                            filtering: true,
                            validate: "required"
                        },
                        {
                            name: "lastName",
                            title: "LastName",
                            type: "text",
                            width: 100,
                            validate: "required",
                            filtering: true
                        },
                        {
                            name: "mobileNumber",
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
                            name: "emailAddress", title: "Email", type: "text", filtering: true, width: 150, validate: {
                                message: "Email Must be Specified", validator: function (value) {
                                    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
                                    return re.test(String(value).toLowerCase());
                                }
                            }
                        },
                        {
                            name: "businessName",
                            title: "BusinessName",
                            type: "select",
                            width: 100,
                            items: businesses,
                            valueField: "id",
                            textField: "business",
                            filtering: true,
                            validate: {
                                message: "BusinessName Must be Specified", validator: function (value) {
                                    return value > 0;
                                }
                            }
                        },
                        {
                            name: "headOfficeName",
                            title: "HeadOffice",
                            type: "select",
                            width: 100,
                            items: headOffices,
                            valueField: "Id",
                            textField: "headOfficeName",
                            filtering: true,
                            validate:"required"
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
                       loadData: function(filter){
                var deferred = $.Deferred();
                $.ajax({

                     url: "/MpayaWallet/loadDirectors",
                    data:filter,
                    cache:true,
                    dataType: "json"


                }).done(function(response){
                    response.data=response.data;
                    response.itemsCount =response.len;

                  
                    deferred.resolve(response);


                });

                return deferred.promise();

            },
                        insertItem: function (item) {
                            return $.ajax({
                                type: "POST",
                                url: "/MpayaWallet/postDirectorJsGrid",
                                data: item,
                                success: function (data) {
                                    $("#jsGrid").jsGrid("refresh");
                                }
                            });
                        },
                        updateItem: function (item) {
                            return $.ajax({
                                type: "POST",
                                url: "/MpayaWallet/EditUser",
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
        })


    });


