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



    jsGrid.loadStrategies.PageLoadingStrategy.prototype.sort = function () {
        this._grid._sortData();
        this._grid.refresh();
        return $.Deferred().resolve().promise();
    };

    var email = $('#comment').val();

    ////MULTISELECT////////
    var MultiselectField = function (config) {
        jsGrid.Field.call(this, config);
    };

    MultiselectField.prototype = new jsGrid.Field({

        items: [],

        textField: "",

        itemTemplate: function (value) {
            return $.makeArray(value).join(", ");
        },

        _createSelect: function (selected) {
            var textField = this.textField;
            var $result = $("<select>").prop("multiple", true);

            $.each(this.items, function (_, item) {
                var value = item[textField];
                var $opt = $("<option>").text(value);

                if ($.inArray(value, selected) > -1) {
                    $opt.attr("selected", "selected");
                }

                $result.append($opt);
            });

            return $result;
        },

        insertTemplate: function () {
            var insertControl = this._insertControl = this._createSelect();

            setTimeout(function () {
                insertControl.multiselect({
                    minWidth: 140
                });
            });

            return insertControl;
        },

        editTemplate: function (value) {
            var editControl = this._editControl = this._createSelect(value);

            setTimeout(function () {
                editControl.multiselect({
                    minWidth: 140
                });
            });

            return editControl;
        },

        insertValue: function () {
            return this._insertControl.find("option:selected").map(function () {
                return this.selected ? $(this).text() : null;
            });
        },

        editValue: function () {
            return this._editControl.find("option:selected").map(function () {
                return this.selected ? $(this).text() : null;
            });
        }

    });

    jsGrid.fields.multiselect = MultiselectField;


    /////MULTISELECT////////

    $("#jsGrid").jsGrid({
            height: "auto",
            width: "100%",
            updateOnResize: true,
            filtering: true,
            inserting: true,
            editing: true,
            sorting: true,
            paging: true,
            pageLoading: true,
            pageSize: 50,
            loadShading: true,
            searching: true,
            autosearch: true,
            autoload: true,
            pageButtonCount: 5,
            insertedRowLocation: "top",
            insertRowLocation: "top",
            deleteConfirm: "Do you really want to delete Individual Email ?",
            headerRowClass: 'table-green-header',

          

            controller: {
                loadData: function(filter){
                var deferred = $.Deferred();
                $.ajax({

                    url: "/oracom/loadIndividualEmails",
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
                        url: "/oracom/saveIndividualEmails",
                        data: item,
                        error: function (data) {
                            $.toast({
                                text: "Sorry, this record already exists in the database",
                                showHideTransition: 'fade', // It can be plain, fade or slide
                                bgColor: 'orange',              // Background color for toast
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
                },
                updateItem: function (item) {
                    return $.ajax({
                        type: "PUT",
                        url: "/oracom/editIndividualEmails",
                        data: item
                    });
                },

                finishInsert: function (insertedItem) {
                    var grid = this._grid;
                    grid.option("data").push(insertedItem);
                    grid.refresh();
                },
                deleteItem: function (item) {
                    return $.ajax({
                        type: "DELETE",
                        url: "/oracom/deleteIndividualEmails ",
                        data: item,
                        success: function (data) {
                            $("#jsGrid").jsGrid("refresh");

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
                {type: "control", width:0.1},

                {name: "individualSelected", title: "Select", type: "_checkboxDateField", width: 40, align: "center"},
                {
                    name: "individualEmail",
                    title: "Email",
                    type: "text",
                    width: 50,
                    autosearch: true,
                    filtering: true,
                    message: "Please enter Email",
                    validate: "required"
                },

                {

                    name: "individualDescription",
                    title: "Description",
                    type: "text",
                    //type: "select",
                    width: 80,
                    autosearch: true,
                    filtering: true,
                    //   validate: "required",
                    //items: company_categories, valueField: "Id", textField: "Name"
                },
                {
                    name: "individualComments",
                    title: "Comments",
                    //type: "select",
                    type: "text",
                    width: 80,
                    autosearch: true,
                    //  items: company_subcategories, valueField: "Id", textField: "Name",
                    filtering: true,
                    //  validate: "required"
                },


                {
                    name: "individualCreatedBy", title: "CreatedBy", editing: false, readOnly: "true",
                    width: 60
                },
                {name: "individualDateCreated", title: "DateCreated", editing: false, readOnly: "true", width: 60},


                {type: "control", width:0.1}
           /*
                 {
                     name: "select", title: "Select", align: "center", wdth: 90,
                     itemTemplate: function (value, item) {
                         return $("<input>").attr("type", "_checkboxDateField")
                             .attr("checked", value || item.Checked)
                             .on("change", function () {
                                 item.Checked = $(this).is(":checked");
                             });
                     }
                 },
     */
            ],

        });
    });

