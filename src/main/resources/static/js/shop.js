const url = "http://localhost:8080/api/customer";
let sorting = "";
function createCustomer() {
		let firstName = document.getElementById("firstName").value;
		let lastName = document.getElementById("lastName").value;
		let middleName = document.getElementById("middleName").value;
		let date = new Date(document.getElementById("birthdate").value);
		let birthdate = date.toLocaleDateString("ru");

		let responseTextArea = document.getElementById("responsePost");
		axios.post(url, {
			firstName : firstName,
			lastName : lastName,
			middleName : middleName,
			birthdate : birthdate
	    }, 
	    {
	    	//responseType: 'json',
		    headers : {
			'Accept': 'application/json'}
		}).then(response => {
	    	console.log(response);
	    	let resJson = JSON.parse(response.config.data);
	    	console.log(resJson["firstName"]);
	        responseTextArea.textContent = resJson["lastName"] + "\n" + resJson["firstName"] + "\n"
	         + resJson["middleName"] + "\n" + resJson["birthdate"];

	    }).catch(error => {
	    	console.log(error);
	    	console.log(error.response);
	        if (error.response) {
	        	responseTextArea.textContent = error.response.status + " " + error.response.statusText;
	            responseTextArea.textContent += JSON.stringify(error.response.data);
	        } else {
	            responseTextArea.textContent = error;
	        }
	    })
}

function getCustomers() {
	let responseTextArea = document.getElementById("responseGet");
	let params = "";
	if (document.getElementById("firstNameParam").value != "") {
		params += "firstName=" + document.getElementById("firstNameParam").value;
	}
	if (document.getElementById("lastNameParam").value != "") {
		if (params != ""){
			params += "&";
		}
		params += "lastName=" + document.getElementById("lastNameParam").value;
	}
	if (document.getElementById("middleNameParam").value != "") {
		if (params != ""){
			params += "&";
		}
		params += "middleName=" + document.getElementById("middleNameParam").value;
	}
	if (document.getElementById("birthdateParam").value != "") {
		if (params != ""){
			params += "&";
		}
		let date = new Date(document.getElementById("birthdateParam").value);
		let birthdate = date.toLocaleDateString("ru");
		params += "birthdate=" + birthdate;
	}
	if (document.getElementById("birthdateFrom").value != "") {
		if (params != ""){
			params += "&";
		}
		let date = new Date(document.getElementById("birthdateFrom").value);
		let birthdate = date.toLocaleDateString("ru");
		params += "birthdateFrom=" + birthdate;
	}
	if (document.getElementById("birthdateTo").value != "") {
		if (params != ""){
			params += "&";
		}
		let date = new Date(document.getElementById("birthdateTo").value);
		let birthdate = date.toLocaleDateString("ru");
		params += "birthdateTo=" + birthdate;
	}
	if (params != ""){
		params += "&";
	} 
	params = "?" + params + "page=" + document.getElementById("pageNumber").value + "&" +
				"pageItems=" + document.getElementById("countPages").value;
	if (sorting != "") {
		params += "&orderBy=" + sorting;
	}
	console.log(url+params);

	axios.get(url+params).then(response => {
		console.log(response);
		responseTextArea.textContent = "";
		response.data.forEach(function(customer){
			responseTextArea.textContent += printCustomer(customer);
		})
	}).catch(error => {
			console.log(error);
	    	console.log(error.response);
	        if (error.response) {
	        	responseTextArea.textContent = error.response.status + " " + error.response.statusText;
	            responseTextArea.textContent += JSON.stringify(error.response.data);
	        } else {
	            responseTextArea.textContent = error;
	        }
	    })
}

function getCustomerById(){
	let responseTextArea = document.getElementById("responseGet");
	let id = document.getElementById("customerId").value;
	console.log(url+"/"+ id);
	axios.get(url+"/"+ id).then(response => {
		console.log(response);
		responseTextArea.textContent = printCustomer(response.data);
	}).catch(error => {
	        console.log(error);
	    	console.log(error.response);
	        if (error.response) {
	        	responseTextArea.textContent = error.response.status + " " + error.response.statusText;
	            responseTextArea.textContent += JSON.stringify(error.response.data);
	        } else {
	            responseTextArea.textContent = error;
	        }
	    })
}

function printCustomer(customer){
	return "{ \n" +
		   "  firstName : " + customer["firstName"] + ", \n" +
		   "  lastName : " + customer["lastName"] + ", \n" +
		   "  middleName : " + customer["middleName"] + ", \n" +
		   "  birthdate : " + customer["birthdate"] + ", \n" +
		   "  id : " + customer["id"] + "\n}\n";
}

function addSorting(){
	if (sorting != ""){
		sorting += ",";
	}
	let e = document.getElementById("sortBy");
	let param = e.options[e.selectedIndex].value;

	if (document.getElementById("desc").checked){
		param = "-" + param;
	}
	sorting += param;
}

function cleanSorting(){
	sorting = "";
}