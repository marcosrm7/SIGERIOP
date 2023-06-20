jQuery(document).ready( function () {
	
	console.log("control.js");
    var dataTablep = $('#tbControls').DataTable({
        processing: true,
        serverSide: false,
        responsive: true,
        autoWidth: false,
        order: [],
        ajax:{
            url :baseurl+"controles/paginate", // json datasource
            type: "post",  // method  , by default get
            complete: function(){
                table=$('#tbControls');
            },
            error: function(xhr, textStatus, errorThrown){  // error handling

                $("#tbControls_wrapper").html("");
                $("#tbControls_wrapper").append('<div class="alert alert-danger alert-dismissable"><tr><th colspan="3">No data found in the server</th></tr></div>');
            }
        },
        "aoColumns": [
            { "mData": "id" },
            { "mData": "nombre"},
			{ "mData": "tipoControl"},
            { "mData": "resultadoDisenoControl"},
			{ "mData": "resultadoEjecucionControl"},
			{ "mData": "resultadoControl"},
            { "mData":null,
                "bSortable": false,
                "sClass": "text-center",
                "mRender": function(data, type, full) {
					buttons='<button data-toggle="modal" data-target="#myModalViewControl" data-remote="false" '+
                        'type="button" data-id="'+data.id+'" id="btnEditControl" class="btn btn-info btn-xs" '+
                        'href="' + baseurl +'controles/view/edit/'+data.id+'" title="Editar" ><i class="fa fa-edit"></i></button> ';
                    buttons+='<button data-toggle="modal" data-target="#myModalViewControl" data-remote="false" '+
                        'type="button" data-id="'+data.id+'" id="btnViewControl" class="btn btn-success btn-xs" '+
                        'href="' + baseurl +'controles/view/detail/'+data.id+'" title="Ver" ><i class="fa fa-eye"></i></button> ';
                    buttons+='<button type="button" data-id="'+data.id+'" id="btnDeleteControl" class="btn btn-danger btn-xs" title="Eliminar"><i class="fa fa-trash"></i></button>';
					return buttons;
                }
            }

        ]
    });
	
	$('#myModalViewControl').on('show.bs.modal', function (e) {
        console.log("myModalViewControl");
        var btn = $(e.relatedTarget);
        //var idControl=btn.data('id');
        var data=null;
        var title="Nuevo Control";
        var frm='#formSaveControl';

        if(btn.attr("id")==="btnEditControl"){
            console.log("btnEditControl");
            //data={"idControl":idControl};
            title="Modificar Control";
            frm='#formEditControl';
        }
		
		if(btn.attr("id")==="btnViewControl"){
            console.log("btnViewControl");
            title="Detalle del Control";
            frm='#formViewControl';
        }
		
        $.get(btn.attr("href"), function( data ) {
            $('#modal-title').html(title);
            $('#modal-body').html(data);
			if(btn.attr("id")==="btnEditControl") {
                idsRiesgos = JSON.parse($('#listRiesgos').attr("data-ids"));
                console.log(idsRiesgos)
                $('#listRiesgos').val(idsRiesgos);
            }
			
			if(btn.attr("id")==="btnViewControl"){
				idsRiesgos = JSON.parse($('#listRiesgos').attr("data-ids"));
                console.log(idsRiesgos)
                $('#listRiesgos').val(idsRiesgos);
				$("#formViewControl :input").prop("disabled", true);
				$('#btnClose').removeAttr("disabled");
			}
			
			$("#listRiesgos").select2({});
			changeResultado();
            $(frm).validator();
        });

    });

    $(document).on("submit","#formSaveControl",function(e){

        if (e.isDefaultPrevented()) {
            return false;
        }

        frm=$(this);
        e.preventDefault();
        var msj=$("#msjmntControl");
        msj.removeAttr('class');
        msj.html("");

        //$("#popup").modal("show");
        console.log("formControl");

        $.ajax({
            type: "POST",
            url: baseurl+"controles/add",
            contentType: 'application/json',
            data:JSON.stringify(frm.serializeJSON()),
            success: function(result){
                //  alert(result);
                //  chatWith('9','name');
                if(result==1){
                    alerts(0,msj,"El Control se grabo con exito");
                    loadDataTable("#tbControls");
                    frm.trigger('reset');
					$("#listRiesgos").val();
		            $("#listRiesgos").trigger('change');
                }else{
                    alerts(2,msj,"El Control ya existe");
                }
            },
            error: function(err) {
                //estableceAlerta("#msj_urs","errors","A ocurrido un error interno !!!");
                console.log(err);
                alerts(3,msj,"A ocurrido un error interno !!!");
            }
        });
        //  alert("ajax");
    });
//	$("#cboEstadoResponsable").on("change",function(e){
	
	//btnElimina
    $(document).on("click","#btnDeleteControl",function(e){

        var obj = this;
        ezBSAlert({
            type: "confirm",
            headerText:"Confirm",
            messageText: "Esta seguro de esto?",
            alertType: "warning"
        }).done(function (e) {
            var id = $(obj).data('id');
            if(e){
                $.ajax({
                    url: baseurl+"controles/delete/"+id,
                    type: 'DELETE',
                    success: function (result) {
                        if(result==1){
                            loadDataTable("#tbControls");
                            ezBSAlert({ headerText:"success", messageText: "El control se elimino con exito", alertType: "success"});
                        }else{
                            ezBSAlert({ headerText:"Error",messageText: "No se completo el proceso.. !!!", alertType: "danger"});
                        }
                    },
                    error: function () {
                        ezBSAlert({ headerText:"Error",messageText: "A ocurrido un error interno !!!", alertType: "danger"});
                    }
                });
            }
        });
    });


});


	function viewResponsable(){
		 var x = document.getElementById("responsableDiv");
	    if ($("#cboEstadoResponsable").val()=='15') {
	        x.style.display = "block";
	    } else {
	        x.style.display = "none";
	    }
	};
	
	function changeResultado(){
		var tipoControl= ~~$('#cboTipoControl').find(':selected').attr('data-peso');
		var tipoEjecucion= ~~$('#cboTipoEjecucion').find(':selected').attr('data-peso');
		var frecuenciaEjecucion= ~~$('#cboFrecuenciaEjecucion').find(':selected').attr('data-peso');
		var estadoDocumentacion= ~~$('#cboEstadoDocumentacion').find(':selected').attr('data-peso');
		var estadoEvidencia= ~~$('#cboEstadoEvidencia').find(':selected').attr('data-peso');
		var estadoResponsable= ~~$('#cboEstadoResponsable').find(':selected').attr('data-peso');
		var estadoEvento= ~~$('#cboEstadoEvento').find(':selected').attr('data-peso');
		var setadoEfectividad= ~~$('#cboEstadoEfectividad').find(':selected').attr('data-peso');
		var estadoEvidencia2= ~~$('#cboEstadoEvidencia2').find(':selected').attr('data-peso');
		
		var resultadoEjecucion=estadoEvento+setadoEfectividad+estadoEvidencia2;
		var resultadoDisenoControl=tipoControl+tipoEjecucion+frecuenciaEjecucion+estadoDocumentacion+estadoEvidencia+estadoResponsable;
		var resultadoControl=(resultadoEjecucion+resultadoDisenoControl)/2;
		
		$('#txtresultadoDisenoControl').val(resultadoDisenoControl);
		$('#txtResultadoEjecucionControl').val(resultadoEjecucion);
		$('#txtResultadoControl').val(resultadoControl);
		
		
	}
	
	  $(document).on("submit","#formEditControl",function(e){

        if (e.isDefaultPrevented()) {
            return false;
        }

        frm=$(this);
        e.preventDefault();
        var msj=$("#msjmntControl");
        msj.removeAttr('class');
        msj.html("");

        //$("#popup").modal("show");
        console.log("formEditControl");

        $.ajax({
            type: "PUT",
            url: baseurl+"controles/edit",
            contentType: 'application/json',
            data:JSON.stringify(frm.serializeJSON()),
            success: function(result){
                if(result==1){
                    alerts(0,msj,"El control se edito con exito");
                    loadDataTable("#tbControls");
                }else{
                    alerts(2,msj,"Ocurrio un problema al editar el control");
                }
            },
            error: function(err) {
                console.log(err);
                alerts(3,msj,"A ocurrido un error interno !!!");
            }
        });
    });	
	
	
	
	