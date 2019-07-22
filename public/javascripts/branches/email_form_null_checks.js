$(document).ready(function () {

    $('#emailForm').validate({
     // initialize the plugin
     debug:true,
        rules: {
            subjectTextField: {
                required: true

            },
            bodyTextField: {
                required: true,
                minlength: 5
            },
            fromTextField: {
                required: true,
                email:true

            },
            passwordTextField: {
                required: true,
                minlength: 5
            }
        }, messages: {
            subjectTextField: {
                required: "Enter a Subject"
            },
            bodyTextField: {
                required: "Enter a the body",
                minlength: "Enter at least {0} characters"
            }
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
