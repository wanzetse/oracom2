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
    var deliv = [
        
        {Name: true, Id: 1},
        {Name: false, Id: 2}


    ];

        $("#jsGrid").jsGrid({
            height: "auto",
            width: "auto",
            filtering: true,
            sorting: true,
            paging: true,
            autowidth: true,
            shrinkToFit: false,
            altRows: true,
            autoload: true,
            loadShading: true,
            pageLoading:true,
            pageSize: 5,

            
            rowClick: function (args) {
                var $row = this.rowByItem(args.item);
                $row.toggleClass("highlight");

                return false;
            },
            
            fields: [
                
/*
public String type;
public String SentBy;
public String SentTo;
public boolean received;
public String SentDate;
public String DateReceived;

*/

               {type: "control"},
                {
                    name: "type",
                    title: "SMS Category",
                    type: "text",
                    width: 150,
                    // validate: "required"
                    filtering: true
                },
                {
                    name: "SentBy",
                    title: "SENDER PHONE",
                    type: "text",
                    width: 150,
                    // validate: "required"
                    filtering: true
                }, {
                    name: "SenderName",
                    title: "Sender USername",
                    type: "text",
                    width: 150,
                    // validate: "required"
                    filtering: true
                },
                {
                    name: "SentTo",
                    title: "RECIEVER",
                    type: "text",
                    width: 150,
                    filtering: true
                    // validate: "required"
                },
               
            {    name: "received",
                 title: "Delivered",
                 type: "_checkboxDateField", width: 50, align: "center",
                 readOnly: "true"
                    
               
            },
                {
                    name: "SentDate",
                    title: "Date Sent",
                    type: "text",
                    width: 100,
                    filtering: true
                    },
                {
                    name: "DateReceived",
                    title: "Date Received",
                    type: "text",
                    width: 100,
                    //validate: "required",
                    filtering: true
                    

                }
                //////Updates/////
                
               // {name: "selected", title: "Select", type: "_checkboxDateField", width: 90, align: "center"},

                //////Updates/////
                
            ],

            controller: {

                loadData: function(filter){
                var deferred = $.Deferred();
                $.ajax({

                    url: "/oracom/loadSmsreports",
                    data:filter,
                    cache:true,
                    dataType: "json"


                }).done(function(response){
                    response.data=response.data;
                    response.itemsCount = response.len;

                  
                    deferred.resolve(response);


                });

                return deferred.promise();

            }

                
            }

        });
    });


