$(document).ready(function () {
    $("#head_office").submit(function (event) {
        event.preventDefault();

        var intObj = {
            template: 3
        };
        var indeterminateProgress = new Mprogress(intObj);
        var hqName = document.getElementById("hqName").value;
        var hqNumber = document.getElementById("hqNumber").value;
        var contact_ps = document.getElementById("contact_ps").value;
        var contact = document.getElementById("contact").value;
        var mobile_no = document.getElementById("mobile_no").value;
        var email = document.getElementById("email_address").value;
        var location = document.getElementById("location_address").value;


        indeterminateProgress.start();

        var xhr = new XMLHttpRequest();
        var url = "/MpayaWallet/saveHeadOffice";
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
            "hqName": hqName,
            "hqNumber": hqNumber,
            "contact_ps": contact_ps,
            "contact": contact,
            "mobile_no": mobile_no,
            "email": email,
            "location": location
        });
        xhr.send(data);

    });

});
