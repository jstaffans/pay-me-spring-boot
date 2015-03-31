define(function (require) {
    var $ = require('jquery');

    $('#pay-button').click(function (event) {
        $('#pay-form').submit();
    });
});