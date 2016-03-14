function InitChat() {
	toggleButtomBar(false);

	document.getElementById('chatLogin').addEventListener('click', function () {
  		login();
	});

	document.getElementById('chatSend').addEventListener('click', function () {
  		sendMessage();
	});

	document.getElementById('chatUserTitle').addEventListener('click', function () {
  		clearSelectedUser();
	});

}

function toggleButtomBar(sendVisible) {
	var elemLogin = document.getElementById("chatLoginDiv");
	var elemSend = document.getElementById("chatSendDiv");
	if(sendVisible) {
		elemLogin.style.display = 'none';
		elemSend.style.display = 'block';
	} else {
		elemLogin.style.display = 'block';
		elemSend.style.display = 'none';
	}
}

function logout() {
	toggleButtomBar(false);

	document.getElementsByClassName('logoutButton')[0].removeEventListener("click", logout);

	var elem = document.getElementById('chatTopBar');
	elem.innerHTML = '';
}

function login() {
	toggleButtomBar(true);

	var user = document.getElementById('chatUser').value;

	var span = document.createElement('span');
	span.setAttribute('id', 'spanLoginUser');
	span.innerHTML = '<img src="images/avatar_my.png" width="23" height="23" /> <span class="name">' + user + '</span><a href="#" class="logoutButton rounded">Выйти</a>';
    	document.getElementById('chatTopBar').appendChild(span);

	document.getElementsByClassName('logoutButton')[0].addEventListener('click', logout);
}

function sendMessage() {
	var text = document.getElementById('chatText').value;
}

function clearSelectedUser() {
}

InitChat();