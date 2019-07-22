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


    var counties = [
        {Name: "", Id: 0},
        {Name: "Baringo", Id: 1},
        {Name: "Bomet", Id: 2},
        {Name: "Bungoma", Id: 3},
        {Name: "Busia", Id: 4},
        {Name: "Elgeyo Marakwet", Id: 5},
        {Name: "Embu", Id: 6},
        {Name: "Garissa", Id: 7},
        {Name: "Homa Bay", Id: 8},
        {Name: "Isiolo", Id: 9},
        {Name: "Kajiado", Id: 10},
        {Name: "Kakamega", Id: 11},
        {Name: "Kericho", Id: 12},
        {Name: "Kiambu", Id: 13},
        {Name: "Kilifi", Id: 14},
        {Name: "Kirinyaga", Id: 15},
        {Name: "Kisii", Id: 16},
        {Name: "Kisumu", Id: 17},
        {Name: "Kitui", Id: 18},
        {Name: "Kwale", Id: 19},
        {Name: "Laikipia", Id: 20},
        {Name: "Lamu", Id: 21},
        {Name: "Machakos", Id: 22},
        {Name: "Makueni", Id: 23},
        {Name: "Mandera", Id: 24},
        {Name: "Meru", Id: 25},
        {Name: "Migori", Id: 26},
        {Name: "Marsabit", Id: 27},
        {Name: "Mombasa", Id: 28},
        {Name: "Muranga", Id: 29},
        {Name: "Nairobi", Id: 30},
        {Name: "Nakuru", Id: 31},
        {Name: "Nandi", Id: 32},
        {Name: "Narok", Id: 33},
        {Name: "Nyamira", Id: 34},
        {Name: "Nyandarua", Id: 35},
        {Name: "Nyeri", Id: 36},
        {Name: "Samburu", Id: 37},
        {Name: "Siaya", Id: 38},
        {Name: "Taita", Id: 39},
        {Name: "Tana River", Id: 40},
        {Name: "Tharaka Nithi", Id: 41},
        {Name: "Trans Nzoia", Id: 42},
        {Name: "Turkana", Id: 43},
        {Name: "Uasin Gishu", Id: 44},
        {Name: "Vihiga", Id: 45},
        {Name: "Wajir", Id: 46},
        {Name: "West Pokot", Id: 47}

    ];

    var status = [
        {status: "", id: 0},
        {status: "Prospect", id: 1},
        {status: "Lead", id: 2},
        {status: "Client", id: 3},
        {status: "Lost", id: 4},
        {status: "Partner", id: 5},
        {status: "Other", id: 6}
    ];

    var services = [
        {service: "", Id: 0},
        {service: "Web Design", Id: 1},
        {service: "SSL", Id: 2},
        {service: "Software Development", Id: 3},
        {service: "Mobile Apps", Id: 4},
        {service: "Domains and Hosting", Id: 5},
        {service: "Email Services", Id: 6},
        {service: "Digital Marketing Training", Id: 7},
        {service: "Internet Marketing Agency", Id: 8},
        {service: "Photography", Id: 9},
        {service: "Video Production", Id: 10},
        {service: "Studio Services", Id: 11},
        {service: "Graphic Design", Id: 12},
        {service: "Bulk SMS", Id: 13},
        {service: "SC, USSD, RBT", Id: 14},
        {service: "Product Marketing - Merchant", Id: 15},
        {service: "Product Marketing - Delivery", Id: 16},
        {service: "My Leader", Id: 17},
        {service: "Other", Id: 18}

    ];

    var company_categories = [
        {Name: "", Id: 0},
        {Name: "Technology & Electronics", Id: 1},
        {Name: "Beauty and Personal Care", Id: 2},
        {Name: "Business to business.", Id: 3},
        {Name: "Fashion & Clothing", Id: 4},
        {Name: "Agriculture & Farming", Id: 5},
        {Name: "Education & Training", Id: 6},
        {Name: "Auto & Transport", Id: 7},
        {Name: "Home & Office", Id: 8},
        {Name: "Building & Construction", Id: 9},
        {Name: "Events, Sports & Entertainment", Id: 10},
        {Name: "Education, Training & Books", Id: 11},
        {Name: "Health, Fitness ", Id: 12},
        {Name: "Food & Drink ", Id: 13},
        {Name: "Tours & Travel", Id: 14},
        {Name: "Accommodation & Hotels", Id: 15},
        {Name: " Art & Entertainment", Id: 16},
        {Name: "Baby & Toddler", Id: 17},
        {Name: "Other", Id: 18}
    ];

    var company_subcategories = [

        {Name: "", Id: 0},
        {Name: "Antiques.", Id: 1},
        {Name: "Art and craft supplies", Id: 2},
        {Name: "Art dealers and galleries.", Id: 3},
        {Name: "Camera and photographic supplies", Id: 4},
        {Name: "Digital art", Id: 5},
        {Name: "Music store (instruments and sheet music)", Id: 6},
        {Name: "Sewing, needlework, and fabrics", Id: 7},
        {Name: "Stamp and coin", Id: 8},
        {Name: "Stationary, printing and writing paper", Id: 9},
        {Name: "Clothing", Id: 10},
        {Name: "Furniture", Id: 11},
        {Name: "Safety and health", Id: 12},
        {Name: "Baby products (other)", Id: 13},
        {Name: "Bath and body", Id: 14},
        {Name: "Fragrances and perfumes.", Id: 15},
        {Name: "Makeup and cosmetics", Id: 16},
        {Name: "Audio books.", Id: 17},
        {Name: "Digital content", Id: 18},
        {Name: "Educational and textbooks", Id: 19},
        {Name: "Publishing and printing", Id: 20}

    ];

    jsGrid.fields._checkboxDateField = MyCheckboxDateField;

    counties.unshift({id: "0", Name: ""});
    services.unshift({id: "0", Name: ""});
    company_categories.unshift({id: "0", Name: ""});
    company_subcategories.unshift({id: "0", Name: ""});


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


    $.ajax({
        type: "GET",
        url: "/oracom/loadCorporateEmails"

    }).done(function (data) {
        console.log(data);
        $("#jsGrid").jsGrid({
            eight: "auto",
            width: "100%",
            updateOnResize: true,
            filtering: true,
            inserting: true,
            editing: true,
            sorting: true,
            paging: true,
            //pageLoading: true,
            pageSize: 50,
            loadShading: true,
            searching: true,
            autosearch: true,
            autoload: true,
            pageButtonCount: 5,
            insertedRowLocation: "top",
            insertRowLocation: "top",
            deleteConfirm: "Do you really want to delete Corporate Email ?",
            headerRowClass: 'table-green-header',

            fields: [
                {type: "control", with: 0.1},

                {name: "selected", title: "Select", type: "_checkboxDateField", width: 20, align: "center"},
                {
                    name: "Email",
                    title: "Email",
                    type: "text",
                    width: 40,
                    autosearch: true,
                    filtering: true,
                    message: "Please enter Email",
                    validate: "required"
                },

                {

                    name: "Description",
                    title: "Description ",
                    type: "text",
                    //type: "select",
                    width: 40,
                    autosearch: true,
                    filtering: true,
                    //   validate: "required",
                    //items: company_categories, valueField: "Id", textField: "Name"
                },
                {
                    name: "Comments",
                    title: "Comments",
                    type: "text", width: 60,
                    // validate: "required",
                    autosearch: true,
                    filtering: true
                },

                {
                    name: "CreatedBy", title: "CreatedBy", editing: false, readOnly: "true",
                    width: 40
                },
                {name: "dateCreated", title: "DateCreated", editing: false, readOnly: "true", width: 40},

                {type: "control", with: 0.1}

            ],


            controller: {

                loadData: function (filter) {


                    return $.grep(data, function (item) {

                        // client-side filtering below (be sure conditions are correct)
                        return (!filter.selected || item.selected.indexOf(filter.selected) > -1)
                            && (!filter.Email || item.Email.toLowerCase().indexOf(filter.Email.toLowerCase()) > -1)
                            && (!filter.Description || item.Description.toLowerCase().indexOf(filter.Description.toLowerCase()) > -1)
                            && (!filter.Comments || item.Comments.toLowerCase().indexOf(filter.Comments.toLowerCase()) > -1)
                            && (!filter.CreatedBy || item.CreatedBy.toLowerCase().indexOf(filter.CreatedBy.toLowerCase()) > -1)
                            && (!filter.dateCreated || item.dateCreated.indexOf(filter.dateCreated) > -1)



                    });
                },

                insertItem: function (item) {
                    return $.ajax({
                        type: "POST",
                        url: "/oracom/saveCorporateEmails",
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
                        url: "/oracom/editCorporateEmails",
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
                        url: "/oracom/deleteCorporateEmails ",
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
            }
        });
    });


});