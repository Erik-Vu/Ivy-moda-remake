
function logOut(id) {
	$.ajax({
        url : "/beforelogout",
        type : "post",
        dataType:"text",
        data : {
        id : id
         },
         success : function (data){
         	if(data == 1) window.location.replace("/logout");
         }
     });
}

function sendEmail() {	
	$.ajax({
        url : "/sendemail",
        type : "post",
        dataType:"text",
        data : {
       	email : $('#remail').val()
         },
         success : function (data){
         	if (data == 0) document.getElementById('message').innerText = "Email không tồn tại";
         	else document.getElementById('message').innerText = "Gửi email thành công";
         }
     });
}

function sendPassword() {	
	var pass = $("#newpass").val();
    var valid = pass == $("#renewpass").val();
    if(!valid) {
      $("#error").show();
      return;
    }
	$.ajax({
        url : "/sendpassword",
        type : "post",
        dataType:"text",
        data : {
       	password : $('#password').val(),
       	pass : pass,
         },
         success : function (data){
         	if (data == 0) document.getElementById('error').innerText = "Mã xác nhận không tồn tại";
         	else document.getElementById('error').innerText = "Đổi mật khẩu thành công";
         }
     });
}

function sendToken() {	
	$.ajax({
        url : "/sendtoken",
        type : "post",
        dataType:"text",
        data : {
         },
         success : function (data){
         	if (data == 1) $("#message").show();
         }
     });
}