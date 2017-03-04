'use strict';

var localUrl = 'http://localhost:8888/';

function startStreaming() {
    var filters = $('#filter').val();
    if (filters.length > 0) {
        $.get(localUrl + 'start&filter=' + encodeURIComponent(filters));
        document.location.href = 'feed.html', true;
    }
}

function stopStreaming() {
    $.get(localUrl + 'stop');
    document.location.href = '/', true;
}

var eb = new vertx.EventBus("/eventbus");

eb.onopen = function () {
    console.log("opening the socket");
    eb.registerHandler("stream", function (msg) {
        console.log("receiving the message " + msg);
        var message = JSON.parse(msg);
        $("#messages").prepend(
            "<p>" + message.user.name + ': ' + message.text + "</p>");
    });
};

eb.onclose = function () {
    console.log("closing socket");
    eb = null;
};
