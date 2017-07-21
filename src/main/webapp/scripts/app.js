var routes = {
	main : "http://localhost:8080/todolist/rest/todos",
	item : "http://localhost:8080/todolist/rest/items",
}

/**
* Cache de objetos
**/
var dom = {
	main : $('#main'),
	templateList : $('#templateList').html(),
	modal : $('#myModal'),
	formTodo : $('#formTodo'),
	itemContent : $('#itemContent'),
	templateItem : $('#templateItem').html(),
	name : $('#name'),
	id : $('#id')
}

/**
* Adicionar item
**/
var addItem = function(){
	// contabilizar a quantidade de itens
	var itens = dom.itemContent.find('.form-group').length;
	Mustache.parse(dom.templateItem);
	dom.itemContent.append( Mustache.render(dom.templateItem, {  item : itens } ) );
}

/**
* Esconder um item para ser removido no processamento
**/
var removeItem = function(object ){
	var item = $(object);
	var id = parseInt(item.attr('data-id'));
	if( id > 0 ){
		$.ajax({
			type: "DELETE",
			url: routes.item + '/' + id,
			dataType : 'json',
			contentType: "application/json",
			success : function(result){
				item.parents('.form-group').slideUp(function(){
					$(this).remove();
				});
				// Remover item do main
				dom.main.find('#listItem_' + id).slideUp(function(){
					$(this).remove();
				});
				// TODO : Reprocessar valor da lista
			}, 
			error : function(error){
				console.log('Erro');
				console.log(error);
			}
		});
	}else{
		item.parents('.form-group').slideUp(function(){
			$(this).remove();
		});
	}
}

/**
* Função responsável por limpar os dados do formulário
**/
var clearModal = function(){
	dom.formTodo[0].reset();
	dom.itemContent.html(''); // Limpar os itens
}

/**
 * Adicionar um Todo
 */
var saveTodo = function(){
	Mustache.parse(dom.templateList);
	
	var data = new Object();
	var items = [];
	var total = 0;
	
	// Remover itens disableds
	dom.itemContent.find('.form-group.disabled').remove();
	
	if( dom.itemContent.find('.form-group').length == 0 ){
		// TODO : Implementar uma mensagem 
		alert("Adicione um Item à lista");
		return;
	}
	
	// Push de Itens e somar os valores
	dom.itemContent.find('.form-group').each(function( index , item ){
		// TODO : Validar aqui se os dados estão corretos
		var valor = parseFloat($(item).find('.value').val().replace(/([,])/g,"."));
		
		if( isNaN ( valor )){
			valor = parseFloat("0.0");
		}
		
		total+=valor;
		var item = {
			"name" : $(item).find('.name').val() ,
			"value" : valor,
			"id" : $(item).find('.id').val()
		}
		items.push( item );
	});
	
	data.id = dom.id.val();
	data.name = dom.name.val(); 
	data.items = items;
	data.total = total;
	
	$.ajax({
		type: data.id>0?"PUT":"POST",
		url: data.id>0?routes.main + '/' + data.id:routes.main,
		data: JSON.stringify(data),
		dataType : 'json',
		contentType: "application/json",
		success : function(result){
			clearModal();
			dom.modal.modal('hide');
			if( data.id > 0 ){
				dom.main.find('#todo_' + result.id ).slideUp(function(){
					dom.main.append( Mustache.render( dom.templateList, result ) );
				});
			}else{
				dom.main.append( Mustache.render( dom.templateList, result ) );
			}
			
			
		}, 
		error : function(error){
			console.log('Erro');
			console.log(error);
		}
	});
}

var toogleCheck = function(itemId,target){
	var item = $(target).find('span.glyphicon');
	var data = new Object();
	
	data.id = itemId;
	
	$.ajax({
		type: "PUT",
		url: routes.item + '/' + itemId,
		data: JSON.stringify(data),
		dataType : 'json',
		contentType: "application/json",
		success : function(result){
			if(item.hasClass('glyphicon-unchecked')){
				item.removeClass('glyphicon-unchecked').addClass('glyphicon-check');
			}else{
				item.addClass('glyphicon-unchecked').removeClass('glyphicon-check');
			}
		}, 
		error : function(error){
			console.log('Erro');
			console.log(error);
		}
	});
}


var addEdit = function(i){
	
	Mustache.parse(dom.templateItem);
	
	
	if( i == 0 ){
		clearModal();
		dom.modal.modal('show');
		return;
	}
	
	// Fazer busca de item
	$.get( routes.main + "/" + i , function( data ) {

		dom.formTodo.find('#id').val( data.id );
		dom.formTodo.find('#name').val( data.name );
		
		for( i in data.items ){
			dom.itemContent.append( Mustache.render(dom.templateItem, data.items[i] ) );
		}
		
		dom.modal.modal('show');
	});
		
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
	
	dom.modal.on('hidden.bs.modal', function (e) {
		clearModal();
	})
	
});