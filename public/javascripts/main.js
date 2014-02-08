function setSpanHTML(id, value) {
    var span = document.getElementById(id);

    while( span.firstChild ) {
        span.removeChild( span.firstChild );
    }
    span.appendChild( document.createTextNode(value) );
}

function checkPrice() {
//    alert("HELLO");
    $.ajax({
        type : 'GET',
        url : "/check/" + $('#advertId').html() + "/" + $('#price').val(),
        success : function(data) {
//            alert(data);
            setSpanHTML("yourGuess", $('#price').val());
            setSpanHTML("realPrice", data.realPrice);
        },
        error : function(data) {
            //TODO
        }
    });
};
