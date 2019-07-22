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

                    if(json.app_form==false){
                        $("#sForm").hide();
                    }else{
                        $("psForm").prop('required',true);
                    }
                    //////////////////////////////////////////////////////////////////////////////
                    if(json.accounts==false){
                        $("#audited").hide();
                    }else{
                        $("paudited").prop('required',true);
                    }
                    //////////////////////////////////////////////////////////////////
                    if(json.dir_id==false){
                        $("#pID").hide();
                    }else{
                        $("ppID").prop('required',true);
                    }
                    ////////////////////////////////////////////////////
                    if(json.c_council==false){
                        $("#council").hide();
                    }else{
                        $("pcouncil").prop('required',true);
                    }
                    ////////////////////////////////////
                    if(json.kra==false){
                        $("#kraPin").hide();
                    }else{
                        $("pkra").prop('required',true);
                    }
                    ////////////////////////
                    if(json.biz_reg==false) {
                        $("#bizCert").hide();
                    }else{
                       $("pbizCert").prop('required',true);
                    }
                    ////////////////////////////////////
                    if(json.U_bill==false){
                        $("#utils").hide();
                    }else{
                        $("putils").prop('required',true);
                    }
                    ////////////////////////////////////
                    if(json.biz_photo==false){
                        $("#bizPhoto").hide();
                    }else{
                        $("pbizPhoto").prop('required',true);
                    }
                    /////////////////////////////////////////////
                    if(json.bank_l==false){
                        $("#bankLetter").hide();
                    }else{
                        $("pbankLetter").prop('required',true);
                    }
                    /////////////////////////////////
                    if(json.c_cheq==false){
                        $("#cheque").hide();
                    }else{
                        $("pcheque").prop('required',true);
                    }
                    //////////////////////////////////////////////
                    if(json.b_resolution==false){
                        $("#board_resolution").hide();
                    }else{
                        $("pboard_resolution").prop('required',true);
                    }
                    ///////////////////////////////////////////////////////////
                    if(json.cr12==false){
                        $("#cr12").hide();
                    }else{
                        $("pcr12").prop('required',true);
                    }
                    ///////////////////////////////////////////////////////////////
                    if(json.ra_letter==false){
                        $("#regulator").hide();
                    }else{
                        $("pregulator").prop('required',true);
                    }
                    ///////////////////////////////////////////////////////////////
                    indeterminateProgress.end();
                }else if(json.ResponseCode=="001"){
                    Materialize.toast("No configurations present for Business Type "+bizType, 3000, 'rounded');
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
