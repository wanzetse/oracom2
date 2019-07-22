$(document).ready(function () {

    $('#edit_configs').click(function(){
        event.preventDefault();
        if ($("#biz_types")[0].selectedIndex <= 0) {
            Materialize.toast('Please select Business Type!', 3000, 'rounded');
        }
        else

        getValueUsingParentTag($('#biz_types').val())

    });

    function getValueUsingParentTag(bizType){

        let app_form="";
        let dir_id="";
        let kra="";
        let biz_reg="";
        let U_bill="";
        let biz_photo="";
        let c_cheq="";
        let b_resolution="";
        let cr12="";
        let ra_letter="";
        let accounts="";
        let c_council="";
        let bank_l="";

        if ($('#app_form').is(":checked"))
        {
            app_form="1";
        }else{
            app_form="0";
        }
        if ($('#dir_id').is(":checked"))
        {
            dir_id="1";
        }else{
            dir_id="0";
        }
        if ($('#kra').is(":checked"))
        {
            kra="1";
        }else{
            kra="0";
        }
        if ($('#biz_reg').is(":checked"))
        {
            biz_reg="1";
        }else{
            biz_reg="0";
        }
        if ($('#U_bill').is(":checked"))
        {
            U_bill="1";
        }else{
            U_bill="0";
        }
        if ($('#biz_photo').is(":checked"))
        {
            biz_photo="1";
        }else{
            biz_photo="0";
        }
        if ($('#c_cheq').is(":checked"))
        {
            c_cheq="1";
        }else{
            c_cheq="0";
        }
        if ($('#b_resolution').is(":checked"))
        {
            b_resolution="1";
        }else{
            b_resolution="0";
        }
        if ($('#cr12').is(":checked"))
        {
            cr12="1";
        }else{
            cr12="0";
        }
        if ($('#ra_letter').is(":checked"))
        {
            ra_letter="1";
        }else{
            ra_letter="0";
        }
        if ($('#accounts').is(":checked"))
        {
            accounts="1";
        }else{
            accounts="0";
        }
        if ($('#c_council').is(":checked"))
        {
            c_council="1";
        }else{
            c_council="0";
        }
        if ($('#bank_l').is(":checked"))
        {
            bank_l="1";
        }else{
            bank_l="0";
        }
            let intObj = {
                template: 3
            };
            let indeterminateProgress = new Mprogress(intObj);
            let xhr = new XMLHttpRequest();
            let url = "/MpayaWallet/postConfigs";
            xhr.open("POST", url, true);
            xhr.setRequestHeader("Content-Type", "application/json");
            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    var json = JSON.parse(xhr.responseText);

                    if(json.ResponseCode=="000"){
                        Materialize.toast(bizType+" Document Configuration saved", 3000, 'rounded');
                        indeterminateProgress.end();
                    }else if(json.ResponseCode=="001"){
                        Materialize.toast(bizType+" Failed saving. Please contact admin.", 3000, 'rounded');
                        indeterminateProgress.end();
                    }


                }else if(xhr.readyState === 4 && xhr.status===500){
                    console.log(xhr.readyState);
                    Materialize.toast('Internal Server Error. Please check with admin!', 3000, 'rounded');
                }else{

                }
            };
            let data = JSON.stringify({"BizType": bizType, "app_form": app_form, "dir_id": dir_id, "kra": kra,
                "biz_reg": biz_reg, "U_bill": U_bill, "biz_photo": biz_photo, "c_cheq": c_cheq, "b_resolution": b_resolution,
                "cr12": cr12,"ra_letter": ra_letter,"accounts": accounts,"c_council": c_council,"bank_l": bank_l});
            console.log(data);
            xhr.send(data);

    }
});