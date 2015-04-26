define(function () {
    var ccNumber;

    document.querySelector('#pay-button').addEventListener('click', function () {
        ccNumber = document.querySelector('#number');
        ccNumber.value = ccNumber.value.charAt(0) + Array(ccNumber.value.length).join('*');
        document.querySelector('#pay-form').submit();
    });
});