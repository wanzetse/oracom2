$(document).ready(function () {
    $("#director").submit(function (event) {
        event.preventDefault();

        var intObj = {
            template: 3
        };
        var indeterminateProgress = new Mprogress(intObj);
        var fName = document.getElementById("fName").value;
        var mName = document.getElementById("mName").value;
        var lName = document.getElementById("lName").value;
        var mNumber = document.getElementById("mNumber").value;
        var email_add = document.getElementById("email_add").value;
        var county = document.getElementById("county").value;
        var location_name = document.getElementById("location_name").value;
        var cbs_account = document.getElementById("cbs_account").value;
        var select_biz = document.getElementById("hq").value;


        indeterminateProgress.start();

        var xhr = new XMLHttpRequest();
        var url = "/MpayaWallet/postSaveDirector";
        xhr.open("POST", url, true);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var json = JSON.parse(xhr.responseText);

                console.log(json.response);

                if (json.responseCode == "001") {
                    indeterminateProgress.end();
                    $('#director').find('input:text').val('');
                    Materialize.toast('Saved Successfully', 3000, 'rounded');
                } else {
                    indeterminateProgress.end();
                    $('#director').find('input:text').val('');
                    Materialize.toast('Error while saving. Please try again!', 3000, 'rounded');
                }
            }
        };
        var data = JSON.stringify({
            "fName": fName,
            "mName": mName,
            "lName": lName,
            "mNumber": mNumber,
            "email_add": email_add,
            "county": county,
            "location": location,
            "location_name": location_name,
            "cbs_account": cbs_account,
            "select_biz": select_biz
        });
        xhr.send(data);

    });

});
