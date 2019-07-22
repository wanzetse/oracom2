$(document).ready(function () {
    $("#loginForm").submit(function (event) {
        event.preventDefault(); //prevent default action
        var post_url = $(this).attr("action"); //get form action url
        var request_method = $(this).attr("method"); //get form GET/POST method
        var form_data = new FormData(this); //Encode form elements for submission
        var intObj = {
            template: 3
        };
        var indeterminateProgress = new Mprogress(intObj);
        var username = document.getElementById("username").value;
        var password = document.getElementById("password").value;
        indeterminateProgress.start();
        //window.location.href = "oracom/dashboard";


        var xhr = new XMLHttpRequest();
        var url = post_url;
        xhr.open("POST", url, true);
        xhr.setRequestHeader("Content-Type", "application/json");

        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var json = JSON.parse(xhr.responseText);
                console.log(json.login);
                indeterminateProgress.end();
                //Test
                //window.location.href = "oracom/dashboard";

                if (json.login === "success") {
                    indeterminateProgress.end();
                    swal(" Login Successful", "Dear " + username + ", Welcome!", "success");
                    window.location.href = "oracom/dashboard"
                } else if (json.login === "blocked") {
                    indeterminateProgress.end();
                    swal("Account Blocked", "Please contact your system admin!", "error");
                } else if (json.login === "fail") {
                    indeterminateProgress.end();
                    swal("Wrong username or password", "Please contact your system admin!", "warning");
                } else {
                    indeterminateProgress.end();
                    // window.location.href = "oracom/dashboard"
                    swal("Login failed", "Please contact your system admin!", "error");
                }
            }
        };

        var data = JSON.stringify({"username": username, "password": password});
        alert(data);
        xhr.send(data);

    });


});
