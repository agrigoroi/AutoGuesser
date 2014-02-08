function setSpanHTML(id, value) {
    var span = document.getElementById(id);

    while( span.firstChild ) {
        span.removeChild( span.firstChild );
    }
    span.appendChild( document.createTextNode(value) );
}

var url;

function checkPrice() {
//    alert("HELLO");
    $.ajax({
        type : 'GET',
        url : "/check/" + $('#advertId').html() + "/" + $('#price').val(),
        success : function(data) {
//            alert(data);
            setSpanHTML("yourGuess", $('#price').val());
            setSpanHTML("realPrice", data.realPrice);
            url = data.url;
        },
        error : function(data) {
            //TODO
        }
    });
}

function viewAdvert() {
    if(typeof url !== undefined)
        window.location = url;
}

function nextRound() {
    $('#price').val("");
    location.reload(false);
}

