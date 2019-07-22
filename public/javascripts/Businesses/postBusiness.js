$(document).ready(function () {
    $("#director").submit(function (event) {
        event.preventDefault();

        var intObj = {
            template: 3
        };
        var indeterminateProgress = new Mprogress(intObj);
        var organization_name = document.getElementById("organization_name").value;
        var location = document.getElementById("location").value;
        var town = document.getElementById("town").value;
        var biz_type = document.getElementById("biz_type").value;
        var company_reg = document.getElementById("company_reg").value;
        var regType = document.getElementById("reg").value;
        var kra = document.getElementById("kra").value;
        var no_of_dir = document.getElementById("no_of_dir").value;
        var HQ = document.getElementById("hq").value;
        var MSISDN = document.getElementById("director2number").value;


        indeterminateProgress.start();

        var xhr = new XMLHttpRequest();
        var url = "/MpayaWallet/saveBusinesses";
        xhr.open("POST", url, true);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var json = JSON.parse(xhr.responseText);

                console.log(json.response);

                if (json.responseCode == "001") {
                    indeterminateProgress.end();
                    $('#head_office').find('input:text').val('');
                    Materialize.toast('Saved Successfully', 3000, 'rounded');
                } else {
                    indeterminateProgress.end();
                    $('#head_office').find('input:text').val('');
                    Materialize.toast('Error while saving. Please try again!', 3000, 'rounded');
                }
            }
        };
        var data = JSON.stringify({
            "organization_name": organization_name,
            "town": town,
            "biz_type": biz_type,
            "company_reg": company_reg,
            "regType": regType,
            "kra": kra,
            "location": location,
            "HQ": HQ,
            "no_of_dir": no_of_dir,
            "MSISDN": MSISDN
        });
        xhr.send(data);

    });

});
