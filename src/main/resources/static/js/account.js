

function reset() {
	location.reload();
}

function deleteUser(id){
		if (confirm("Are you sure delete this User?")) {
			$.ajax({
		        url : "/admin/deleteuser",
		        type : "post",
		        dataType:"text",
		        data : {
		        id : id
		         },
		         success : function (data){
		         alert(data);
		         }
		     });
	     } 
}

function resetUser(id){
		if (confirm("Are you sure reset this User?")) {
			$.ajax({
		        url : "/admin/resetuser",
		        type : "post",
		        dataType:"text",
		        data : {
		        id : id
		         },
		         success : function (data){
		         alert(data);
		         }
		     });
	     } 
}

function editUser(){
    var form_data = new FormData();
    form_data.append("id",$('#id').val());	
    form_data.append('fullName', $('#name').val());
    form_data.append('email', $('#email').val());
    form_data.append('phone', $('#phone').val());
    form_data.append('role', $('#role').val());
    form_data.append('enabled', $('#enabled').val());
    $.ajax({
        url : "/admin/edituser",
        type : "post",
        dataType:'text',
        cache: false,
		contentType: false,
		processData: false,
        data : form_data,
         success : function (data){
         alert(data);
         window.location.replace("/admin/account")
         }
     });
   }  
   
   
   function searchUser() {
	$.ajax({
        url : "/admin/searchuser",
        type : "post",
        dataType:"text",
        data : {
        keyword : $('#keyword').val()
         },
         success : function (data){
         $('body').html(data);
         }
     });
}