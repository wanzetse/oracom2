

$(document).ready(function () {

    $("#emailForm").submit(function (event) {
        event.preventDefault(); //prevent default action
        var post_url = $(this).attr("action"); //get form action url
        var request_method = $(this).attr("method"); //get form GET/POST method
        var form_data = new FormData(this); //Encode form elements for submission
        var intObj = {
            template: 3
        };
        var indeterminateProgress = new Mprogress(intObj);
        var fromTextField = document.getElementById("fromTextField").value;
        var passwordTextField = document.getElementById("passwordTextField").value;
        var subjectTextField = document.getElementById("subjectTextField").value;
        var bodyTextField = document.getElementById("bodyTextField").value;


        indeterminateProgress.start();


        var xhr = new XMLHttpRequest();
        var url = post_url;
        xhr.open("POST", url, true);
        xhr.setRequestHeader("Content-Type", "application/json");


        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                
                var json = JSON.parse(xhr.responseText);
                console.log(json.result);
                indeterminateProgress.end();

                if (json.result === "Success!") {

                    indeterminateProgress.end();
                    Materialize.toast("Email Sent Successfully!", 3000, "rounded");
                } else if (json.result === "empty") {
                    indeterminateProgress.end();
                    swal("Please fill all the fields", "Please write an Email", "warning");
                } else {
                    indeterminateProgress.end();
                    swal("Error", "Please try again!", "error");
                }

            }
        };

        var data = JSON.stringify({
            "subjectTextField": subjectTextField,
            "bodyTextField": bodyTextField,
            "fromTextField": fromTextField,
            "passwordTextField": passwordTextField,


        });
        
        xhr.send(data);


    });




});



    // code to read selected table row cell data (values).
   
