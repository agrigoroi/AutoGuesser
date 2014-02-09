var score = 0;
var game = 0;
var submited = false;
document.getElementById("price").disabled = false;
document.getElementById("price").readOnly = false;
$('#SubmitPrice').prop('disabled', false);


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
    if(submited)
        return false;
    $.ajax({
        type : 'GET',
        url : "/check/" + $('#advertId').html() + "/" + $('#price').val(),
        success : function(data) {
            submited = true;
            $('#SubmitPrice').prop('disabled', true);
            document.getElementById("price").disabled = true;
            document.getElementById("price").readOnly = true;
            $('.not-visible').removeClass("not-visible");
            setSpanHTML("priceFeedback", "");
            if(data.round < 10) {
                setSpanHTML("yourGuess", $('#price').val());
                setSpanHTML("realPrice", data.realPrice);
                setSpanHTML("roundScore", data.roundScore);
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

function submitName() {
	var name = $('#name').val();
	$.ajax({
	type : 'GET',
        url : "/changeName/" + name});
}

function nextRound() {
    $('#price').val("");
    location.reload(false);
}

