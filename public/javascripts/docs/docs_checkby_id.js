$(document).ready(function () {

    $('#check_role').click(function () {
        event.preventDefault();
        if ($("#roles")[0].selectedIndex <= 0) {
            Materialize.toast('Please select role!', 3000, 'rounded');
        }
        else
            var intObj = {
                template: 3
            };

        var indeterminateProgress = new Mprogress(intObj);
        var xhr = new XMLHttpRequest();
        var url = "/MpayaWallet/loadpermissions";
        xhr.open("POST", url, true);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.onreadystatechange = function () {

            if (xhr.readyState === 4 && xhr.status === 200) {
                var json = JSON.parse(xhr.responseText);

                if (json !== null) {
                    $("#edit_perm :input").prop("disabled", false);
                    $("#edit_perm :input").prop("checked", false);
                    var permits = json.permissions;
                    var array = permits.split(",");
                    var arrayLength = array.length;
                    for (var i = 0; i < arrayLength; i++) {

                        $(":checkbox[value='" + array[i] + "']").prop("checked", "true");
                    }

                    indeterminateProgress.end();
                } else {
                    Materialize.toast('No permissions for the Role!', 3000, 'rounded');
                    indeterminateProgress.end();
                }

            }
        };
        var data = JSON.stringify({"role": $('#roles').val()});
        xhr.send(data);
    });
    $('#edit_permissions').click(function(){
        event.preventDefault();
        if ($("#roles")[0].selectedIndex <= 0) {
            Materialize.toast('Please select role!', 3000, 'rounded');
        }
        else

            getValueUsingParentTag($('#roles').val())

    });

    function getValueUsingParentTag(role){
        var chkArray = [];

        /* look for all checkboes that have a parent id called 'checkboxlist' attached to it and check if it was checked */
        $("#checkboxlist input:checked").each(function() {
            chkArray.push($(this).val());
        });

        /* we join the array separated by the comma */
        var selected;
        selected = chkArray.join(',') ;

        /* check if there is selected checkboxes, by default the length is 1 as it contains one single comma */
        if(selected.length > 0){
            var intObj = {
                template: 3
            };
            var indeterminateProgress = new Mprogress(intObj);
            var xhr = new XMLHttpRequest();
            var url = "/MpayaWallet/posteditpermissions";
            xhr.open("POST", url, true);
            xhr.setRequestHeader("Content-Type", "application/json");
            xhr.onreadystatechange = function () {

                if (xhr.readyState === 2 && xhr.status === 401) {
                    Materialize.toast("You have no permission to edit Role!", 3000, 'rounded');
                    indeterminateProgress.end();

                }
                if (xhr.readyState === 4 && xhr.status === 200) {
                    var json = JSON.parse(xhr.responseText);

                    Materialize.toast(json.response, 3000, 'rounded');
                    indeterminateProgress.end();

                }
            };
            var data = JSON.stringify({"roles": role, "permissions": selected});
            xhr.send(data);
        }else{
            Materialize.toast('Please check at least one of the checkboxes!', 3000, 'rounded');
        }
    }
});
