$(document).ready(function() {
	$("#postTimeBtn").click(getBestPostTime);
});

var x;
var getBestPostTime = function() {
	var $username = $("#username").val();
	if($username.trim().length == 0) {
		$("#error").html("Please enter username");
		return;
	} else {
		$("#error").html("");
	}
	
	var $that = $(this);
	$that.val("Processing... ");
	$that.attr("disabled", "disabled");
	poll($username, $that);
}

var poll = function($username, $that) {
	$.ajax({
		type: "GET",
		url: "http://localhost:8080/CrowdFireAppInstagramService/crowdfire/user/timetopost",
		data: {username: $username},
		dataType: 'json'
		}).done(function(response) {
			x = response;
			populateView(response.data);
		}).fail(function(response) {
			console.log("Error while calling the instagram service");
			x = response;
			$("#output").html(response.responseText);
		}).always(function(response) {
			x = response;
			if(response.pollStatus == "inprogress") {
				console.log("Polling to get more data");
				poll($username, $that);
			} else {		
				$that.val("Get Best Post Time >");
				$that.removeAttr("disabled");	
			}
	});
}

var populateView = function(data) {
	if(data == null || data == undefined) {
		$('#output').html("No response obtained. Some issue from Instagram API ends. Trace 'x' object from console");
	}
	
	var $msg = "Best day to post on instagram is <strong>" +data.day+ "</strong>";
	var $week = data.week;
	$.each($week, function(key, val) {
		$msg += "<br/>";
		if(val== -1) {
			$msg += "None of your followers are active on " + key;
		} else {
			$msg += "Best Time on " + key + " is " + val + " hours";
		}
	});
	$("#output").html($msg);
}

