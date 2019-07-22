$(document).ready(function () {

    $('#biz_types').change(function () {
        event.preventDefault();
        if ($("#biz_types")[0].selectedIndex <= 0) {
            Materialize.toast('Please select Business Type!', 3000, 'rounded');
        }
        else
            getValueUsingParentTag($('#biz_types').val())

    });
    function getValueUsingParentTag(bizType){

        let intObj = {
            template: 3
        };
        let indeterminateProgress = new Mprogress(intObj);
        let xhr = new XMLHttpRequest();
        let url = "/MpayaWallet/loadConfigs";
        xhr.open("POST", url, true);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var json = JSON.parse(xhr.responseText);

                console.log(json);

                if(json.ResponseCode=="000"){
                    $("#edit_doc :input").prop("disabled", false);
                    $("#edit_doc :input").prop("checked", false);

                    $('#app_form').prop('checked', json.app_form);
                    $('#dir_id').prop('checked', json.dir_id);
                    $('#c_council').prop('checked', json.c_council);
                    $('#kra').prop('checked', json.kra);
                    $('#biz_reg').prop('checked', json.biz_reg);
                    $('#U_bill').prop('checked', json.U_bill);
                    $('#biz_photo').prop('checked', json.biz_photo);
                    $('#bank_l').prop('checked', json.bank_l);
                    $('#c_cheq').prop('checked', json.c_cheq);
                    $('#b_resolution').prop('checked', json.b_resolution);
                    $('#cr12').prop('checked', json.cr12);
                    $('#ra_letter').prop('checked', json.ra_letter);
                    $('#accounts').prop('checked', json.accounts);


                    indeterminateProgress.end();
                }else if(json.ResponseCode=="001"){
                    Materialize.toast("No configurations present for Business Type "+bizType, 3000, 'rounded');
                    $("#edit_doc :input").prop("disabled", false);
                    $("#edit_doc :input").prop("checked", false);
                    indeterminateProgress.end();
                }



            }else if(xhr.readyState === 4 && xhr.status===500){
                console.log(xhr.readyState);
                Materialize.toast('Internal Server Error. Please check with admin!', 3000, 'rounded');
            }else{

            }
        };
        let data = JSON.stringify({"biz_type": bizType});

        xhr.send(data);

    }
});
