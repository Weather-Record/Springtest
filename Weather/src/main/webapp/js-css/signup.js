window.addEventListener("load", function(event){
	//DOM 객체 찾아오기
	var signup = document.getElementById("signup");
	
	var id = document.getElementById("id");
	var email = document.getElementById("email");
	var pw = document.getElementById("pw");
	var pw1 = document.getElementById("pw1");
	var nickname = document.getElementById("nickname");
	
	var msg = document.getElementById("msg");
	var idmsg = document.getElementById("idmsg");
	var emailmsg = document.getElementById("emailmsg");
	var pwmsg = document.getElementById("pwmsg");
	var nicknamemsg = document.getElementById("nicknamemsg");
	
	var signupbtn = document.getElementById("signupbtn");
	var mainbtn = document.getElementById("mainbtn");
	var loginbtn = document.getElementById("loginbtn");
	
 	mainbtn.addEventListener("click", function(event){
 		location.href="../";
	});
	
	loginbtn.addEventListener("click", function(event){
 		location.href="login";
	});
	
	id.addEventListener("focusout", function(event){
		var url="idcheck?id=" + id.value;
	  	var request=new XMLHttpRequest();
	  	request.open("get", url, true);
	  	request.send('');
	  	request.addEventListener('load', function(e){
	  		var map = JSON.parse(e.target.responseText);
	  		if(map.idcheck == false){
			 	idmsg.innerHTML = "사용 불가능한 ID입니다.";
			 	idmsg.style.color = "Red";
			}else{
				idmsg.innerHTML = "사용 가능한 ID입니다.";
			 	idmsg.style.color = "Blue";
			}
	  	})
	})
	
	email.addEventListener("focusout", function(event){
		var url="emailcheck?email=" + email.value;
	  	var request=new XMLHttpRequest();
	  	request.open("get", url, true);
	  	request.send('');
	  	request.addEventListener('load', function(e){
	  		var map = JSON.parse(e.target.responseText);
	  		if(map.emailcheck == false){
			 	emailmsg.innerHTML = "사용 불가능한 이메일입니다.";
			 	emailmsg.style.color = "Red";
			}else{
				emailmsg.innerHTML = "사용 가능한 이메일입니다.";
			 	emailmsg.style.color = "Blue";
			}
	  	})
	})
	
	nickname.addEventListener("focusout", function(event){
		var url="nicknamecheck?nickname=" + nickname.value;
	  	var request=new XMLHttpRequest();
	  	request.open("get", url, true);
	  	request.send('');
	  	request.addEventListener('load', function show(e){
	  		var map = JSON.parse(e.target.responseText);
	  		if(map.nicknamecheck == false){
			 	nicknamemsg.innerHTML = "사용 불가능한 별명입니다.";
			 	nicknamemsg.style.color = "Red";
			}else{
				nicknamemsg.innerHTML = "사용 가능한 별명입니다.";
			 	nicknamemsg.style.color = "Blue";
			}
	  	})
	})
	
	
	signupbtn.addEventListener("click", function(event){
		var flag = false;
		emailmsg.innerHTML = '';
		pwmsg.innerHTML = ''
		nicknamemsg.innerHTML = '';
		
		if (id.value.trim().length < 1) {
			idmsg.innerHTML = 'ID는 필수 입력입니다.<br/>';
			flag = true;
		} 
		
		if (email.value.trim().length < 1) {
			emailmsg.innerHTML = '이메일은 필수 입력입니다.<br/>';
			flag = true;
		} else {
			var emailRegExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
			if (!emailRegExp.test(email.value.trim())) {
					emailmsg.innerHTML = '잘못된 이메일 형식입니다.<br/>';
					flag=true;
			}
		}

		if (pw.value.trim().length < 1) {
			pwmsg.innerHTML += '비밀번호는 필수 입력입니다.<br/>';
			flag = true;
		}
		else {
			if (pw.value.trim() !== pw1.value.trim()) {
				pwmsg.innerHTML += '2개의 비밀번호는 같아야 합니다.<br/>';
				flag = true;
			}
		}

		if (nickname.value.trim().length < 1) {
			nicknamemsg.innerHTML += '별명은 필수 입력입니다.<br/>';
			flag = true;
		} else {
			var nicknameRegExp = /^[a-zA-z가-힣0-9]{2,10}$/;
			if (!nicknameRegExp.test(nickname.value.trim())) {
				nicknamemsg.innerHTML = '닉네임은 2~10자까지 가능합니다.<br/>';
				flag = true;
			}
		}
		
		var url="signup";
	  	var request=new XMLHttpRequest();
		  	
		request.open("post", url, true);
		var formdata = new FormData(signup);
		request.send(formdata);
		request.addEventListener('load', function show(e){
			 var map = JSON.parse(e.target.responseText);
			 if(map.result == true){
			 	location.href = "../";
			 }else{
			 	if(map.idcheck == false){
			 		idmsg.innerHTML = "사용 불가능한 ID입니다.";
			 		idmsg.style.color = "Red";
			 	}else{
			 		idmsg.innerHTML = "사용 가능한 ID입니다.";
			 		idmsg.style.color = "Blue";
			 	}
			 	if(map.emailcheck == false){
			 		emailmsg.innerHTML = "사용 불가능한 이메일입니다.";
			 		emailmsg.style.color = "Red";
			 	}else{
			 		emailmsg.innerHTML = "사용 가능한 이메일입니다.";
			 		emailmsg.style.color = "Blue";
			 	}
			 	if(map.nicknamecheck == false){
			 		nicknamemsg.innerHTML = "사용 불가능한 닉네임입니다.";
			 		nicknamemsg.style.color = "Red";
			 	}else{
			 		nicknamemsg.innerHTML = "사용 가능한 닉네임입니다.";
			 		nicknamemsg.style.color = "Blue";
			 	}
			 }
		  })
	});
});