jQuery(document).ready( function () {
    //$idmnus=$("#opmnu").val().split(":");
    console.log("eventos.js");
    var dataTablep = $('#tbeventos').DataTable({
        processing: true,
        serverSide: false,
        responsive: true,
        autoWidth: false,
        order: [],
        ajax:{
            url :baseurl+"eventos/paginate", // json datasource
            type: "post",  // method  , by default get
            complete: function(){
                table=$('#tbeventos');
            },
            error: function(xhr, textStatus, errorThrown){  // error handling
                $("#tbeventos_wrapper").html("");
                $("#tbeventos_wrapper").append('<div class="alert alert-danger alert-dismissable"><tr><th colspan="3">No data found in the server</th></tr></div>');
            }
        },
        "aoColumns": [
            { "mData": "id" },
            { "mData": "nombre"},
            { "mData": "generaPerdida"},
            { "mData": "lugar"},
            { "mData": "responsable"},
            { "mData": "fechaInicioEvento"},
            { "mData":null,
                "sClass": "text-center",
                "mRender": function(data, type, full) {
                    return (data.criticidadId==1)? '<span class=\"label label-danger\">'+data.criticidad+'</span>':'<span class="label label-success">'+data.criticidad+'</span>';
                }
            },
            { "mData":null,
                "bSortable": false,
                "sClass": "text-center",
                "mRender": function(data, type, full) {

                    buttons='<button data-toggle="modal" data-target="#myModalViewEvento" data-remote="false" '+
                        'type="button" data-id="'+data.id+'" id="btnEditEvento" class="btn btn-success btn-xs" '+
                        'href="' + baseurl +'eventos/view/edit/'+data.id+'" title="Edit" ><i class="fa fa-edit"></i></button> ';
                    buttons+='<button data-toggle="modal" data-target="#myModalViewEvento" data-remote="false" '+
                        'type="button" data-id="'+data.id+'" id="btnViewEvento" class="btn btn-info btn-xs" '+
                        'href="' + baseurl +'eventos/view/detail/'+data.id+'" title="Ver" ><i class="fa fa-eye"></i></button> ';
					buttons+='<button type="button" data-id="'+data.id+'" id="btnDeleteEvento" class="btn btn-danger btn-xs" title="Eliminar"><i class="fa fa-trash"></i></button>';
                    return buttons;
                }
            }

        ]
    });


    $('#myModalViewEvento').on('show.bs.modal', function (e) {

        console.log("myModalViewEvento");

        var btn = $(e.relatedTarget);
        //var idevento=btn.data('id');
        var data=null;
        var title="Nuevo evento";
        var frm='#formSaveEvento';

        if(btn.attr("id")==="btnEditEvento"){
            console.log("btnEditEvento");
            //data={"idevento":idevento};
            title="Modificar evento";
            frm='#formEditEvento';
        }

		if(btn.attr("id")==="btnViewEvento"){
            console.log("btnViewEvento");
            title="Detalle del Evento";
            frm='#formViewEvento';
        }
        $.get(btn.attr("href"), function( data ) {
            $('#modal-title').html(title);
            $('#modal-body').html(data);
            $('#fechaInicioEvento,#fechaDescubrimientoEvento').datetimepicker({
                format: 'DD/MM/YYYY HH:mm'
            });
            //$("#txtmontoPerdida").inputmask('Regex', {regex: "^[0-9]{1,6}(\\.\\d{1,2})?$"});
            $(document).on("input", "#txtmontoPerdida", function (e) {
                this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');
            });
			if(btn.attr("id")==="btnEditRiesgo") {
                idsProcesos = JSON.parse($('#listprocesos').attr("data-ids"));
                console.log(idsProcesos)
                $('#listprocesos').val(idsProcesos);
            }
			if(btn.attr("id")==="btnViewEvento"){
				idsProcesos = JSON.parse($('#listprocesos').attr("data-ids"));
                console.log(idsProcesos)
                $('#listprocesos').val(idsProcesos);
				$("#formViewEvento :input").prop("disabled", true);
				$('#btnClose').removeAttr("disabled");
			}
            $("#listprocesos").select2();
            $(frm).validator();
        });
    });

    $(document).on( "change", "#cboTiposevento", function(e) {
        console.log("cboGrado");
        //if(ValidaSession()){ return; }
        var tipoeventoId = $(this).val();
        if(tipoeventoId){

            $.ajax({
                type:"GET",
                url:baseurl+"eventos/tipos/"+tipoeventoId+"/categoria",
                //data:({gradoId: gradoId}),
                success:function(dataJson){
                    console.log(dataJson.length);
                    if(dataJson.length > 0){
                        $('#cboCategoriasevento').html('<option value="">Seleccione</option>');
                        option="<option value=''>Seleccione</option>";
                        dataJson.forEach(function (item) {
                            option+="<option value='"+item.id+"'>"+item.nombre+"</option>";
                        });
                    }else{
                        option="<option value=''>Categoria no disponible</option>";
                    }
                    $('#cboCategoriasevento').html(option);
                }
            });
        }else{
            $('#cboAnioElec').html('<option value="">Seleccione</option>');
            //$('#cboCity').html('<option value="">Select state first</option>');
        }
    });

    $(document).on("submit","#formSaveEvento",function(e){

        if (e.isDefaultPrevented()) {
            return false;
        }

        frm=$(this);
        e.preventDefault();
        var msj=$("#msjformevento");
        msj.removeAttr('class');
        msj.html("");

        //$("#popup").modal("show");
        console.log("formSaveEvento");

        $.ajax({
            type: "POST",
            url: baseurl+"eventos/add",
            contentType: 'application/json',
            data:JSON.stringify(frm.serializeJSON()),
            success: function(result){
                //  alert(result);
                //  chatWith('9','name');
                if(result==1){
                    alerts(0,msj,"El evento se grabo con exito");
                    loadDataTable("#tbeventos");
                    frm.trigger('reset');
                }else{
                    alerts(2,msj,"El evento ya existe");
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

 	
$(document).on("submit","#formEditEvento",function(e){

        if (e.isDefaultPrevented()) {
            return false;
        }

        frm=$(this);
        e.preventDefault();
        var msj=$("#msjformevento");
        msj.removeAttr('class');
        msj.html("");

        //$("#popup").modal("show");
        console.log("formEditEvento");

        $.ajax({
            type: "PUT",
            url: baseurl+"eventos/edit",
            contentType: 'application/json',
            data:JSON.stringify(frm.serializeJSON()),
            success: function(result){
                if(result==1){
                    alerts(0,msj,"El evento se edito con exito");
                    loadDataTable("#tbeventos");
                }else{
                    alerts(2,msj,"Ocurrio un problema al editar el evento");
                }
            },
            error: function(err) {
                console.log(err);
                alerts(3,msj,"A ocurrido un error interno !!!");
            }
        });
    });
	
	//btnElimina
    $(document).on("click","#btnDeleteEvento",function(e){

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
                    url: baseurl+"eventos/delete/"+id,
                    type: 'DELETE',
                    success: function (result) {
                        if(result==1){
                            loadDataTable("#tbeventos");
                            ezBSAlert({ headerText:"success", messageText: "El evento se elimino con exito", alertType: "success"});
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
})