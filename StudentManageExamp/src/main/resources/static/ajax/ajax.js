$(document).ready(function () {

    $("#search-form").submit(function (event) {

        //stop submit the form, we will post it manually.
        event.preventDefault();
    });
});

function searchButtonClick() {
	pageButtonClick('1');
}

function pageButtonClick(targetPage) {

	var current = targetPage;
	var name = $('#searchName').val();
	$.ajax({
		url : "student/search",
		type : "POST",
		data : {
			name : name,
			current : current,
		},
		complete : function(response) {
			$('#table').html(response.responseText);
		}
	});
}