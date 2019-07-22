$(document).ready(function () {
    $("#smsForm").submit(function (event) {
        event.preventDefault(); //prevent default action
        var post_url = $(this).attr("action"); //get form action url
        var request_method = $(this).attr("method"); //get form GET/POST method
        var form_data = new FormData(this); //Encode form elements for submission
        var intObj = {
            template: 3
        };
        var indeterminateProgress = new Mprogress(intObj);
        var smsbodyTextField = document.getElementById("smsbodyTextField").value;
        var userNameTextField = document.getElementById("userNameTextField").value;
        var senderIDTextField = document.getElementById("senderIDTextField").value;
        var senderIDPasswordTextField= document.getElementById("senderIDPasswordTextField").value;
        indeterminateProgress.start();

        // Material.toast("Successful");

        var xhr = new XMLHttpRequest();
        var url = post_url;
        xhr.open("POST", url, true);
        xhr.setRequestHeader("Content-Type", "application/json");

        $('#smsbodyTextField').keyup(function () {
            var max = 500;
            var len = $(this).val().length;
            if (len >= max) {
                $('#charNum').text(' you have reached the limit');
            } else {
                var char = max - len;
                $('#charNum').text(char + ' characters left');
            }
        });

        /*
        $('textarea').on("input", function(){
            var maxlength = $(this).attr("maxlength");
            var currentLength = $(this).val().length;

            if( currentLength >= maxlength ){
                console.log("You have reached the maximum number of characters.");
            }else{
                console.log(maxlength - currentLength + " chars left");
            }
        });
        */

        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var json = JSON.parse(xhr.responseText);
                console.log(json.login);
                indeterminateProgress.end();
                //Test
                // window.location.href = "oracom/dashboard";

                if (json.result === "Success!") {
                    indeterminateProgress.end();
                    Materialize.toast("SMS Sent Successfully!", 3000, "rounded");
                } else if (json.result === "empty") {
                    indeterminateProgress.end();
                    swal("SMS field is empty", "Please write an SMS", "warning");
                } else {
                    indeterminateProgress.end();
                    // window.location.href = "oracom/dashboard"
                    swal("Error", json.result, "error");
                }

            }
        };


        var data = JSON.stringify({
            "smsbodyTextField": smsbodyTextField, "senderIDTextField": senderIDTextField,
            "userNameTextField": userNameTextField, "senderIDPasswordTextField": senderIDPasswordTextField
        });
        xhr.send(data);

    });

});
