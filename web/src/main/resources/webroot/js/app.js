function send() {
    var filters = $('#filter').val();
    if (filters.length > 0) {
        var url = 'http://localhost:8888/start&filter=' + encodeURIComponent(filters);
        $.get(url);
        document.location.href = 'feed.html', true;
    }
}

var eb = new vertx.EventBus("/eventbus");
eb.onclose = function () {
    console.log("closing socket");
    eb = null;
};
eb.onopen = function () {
    console.log("opening the socket");
    eb.registerHandler("stream", function (msg) {
        console.log("receiving the message " + msg);
        var message = JSON.parse(msg);
        $("#messages").prepend(
            "<p>" + message.text + "</p>");
    });
};
