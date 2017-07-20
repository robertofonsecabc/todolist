var objectifyForm = function(formArray) {
	var returnArray = {};
	for (var i = 0; i < formArray.length; i++) {
		returnArray[formArray[i]['name']] = formArray[i]['value'];
	}
	return returnArray;
}

$.fn.serializeObject = function() {
	var o = {};
	var a = this.serializeArray();
	$.each(a, function() {
		if (o[this.name] !== undefined) {
			if (!o[this.name].push) {
				o[this.name] = [ o[this.name] ];
			}
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = this.value || '';
		}
	});
	return o;
};


var routes = {
	main : "http://localhost:8080/todolist/rest/todos"
}

var dom = {
	main : $('#main'),
	templateList : $('#templateList').html(),
	modal : $('#myModal')
}

/**
 * Adicionar um Todo
 */
var saveTodo = function(){
	
	var form = $('#formTodo');
	// var data = JSON.stringify(objectifyForm( form.serializeArray() ));
	
	console.log( JSON.stringify(form.serializeObject()) );
	
	return;
	
	// Mustache.parse(dom.templateList); 
	// dom.main.append( Mustache.render(dom.templateList, { } ) );
	
	$.ajax({
		type: "POST",
		url: routes.main,
		data: data,
		dataType : 'json',
		contentType: "application/json",
		success : function(result){
			console.log(result);
		}, 
		error : function(error){
			console.log(error);
		}
	});
}

var toogleCheck = function(itemId,target){
	var item = $(target).find('span.glyphicon');
	
	if(item.hasClass('glyphicon-unchecked')){
		item.removeClass('glyphicon-unchecked').addClass('glyphicon-check');
	}else{
		item.addClass('glyphicon-unchecked').removeClass('glyphicon-check');
	}
}

var addEdit = function(i){
	
	dom.modal.modal('show')
	
	if( i == 0 ){
		// Novo
	}else{
		console.log(i);
	}
}

var loadTodo = function(){
	Mustache.parse(dom.templateList);
	$.get( routes.main , function( data ) {
		for( i in data ){
			dom.main.append( Mustache.render(dom.templateList, data[i] ) );
		}
	});
}


$(document).ready(function(){
	loadTodo();
});