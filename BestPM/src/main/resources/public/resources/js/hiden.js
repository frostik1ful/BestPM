var count = 0;
	var divInputHiden = document.querySelector("#hidemInput");
	var selectMotivatorType = document.querySelector("#motivatorType");
	
	function setToZero(event){
		count = 0;
	}
	
	function motivatorListener(event){
		count++;
		if(count === 2){
			if(selectMotivatorType.value == "TEMPORARY"){
				divInputHiden.style.visibility = "visible";
			} else {
				divInputHiden.style.visibility = "hidden";
			}
			
		}
	}
	
	selectMotivatorType.addEventListener("mouseout",setToZero,false);
	selectMotivatorType.addEventListener("click",motivatorListener,false);