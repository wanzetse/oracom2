$(function() {
    var $beefup = $('.beefup').beefup({
        openSingle: true
    });

    $('#open-all').on('click', function() {
        $beefup.open();
    });

    $('#close-all').on('click', function() {
        $beefup.close();
    });

    $('#toggle1').on('click', function() {
        $beefup.click($('#beefup1'));
    });

    $('#toggle2').on('click', function() {
        $beefup.click($('#beefup2'));
    });

    $('#toggle3').on('click', function() {
        $beefup.click($('#beefup3'));
    });
});
