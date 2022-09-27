

$('#checkAll').click(function () {    
    $('input:checkbox').prop('checked', this.checked);    
});

var editor;
var imageID;
var sizeID;

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
   var sizeCheck = document.getElementsByName('sizeCheck');
	var sizenames = [];
	 for (var i = 0; i < sizeCheck.length; i++){
	                if (sizeCheck[i].checked === true){
	                    sizenames.push(sizeCheck[i].value);
	                }
	            }
	form_data.append("sizenames", sizenames);	
    form_data.append("mainImage", document.getElementById('mainImage').files[0]);
    form_data.append('name', $('#name').val());
    form_data.append('price', $('#price').val());
    form_data.append('lastPrice', $('#lastPrice').val());
    form_data.append('description', editor.getData());
    form_data.append('type', $('#type').val());
    $.ajax({
        url : "/admin/addproduct",
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
    var form_data = new FormData();
    form_data.append("id",$('#id').val());	
    form_data.append("mainImage", document.getElementById('mainImage').files[0]);
    form_data.append('name', $('#name').val());
    form_data.append('price', $('#price').val());
    form_data.append('lastPrice', $('#lastPrice').val());
    form_data.append('description', editor.getData());
    form_data.append('type', $('#type').val());
    $.ajax({
        url : "/admin/editproduct",
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
   
function clearForm(){
	document.getElementById('name').value = '';
	document.getElementById('mainImage').value = '';
	document.getElementById('images').value = '';
	document.getElementById('price').value = '';
	document.getElementById('lastPrice').value = '';
	document.getElementById('type').value = '';
	editor.setData("");
	$('input:checkbox').removeAttr('checked');
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
		        url : "/admin/deleteproduct",
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
	imageID = id;
	$.ajax({
        url : "/admin/viewimages",
        type : "post",
        dataType:"text",
        data : {
        id : id
         },
         success : function (images){
         $('#view').html(images);
         }
     });
}

function deleteImages(id) {
	$.ajax({
        url : "/admin/deleteimages",
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

function sendImages() {
	 var form_images = new FormData();
   var totalfiles = document.getElementById('newimg').files.length;
   for (var index = 0; index < totalfiles; index++) {
      form_images.append("newimg[]", document.getElementById('newimg').files[index]);
   }
    form_images.append("id", imageID);
   $.ajax({
        url : "/admin/addimages",
        type : "post",
        dataType:'text',
        cache: false,
		contentType: false,
		processData: false,
        data : form_images,
         success : function (data){
         alert(data);
         }
     });
}

function viewSizes(id) {
	sizeID = id;
	$.ajax({
        url : "/admin/viewsizes",
        type : "post",
        dataType:"text",
        data : {
        id : id
         },
         success : function (images){
         $('#viewsize').html(images);
         }
     });
}

function deleteSizes(id) {
	$.ajax({
        url : "/admin/deletesizes",
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

function sendSizes() {
	 var form_sizes = new FormData();
    form_sizes.append("id", sizeID);
    var sizeCheck = document.getElementsByName('sizeCheck');
	var sizenames = [];
	 for (var i = 0; i < sizeCheck.length; i++){
	                if (sizeCheck[i].checked === true){
	                    sizenames.push(sizeCheck[i].value);
	                }
	            }
	form_sizes.append("sizes", sizenames);
   $.ajax({
        url : "/admin/addsizes",
        type : "post",
        dataType:'text',
        cache: false,
		contentType: false,
		processData: false,
        data : form_sizes,
         success : function (data){
         alert(data);
         }
     });
}

function viewDes(id) {
	 $.ajax({
        url : "/admin/viewdes",
        type : "post",
        dataType:"text",
        data : {
        id : id
         },
         success : function (data){
         $('#viewDes').html(data);
         }
     });
}

function searchProduct() {
	$.ajax({
        url : "/admin/searchproduct",
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

function sendSize() {
	$.ajax({
        url : "/admin/addsize",
        type : "post",
        dataType:"text",
        data : {
        size : $('#sizename').val()
         },
         success : function (data){
         alert("add size: " + data + " successfuly");
         }
     });
}

function deleteSize(id) {
		$.ajax({
        url : "/admin/deletesize",
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

function sendType() {
	$.ajax({
        url : "/admin/addtype",
        type : "post",
        dataType:"text",
        data : {
        type : $('#typename').val()
         },
         success : function (data){
         alert("add type: " + data + " successfuly");
         }
     });
}

function deleteType(id) {
		$.ajax({
        url : "/admin/deletetype",
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