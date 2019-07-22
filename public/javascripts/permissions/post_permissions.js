$(document).ready(function () {

    $('#send_permissions').click(function(){
        event.preventDefault();
        if ($("#user_roles")[0].selectedIndex <= 0) {
            Materialize.toast('Please select role!', 3000, 'rounded');
        }
        else

        getValueUsingParentTag($('#user_roles').val())

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
            var url = "/oracom/savePermissions";
            xhr.open("POST", url, true);
            xhr.setRequestHeader("Content-Type", "application/json");
            xhr.onreadystatechange = function () {
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