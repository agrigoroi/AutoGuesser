var score = 0;
var game = 0;

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
            $('.not-visible').removeClass("not-visible");
            setSpanHTML("priceFeedback", "");
            if(data.round < 11) {
                setSpanHTML("yourGuess", $('#price').val());
                setSpanHTML("realPrice", data.realPrice);
                url = data.url;
                $('#resultModal').modal('toggle');
            } else {
                setSpanHTML("yourGuess2", $('#price').val());
                setSpanHTML("realPrice2", data.realPrice);
                setSpanHTML("totalScore", data.score);
                url = data.url;
                $('#endGameModal').modal('toggle');
            }
        },
        error : function(data) {
            setSpanHTML("priceFeedback", "Price should be a number");
            //TODO
        }
    });
    return false;
}

function viewAdvert() {
    if(typeof url !== undefined)
        window.location = url;
}

function nextRound() {
    $('#price').val("");
    location.reload(false);
}

