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
               
                { name: "received", title: "Delivered", align: "center",
                itemTemplate: function(value, item) {
                return $("<input>").attr("type", "checkbox")
                        .attr("checked", value || item.Checked)
                    .on("change", function() {
                        item.Checked = $(this).is(":checked");
                    });
              }
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


