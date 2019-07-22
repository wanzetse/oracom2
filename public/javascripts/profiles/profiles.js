$(function () {
    function editlabeldatefield(obj, value) {
        obj._editTemplate_label = $("<label>");
        obj._editTemplate_check = $('<input />', {type: 'checkbox', checked: value});
        obj._editTemplate_check.appendTo(obj._editTemplate_label);
        obj._editTemplate_check.checkboxradio();
        //return $("<label>").append($('<input />', { type: 'checkbox', checked: value}).checkboxradio());
        return obj._editTemplate_label;///=editlabeldatefield(value);
    }

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
            console.log($(this._datecheck).prop('checked'))
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
    var NumberField3 = jsGrid.NumberField;

    function DisabledField(config) {
        NumberField3.call(this, config);
    }

    DisabledField.prototype = new NumberField3({
        min: 0,
        _createTextBox: function () {
            return $("<input>").attr({
                type: "text",
                min: this.min, disabled: true
            });
        }
    });

    jsGrid.fields.disabled = jsGrid.DisabledField = DisabledField;
    var MyDateField = function (config) {
        jsGrid.Field.call(this, config);
    };

    MyDateField.prototype = new jsGrid.Field({

        css: "date-field",            // redefine general property 'css'
        align: "center",              // redefine general property 'align'

        myCustomProperty: "foo",      // custom property

        sorter: function (date1, date2) {
            return new Date(date1) - new Date(date2);
        },

        itemTemplate: function (value) {
            return new Date(value).toDateString();
        },

        insertTemplate: function (value) {
            return this._insertPicker = $("<input>").datepicker({defaultDate: new Date()});
        },

        editTemplate: function (value) {
            return this._editPicker = $("<input>").datepicker().datepicker("setDate", new Date(value));
        },

        insertValue: function () {
            return this._insertPicker.datepicker("getDate").toISOString();
        },

        editValue: function () {
            return this._editPicker.datepicker("getDate").toISOString();
        }
    });

    jsGrid.fields.date = MyDateField;


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
        controller: {
            loadData: function (filter) {
                var d = $.Deferred();
                return $.ajax({
                    type: "GET",
                    url: "/oracom/ProfilesMaster",
                  //  dataType: "json",
                    data: filter
                }).done(function (response) {

                    return $.grep(response, function (item) {

                        return (!filter.RoleName || item.RoleName.indexOf(filter.RoleName) > -1)

                    });
                });

            },
            insertItem: function (item) {
                return $.ajax({
                    type: "POST",
                    url: "/oracom/SaveProfile",
                   // dataType: "json",
                    data: item,
                    success: function (data) {
                        $("#jsGrid").jsGrid("render");
                    }
                });
            },
            updateItem: function (item) {
                return $.ajax({
                    type: "PUT",
                    url: "/oracom/EditProfile ",
                    //dataType: "json",
                    data: item, success: function (data) {
                        $("#jsGrid").jsGrid("render");
                    }
                });
            },
            deleteItem: function (item) {
                return $.ajax({
                    type: "DELETE",
                    url: "/oracom/deleteProfile",
                   // dataType: "json",
                    data: item,
                    success: function (data) {
                        $("#jsGrid").jsGrid("refresh");
                        Materialize.toast('Role deleted Successfully!', 3000, 'rounded');

                        $.toast({
                            text: "Item  DELETED successfully",
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
                    },
                    error: function (data) {
                        $.toast({
                            text: "Item NOT DELETED successfully",
                            showHideTransition: 'fade', // It can be plain, fade or slide
                            bgColor: 'red',              // Background color for toast
                            textColor: '#eee',            // text color
                            allowToastClose: false,       // Show the close button or not
                            hideAfter: 5000, // `false` to make it sticky or time in miliseconds to hide after
                            stack: 5,                     // `fakse` to show one stack at a time count showing the number of toasts that can be shown at once
                            textAlign: 'left',            // Alignment of text i.e. left, right, center
                            position: 'bottom-left',// bottom-left or bottom-right or bottom-center or top-left or top-right or top-center or mid-center or an object representing the left, right, top, bottom values to position the toast on page
                            loaderBg: '#eee'
                        });

                        $("#jsGrid").jsGrid("refresh");
                    }
                });
            }

        },
        fields: [
            {
                name: "id",
                title: "Role_Id",
                type: "text",
                width: 150,
                filtering: true,
                sorting: "true",
                readOnly: "true",
                sorter: "string",
                autosearch: true
            },
            {
                name: "RoleName",
                title: "ProfileName",
                type: "text",
                width: 150,
                validate: "required",
                filtering: true,
                sorting: "true",
                sorter: "string",
                autosearch: true
            },
            {name: "remarks", title: "Remarks", type: "text", width: 150, filtering: false, align: "center"},
            {name: "dateCreated", title: "DateCreated", readOnly: "true", width: 150},
            {name: "approved", title: "Approval Status", type: "_checkboxDateField", width: 90, align: "center"},
            {type: "control"}
        ]
    })


});