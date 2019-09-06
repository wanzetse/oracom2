
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

    ////LOAD STRATEGY/////////
    jsGrid.loadStrategies.PageLoadingStrategy.prototype.sort = function () {
        this._grid._sortData();
        this._grid.refresh();
        return $.Deferred().resolve().promise();
    };
    ////LOAD STRATEGY/////////


    ///filtering////





    $("#jsGrid").jsGrid({
        height: "auto",
        width: "auto",
        updateOnResize: true,
        filtering: true,
        inserting: true,
        editing: true,
        sorting: true,
        paging: true,
        pageLoading: true,
        pageSize: 50,
        pageIndex: 1,
        loadShading: true,
        searching: true,
        autosearch: true,
        autoload: true,
        pageButtonCount: 5,
        insertedRowLocation: "top",
        insertRowLocation: "top",
        deleteConfirm: "Do you really want to delete Company ?",
        headerRowClass: 'table-green-header',

        controller: {

            loadData: function(filter){
                var deferred = $.Deferred();
                $.ajax({

                    url: "/oracom/BranchesMaster",
                    data:filter,
                    cache:true,
                    dataType: "json"


                }).done(function(response){
                    response.data=response.branches;
                    response.itemsCount = response.len;

                  
                    deferred.resolve(response);


                });

                return deferred.promise();

            },







            // url: "/oracom/BranchesMaster",
            insertItem: function (item) {
                return $.ajax({
                    type: "POST",
                    url: "/oracom/SaveBranch",
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
                    url: "/oracom/EditBranch",
                    data: item
                });
            },

            finishInsert: function (insertedItem) {
                var grid = this._grid;
                grid.option("data").push(insertedItem);
                grid.refresh();
            }
            ,
            deleteItem: function (item) {
                return $.ajax({
                    type: "DELETE",
                    url: "/Oracom/postDeleteBranch ",
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
            {type: "control"},

            {name: "selected", title: "Select", type: "_checkboxDateField", width: 90, align: "center"},
            {
                name: "Company_Name",
                title: "Company Name",
                type: "text",
                width: 200,
                autosearch: true,
                filtering: true,
                sorting: true,
                message: "Please enter Company Name",
                validate: "required"
            },

            {

                name: "Company_Category",
                title: "Category",
                type: "text",
                //type: "select",
                width: 200,
                autosearch: true,
                filtering: true,
                //   validate: "required",
                //items: company_categories, valueField: "Id", textField: "Name"
            },
            {
                name: "Company_Subcategory",
                title: "Subcategory",
                //type: "select",
                type: "text",
                width: 200,
                autosearch: true,
                //  items: company_subcategories, valueField: "Id", textField: "Name",
                filtering: true,
                //  validate: "required"
            },


            {
                name: "Email_1", title: "Email 1 ", type: "text", width: 250, autosearch: true, required: false,
                /*
                validate: {
                    message: "A Valid Email Must be Specified", validator: function (value) {
                        var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
                        return re.test(String(value).toLowerCase());
                    }
                }
                */
            },
            {
                name: "Email_2", title: "Email 2", type: "text", width: 250, autosearch: true, required: false,
                /*
                                    validate: {
                                        message: "A Valid Email Must be Specified", validator: function (value) {
                                            var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
                                            return re.test(String(value).toLowerCase());
                                        }

                                    }
                                      */
            },

            {
                name: "Phone_1", title: "Phone 1", type: "text", width: 150, autosearch: true, required: false,
                /*
                                    validate: {
                                        message: "A Valid Phone Number Must be Specified starting with 254",
                                        validator: function (value) {
                                            var phone_no = /^\d{12}$/;
                                            return phone_no.test(String(value).toLowerCase());
                                        }
                                    }
                                    */
            },

            {
                name: "Phone_2",
                title: "Phone 2",
                type: "text",
                width: 150,
                autosearch: true,
                required: false,
                /*
                validate: {
                    message: "A Valid Phone Number Must be Specified starting with 254",
                    validator: function (value) {
                        var phone_no = /^\d{12}$/;
                        return phone_no.test(String(value).toLowerCase());
                    }
                }
                */

            },

            {
                name: "Website", title: "Website", type: "text", width: 200, autosearch: true,
                //  validate: "required"
            },
            {
                name: "County",
                title: "County",
                //type: "select",
                type: "text",
                //items: counties, valueField: "Id", textField: "Name",
                width: 150,
                autosearch: true,
                filtering: true

            },
            {
                name: "Town",
                title: "Town",
                type: "text",
                width: 200,
                //validate: "required",
                autosearch: true,
                filtering: true
            },
            {
                name: "Street_Name",
                title: "Street Name",
                type: "text",
                width: 200,
                // validate: "required",
                autosearch: true,
                filtering: true
            },
            {
                name: "Building",
                title: "Building",
                type: "text", width: 200,
                //validate: "required",
                autosearch: true,
                filtering: true
            },
            {
                name: "MapLatitude",
                title: "Latitude",
                type: "text",
                width: 120,
                //  validate: "required",
                autosearch: true,
                filtering: true
            },
            {
                name: "MapLongitude",
                title: "Longitude",
                type: "text",
                width: 120,
                // validate: "required",
                autosearch: true,
                filtering: true
            },
            {
                name: "companyBranch",
                title: "Branch",
                type: "text",
                width: 120,
                // validate: "required",
                autosearch: true,
                filtering: true
            },
            {
                name: "Services",
                title: "Services",
                type: "text",
                //type: "select",
                width: 200,
                // validate: "required",
                //items: services,
                //valueField: "Id",
                //textField: "service",
                autosearch: true,
                filtering: true

            },
            {
                name: "Status",
                title: "Status",
                type: "text",
                // type: "select",
                width: 200,
                //validate: "required",
                //items: status,
                //valueField: "id",
                //textField: "status",
                autosearch: true,
                filtering: true

            },

            {
                name: "Comments",
                title: "Comments",
                type: "text", width: 200,
                // validate: "required",
                autosearch: true,
                filtering: true
            },

            {
                name: "CreatedBy", title: "CreatedBy", editing: false, readOnly: "true",
                width: 200
            },
            {name: "dateCreated", title: "DateCreated", editing: false, readOnly: "true", width: 200},

            {type: "control"}

        ],
    })
    ;



});