$(document).ready(function () {

    $('#loginForm').validate({ // initialize the plugin
        rules: {
            username: {
                required: true
            },
            password: {
                required: true,
                minlength: 5
            },
        }, messages: {
            username: {
                required: "Enter a username"
            },
            password: {
                required: "Enter a Password",
                minlength: "Enter at least {0} characters"
            },
        }, errorElement: 'div',
        errorPlacement: function (error, element) {
            var placement = $(element).data('error');
            if (placement) {
                $(placement).append(error)
            } else {
                error.insertAfter(element);
            }
        }
    })
});
