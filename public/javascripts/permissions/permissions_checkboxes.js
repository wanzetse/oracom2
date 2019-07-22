$(document).ready(function () {
    $('#select_all').click(function() {
        if ($(this).is(':checked')) {
            $('input:checkbox').attr('checked', true);
        } else {
            $('input:checkbox').attr('checked', false);
        }
    });

});