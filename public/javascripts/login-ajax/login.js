$(function () {
    $("#loginForm").click(function() {
        inputs =   //grab then inputs of your form #loginform
            $.ajax ({
                url: "urltoyourloginphp.php",
                data: inputs,
                success: function() {
                    $("#login").html("You are now logged in!");
                }
            });
    })

})