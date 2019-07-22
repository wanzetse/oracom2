$(function () {
    var approve = [
        {Name: "YES", Id: true},
        {Name: "NO", Id: false}
    ];

    $.toast({
        text: "A moment please, Loading records...",
        showHideTransition: 'fade', // It can be plain, fade or slide
        bgColor: 'blue',              // Background color for toast
        textColor: '#eee',            // text color
        allowToastClose: false,       // Show the close button or not
        hideAfter: 5000, // `false` to make it sticky or time in miliseconds to hide after
        stack: 5,                     // `fakse` to show one stack at a time count showing the number of toasts that can be shown at once
        textAlign: 'left',            // Alignment of text i.e. left, right, center
        position: 'bottom-left',// bottom-left or bottom-right or bottom-center or top-left or top-right or top-center or mid-center or an object representing the left, right, top, bottom values to position the toast on page
        loaderBg: '#000080'
    });


    //Search jsGrid while typing///
    originalFilterTemplate = jsGrid.fields.text.prototype.filterTemplate;
    jsGrid.fields.text.prototype.filterTemplate = function () {
        var grid = this._grid;
        var $result = originalFilterTemplate.call(this);
        $result.on("keyup", function (e) {
            grid.search();
        });
        return $result;
    };

    //************CUSTOM CHECKBOX*************
    var MyCheckboxDateField = function (config) {
        jsGrid.Field.call(this, config);
    };

    MyCheckboxDateField.prototype = new jsGrid.Field({
        itemTemplate: function (value) {
            //return $("<label>").append($('<input />', { type: 'checkbox', checked: value}).checkboxradio());
            this._label = $("<label>");
            this._datecheck = $('<input />', {type: 'checkbox', checked: value});
            this._datecheck.appendTo(this._label);
            this._datecheck.checkboxradio();
            console.log($(this._datecheck).prop('checked'));
            return this._label;
            /// return $('<input />', { type: 'checkbox', checked: value}).checkboxradio({label : ""});
        },
        editTemplate: function (value) {
            ///return this._editdatecheck=$("<label>").append($('<input />', { type: 'checkbox', checked: value}));

            return this._editTemplate_label = new editlabeldatefield(this, value);///=editlabeldatefield(value);
        },
        editValue: function () {
            console.log($(this._editTemplate_check).prop('checked'));
            return $(this._editTemplate_check).prop('checked');
        },
        insertTemplate: function (value) {
            this._insertTemplate_label = $("<label>");
            this._insertTemplate_check = $('<input />', {type: 'checkbox'});
            this._insertTemplate_check.appendTo(this._insertTemplate_label);
            this._insertTemplate_check.checkboxradio();
            //return $("<label>").append($('<input />', { type: 'checkbox', checked: value}).checkboxradio());
            //console.log(this._insertTemplate_check);
            return this._insertTemplate_label;///=editlabeldatefield(value);
        },
        insertValue: function () {
            return $(this._insertTemplate_check).prop('checked');
        }
    });

    jsGrid.fields._checkboxDateField = MyCheckboxDateField;

    //************CUSTOM CHECKBOX*************


    $.ajax({
        type: "GET",
        url: "/oracom/load_phones"
        //  dataType: "json"

    }).done(function (data) {
        console.log(data);

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
            deleteConfirm: "Do you really want to delete Category ?",
            fields: [

                {type: "control"},

                {name: "phoneIsSelected", title: "Select", type: "_checkboxDateField", width: 40, align: "center"},

                {
                    name: "individual_phone",
                    title: "Phone",
                    type: "text",
                    width: 100,
                    filtering: true,
                    validate: "required"

                },
                {
                    name: "individualPhone_status",
                    title: "Status",
                    type: "text",
                    width: 100,
                    filtering: true,
                    validate: "required"
                },
                {
                    name: "individualPhone_Comments",
                    title: "Comments",
                    type: "text",
                    width: 100,
                    filtering: true,
                    validate: "required"
                },
                {
                    name: "CreateDate",
                    title: "Create Date",
                    type: "text",
                    // valueField: "Id",
                    filtering: true,
                    readOnly: "true"
                    //  textField: "CreateDate"
                }, {
                    name: "CreatedBy",
                    title: "Created By",
                    type: "text",
                    //  valueField: "Id",
                    filtering: true,
                    readOnly: "true"
                    //  textField: "CreatedBy"
                },

                {type: "control"}
            ],

            controller: {
                loadData: function (filter) {
                    return $.grep(data, function (item) {
                        // client-side filtering below (be sure conditions are correct)
                        return (!filter.individual_phone || item.individual_phone.indexOf(filter.individual_phone) > -1)
                            && (!filter.individualPhone_status || item.individualPhone_status.toLowerCase().indexOf(filter.individualPhone_status.toLowerCase()) > -1)
                            && (!filter.individualPhone_Comments || item.individualPhone_Comments.toLowerCase().indexOf(filter.individualPhone_Comments.toLowerCase()) > -1)
                            && (!filter.CreateDate || item.CreateDate.indexOf(filter.CreateDate) > -1)
                            && (!filter.CreatedBy || item.CreatedBy.indexOf(filter.CreatedBy) > -1)

                    });
                },
                insertItem: function (item) {
                    return $.ajax({
                        type: "POST",
                        url: "/oracom/save_business_category",
                        data: item,
                        success: function (data) {
                            $("#jsGrid").jsGrid("refresh");
                        }
                    });
                },
                updateItem: function (item) {
                    return $.ajax({
                        type: "PUT",
                        url: "/oracom/edit_business_category",
                        data: item
                    });
                },
                deleteItem: function (item) {
                    return $.ajax({
                        type: "DELETE",
                        url: "/oracom/delete_phones",
                        data: item
                    });
                }
            }

        });
    });
});







