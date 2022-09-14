

$('#checkAll').click(function () {    
    $('input:checkbox').prop('checked', this.checked);    
});

var editor;

ClassicEditor
    .create( document.querySelector( '#editor' ) )
    .then( newEditor => {
    	editor = newEditor;
    } )
    .catch( error => {
        console.error( error );
    } );
     


function sendProduct(){
   var form_data = new FormData();
   var totalfiles = document.getElementById('images').files.length;
   for (var index = 0; index < totalfiles; index++) {
      form_data.append("image[]", document.getElementById('images').files[index]);
   }
   console.log(editor.getData());	
    form_data.append("mainImage", document.getElementById('mainImage').files[0]);
    form_data.append('name', $('#name').val());
    form_data.append('price', $('#price').val());
    form_data.append('description', editor.getData());
    form_data.append('type', $('#type').val());
    $.ajax({
        url : "/addproduct",
        type : "post",
        dataType:'text',
        cache: false,
		contentType: false,
		processData: false,
        data : form_data,
         success : function (data){
         alert(data);
         }
     });
   }
   
 
function editProduct(){
    $.ajax({
        url : "/edit",
        type : "post",
        dataType:"text",
        data : {
		id: $('#id').val(),
        name : $('#name').val(),
        imageOne : $('#imageOne').val(),
        imageTwo : $('#imageTwo').val(),
        imageThree : $('#imageThree').val(), 
        imageFour : $('#imageFour').val(),
        price : $('#price').val(),
        description : $('#description').val(),
        type : $('#category').val(),
         },
         success : function (data){
         alert(data);
         }
     });
   }  
   
function clearForm(){
	document.getElementById('name').value = '';
	document.getElementById('mainImage').value = '';
	document.getElementById('images').value = '';
	document.getElementById('price').value = '';
	document.getElementById('type').value = '';
}

function deleteProduct(){
	var checkbox = document.getElementsByName('checkbox');
	var ids = [];
	 for (var i = 0; i < checkbox.length; i++){
	                if (checkbox[i].checked === true){
	                    ids.push(checkbox[i].value);
	                }
	            }
	 if (typeof ids !== 'undefined' && ids.length > 0) {
		if (confirm("Are you sure delete Products?")) {
			$.ajax({
		        url : "/deleteproduct",
		        type : "post",
		        dataType:"text",
		        data : {
		        ids : JSON.stringify(ids)
		         },
		         success : function (data){
		         alert(data);
		         }
		     });
	     }
     } else {
		alert('Please check delete products!')
}
}

function reset() {
	location.reload();
}

function viewImages(id) {
	$.ajax({
        url : "/viewimage",
        type : "post",
        dataType:"text",
        data : {
        id : id
         },
         success : function (images){
         $('#images').html(images);
         }
     });
}

function searchProduct() {
	console.log($('#keyword').val());
	$.ajax({
        url : "/searchproduct",
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


