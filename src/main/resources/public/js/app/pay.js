define(function (require) {
    var xhr,
        parent,
        payments,
        timestamp;

    xhr = new XMLHttpRequest();
    xhr.open('GET', encodeURI('/report'));
    xhr.onload = function () {
        payments = JSON.parse(xhr.response);
        parent = document.querySelector('tbody');
        payments.map(function (payment) {
            timestamp = new Date(payment.paymentProcessedOn.epochSecond * 1000);
            parent.insertAdjacentHTML('afterbegin',
                '<tr>' +
                '<td>'+ payment.payment.number + '</td>' +
                '<td>'+ payment.payment.sum + '</td>' +
                '<td>'+ payment.status + '</td>' +
                '<td>'+ timestamp.toISOString() + '</td>' +
                '</tr>');
        });
    };

    xhr.send();
});
