function sendUser() {
	$.ajax({
        url : "/register",
        type : "post",
        dataType:"text",
        data : {
        email : $('#email').val(),
        fullName : $('#fullName').val(),
        phone : $('#phone').val(),
        password : $('#password').val(),
        pass : $('#pass').val()
         },
         success : function (data){
         if (data == 2) document.getElementById('pmessenge').innerHTML = "Mật khẩu không trùng khớp";
         if (data == 3) document.getElementById('emessenge').innerHTML = "Email không tồn tại";
         if (data == 1) window.location.replace("/confirm");
         }
     });
}

function sendConfirm() {
	$.ajax({
        url : "/confirm",
        type : "post",
        dataType:"text",
        data : {
        code : $('#code').val()
         },
         success : function (data){
         if (data == 0) document.getElementById('messenge').innerHTML = "Mã xác nhận không tồn tại";
         if (data == 1) window.location.replace("/home");
         }
     });
}