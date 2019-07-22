$(function () {

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
    var sex = [
        {Name: "", Id: 0},
        {Name: "M", Id: 1},
        {Name: "F", Id: 2}


    ];

    $.ajax({
        type: "GET",
        url: "/oracom/loadPersons"

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
            autowidth: true,
            shrinkToFit: false,
            altRows: true,
            autoload: true,
            selecting: true,
            rowClick: function (args) {
                var $row = this.rowByItem(args.item);
                $row.toggleClass("highlight");

                return false;
            },
            insertRowLocation: "top",
            singleSelectClickMode: "selectonly",
            pageSize: 10,
            pageButtonCount: 5,
            deleteConfirm: "Do you really want to delete Person?",
            fields: [
                {type: "control"},

                 {name: "selected", title: "Select", type: "_checkboxDateField", width: 90, align: "center"},

                {
                    name: "First_Name",
                    title: "First_Name",
                    type: "text",
                    width: 150,
                    // validate: "required"
                    filtering: true
                },
                {
                    name: "Middle_Name",
                    title: "Middle_Name",
                    type: "text",
                    width: 150,
                    // validate: "required"
                    filtering: true
                }, {
                    name: "Last_Name",
                    title: "Last_Name",
                    type: "text",
                    width: 150,
                    // validate: "required"
                    filtering: true
                },
                {
                    name: "Company",
                    title: "Company",
                    type: "text",
                    width: 150,
                    filtering: true,
                    // validate: "required"
                },
                {
                    name: "Email", title: "Email", type: "text", filtering: true,
                    width: 200
                    /*
                      validate: {
                          message: "Email Must be Specified", validator: function (value) {
                              var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
                              return re.test(String(value).toLowerCase());
                          }
                      }*/
                },
                {
                    name: "Phone",
                    title: "Phone",
                    type: "text",
                    width: 100,
                    filtering: true,
                    validate: {
                        message: "A Valid Phone Number Must be Specified starting with 254",
                        validator: function (value) {
                            var phone_no = /^\d{12}$/;
                            return phone_no.test(String(value).toLowerCase());
                        }
                    }                },
                {
                    name: "Sex",
                    title: "Gender",
                    type: "select",
                    // width: 50,
                    //validate: "required",
                    filtering: true,
                    items: sex, valueField: "Id", textField: "Name"

                },
                //////Updates/////
                {
                    name: "CreatedBy", title: "CreatedBy", readOnly: "true",
                    width: 150
                },
                {name: "dateCreated", title: "DateCreated", readOnly: "true", width: 200},

               // {name: "selected", title: "Select", type: "_checkboxDateField", width: 90, align: "center"},

                //////Updates/////
                {type: "control"}
            ],

            controller: {

                loadData: function (filter) {
                    return $.grep(data, function (item) {
                        // client-side filtering below (be sure conditions are correct)
                        return (!filter.First_Name || item.First_Name.indexOf(filter.First_Name) > -1)
                            && (!filter.Middle_Name || item.Middle_Name.indexOf(filter.Middle_Name) > -1)
                            && (!filter.Last_Name || item.Last_Name.indexOf(filter.Last_Name) > -1)
                            && (!filter.Company || item.Company.indexOf(filter.Company) > -1)
                            && (!filter.Email || item.Email.indexOf(filter.Email) > -1)
                            && (!filter.Phone || item.Phone.indexOf(filter.Phone) > -1)
                            && (!filter.Proffession || item.Proffession.indexOf(filter.Proffession) > -1)
                            && (!filter.Sex || item.Sex.indexOf(filter.Sex) > -1)
                            && (!filter.CreatedBy || item.CreatedBy.indexOf(filter.CreatedBy) > -1)
                            && (!filter.dateCreated || item.dateCreated.indexOf(filter.dateCreated) > -1)
                            && (!filter.selected || item.selected.indexOf(filter.selected) > -1)
                    });
                },
                insertItem: function (item) {
                    return $.ajax({
                        type: "POST",
                        url: "/oracom/savePerson",
                        data: item,
                        success: function (data) {
                            $("#jsGrid").jsGrid("refresh");
                        }
                    });
                },
                updateItem: function (item) {
                    return $.ajax({
                        type: "PUT",
                        url: " /oracom/editPerson",
                        data: item
                    });
                },
                deleteItem: function (item) {
                    return $.ajax({
                        type: "DELETE",
                        url: "/oracom/postDelete",
                        data: item
                    });
                }
            }

        });
    });


});