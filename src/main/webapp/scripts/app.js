var routes = {
	main : "http://localhost:8080/todolist/rest/todos"
}

var dom = {
	main : $('#main'),
	templateList : $('#templateList').html(),
	modal : $('#myModal'),
	formTodo : $('#formTodo'),
	itemContent : $('#itemContent'),
	templateItem : $('#templateItem').html()
}

var addItem = function(){
	Mustache.parse(dom.templateItem);
	dom.itemContent.append( Mustache.render(dom.templateItem, { }) );
}

var clearModal = function(){
	dom.formTodo[0].reset();
	dom.itemContent.html(''); // Limpar os itens
}


/**
 * Adicionar um Todo
 */
var saveTodo = function(){
	
	
	data = { "name" :  "" };
	
	
	
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