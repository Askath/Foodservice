
var timeinterval;
var timer;
var end;
var start;
var duration;

function getTimeRemaining() 
{
	var t = duration;
	var seconds = Math.floor((t/1000) % 60);
	var minutes = Math.floor((t/1000/60) % 60);
	return {
		'seconds': seconds,
		'minutes': minutes,
		'total': t
	};
}

function initializeClock(id, endtime, starttime, currenttime)
{	timeinterval = setInterval(updateClock, 1000);
	timer = document.getElementById(id);
	end = endtime;
	start = starttime;
	duration = endtime.getTime() - currenttime.getTime();
	updateClock(id);
}

function updateClock(id)
{
	var t = getTimeRemaining();	
	timer.innerHTML = "<br/>" + t.minutes + " Minuten " + t.seconds + " Sekunden";
	if(t.total<=0){
		clearInterval(timeinterval);
		isPhaseOver();
	}
	duration = duration - 1000;
}