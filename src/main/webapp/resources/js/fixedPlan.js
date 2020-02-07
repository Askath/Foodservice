/**
 * 
 */

$(window).scroll(function() {
	var scrollHeight = $(window).scrollTop();
	
	if(scrollHeight >= 150)
		{
			$("#foodplan").addClass("attachedFoodPlan");
		}
	else
		{
		$("#foodplan").removeClass("attachedFoodPlan");
		}
})