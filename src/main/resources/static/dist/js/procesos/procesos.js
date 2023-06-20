jQuery(document).ready( function () {
    //$idmnus=$("#opmnu").val().split(":");
    console.log("procesos.js");
    var dataTablep = $('#tbProcesos').DataTable({
        processing: true,
        serverSide: false,
        responsive: true,
        autoWidth: false,
        order: [],
        ajax:{
            url :baseurl+"procesos/paginate", // json datasource
            type: "post",  // method  , by default get
            complete: function(){
                table=$('#tbProcesos');
            },
            error: function(xhr, textStatus, errorThrown){  // error handling

                $("#tbProcesos_wrapper").html("");
                $("#tbProcesos_wrapper").append('<div class="alert alert-danger alert-dismissable"><tr><th colspan="3">No data found in the server</th></tr></div>');
            }
        },
        "aoColumns": [
            { "mData": "id" },
            { "mData": "nombre"},
            { "mData": "tipoprocesoNombre"},
            { "mData": "categoriaNombre"},
            { "mData":null,
                "bSortable": false,
                "sClass": "text-center",
                "mRender": function(data, type, full) {

                    buttons='<button data-toggle="modal" data-target="#myModalViewProceso" data-remote="false" '+
                        'type="button" data-id="'+data.id+'" id="btnEditProceso" class="btn btn-success btn-xs" '+
                        'href="' + baseurl +'procesos/view/edit/'+data.id+'" title="Edit" ><i class="fa fa-edit"></i></button> ';
                    buttons+='<button data-toggle="modal" data-target="#myModalViewProceso" data-remote="false" '+
                        'type="button" data-id="'+data.id+'" id="btnViewProceso" class="btn btn-info btn-xs" '+
                        'href="' + baseurl +'procesos/view/detail/'+data.id+'" title="Ver" ><i class="fa fa-eye"></i></button> ';
                    buttons+='<button type="button" data-id="'+data.id+'" id="btnDeleteProceso" class="btn btn-danger btn-xs" title="Eliminar"><i class="fa fa-trash"></i></button>';
                    return buttons;
                }
            }

        ]
    });


    $('#myModalViewProceso').on('show.bs.modal', function (e) {

        console.log("myModalViewProceso");

        var btn = $(e.relatedTarget);
        //var idProceso=btn.data('id');
        var data=null;
        var title="Nuevo Proceso";
        var frm='#formSaveProceso';

        if(btn.attr("id")==="btnEditProceso"){
            console.log("btnEditProceso");
            //data={"idProceso":idProceso};
            title="Modificar Proceso";
            frm='#formEditProceso';
        }
		
		if(btn.attr("id")==="btnViewProceso"){
            console.log("btnViewProceso");
            title="Detalle del Proceso";
            frm='#formViewProceso';
        }


        $.get(btn.attr("href"), function( data ) {
            $('#modal-title').html(title);
            $('#modal-body').html(data);
			
			if(btn.attr("id")==="btnViewProceso"){
				$("#formViewProceso :input").prop("disabled", true);
				$('#btnClose').removeAttr("disabled");
			}
			
            $(frm).validator();
        });
    });

    $(document).on("submit","#formSaveProceso",function(e){

        if (e.isDefaultPrevented()) {
            return false;
        }

        frm=$(this);
        e.preventDefault();
        var msj=$("#msjmntProceso");
        msj.removeAttr('class');
        msj.html("");

        //$("#popup").modal("show");
        console.log("formSaveProceso");

        $.ajax({
            type: "POST",
            url: baseurl+"procesos/add",
            contentType: 'application/json',
            data:JSON.stringify(frm.serializeJSON()),
            success: function(result){
                //  alert(result);
                //  chatWith('9','name');
                if(result==1){
                    alerts(0,msj,"El Proceso se grabo con exito");
                    loadDataTable("#tbProcesos");
                    frm.trigger('reset');
                }else{
                    alerts(3,msj,"Ocurrio un problema al insertar el proceso");
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

	$(document).on("submit","#formEditProceso",function(e){

        if (e.isDefaultPrevented()) {
            return false;
        }

        frm=$(this);
        e.preventDefault();
        var msj=$("#msjmntProceso");
        msj.removeAttr('class');
        msj.html("");

        //$("#popup").modal("show");
        console.log("formEditPlan");

        $.ajax({
            type: "PUT",
            url: baseurl+"procesos/edit",
            contentType: 'application/json',
            data:JSON.stringify(frm.serializeJSON()),
            success: function(result){
                //  alert(result);
                if(result==1){
                    alerts(0,msj,"El proceso se edito con exito");
                    loadDataTable("#tbProcesos");
                }else{
                    alerts(2,msj,"Ocurrio un problema al editar el proceso");
                }
            },
            error: function(err) {
                console.log(err);
                alerts(3,msj,"A ocurrido un error interno !!!");
            }
        });
    });

	//btnElimina
    $(document).on("click","#btnDeleteProceso",function(e){

        var obj = this;
        ezBSAlert({
            type: "confirm",
            headerText:"Confirm",
            messageText: "Esta seguro de esto?",
            alertType: "warning"
        }).done(function (e) {
            var id = $(obj).data('id');
			console.log("id->"+id);
            if(e){
                $.ajax({
                    url: baseurl+"procesos/delete/"+id,
                    type: 'delete',
                    success: function (result) {
                        if(result==1){
                            loadDataTable("#tbProcesos");
                            ezBSAlert({ headerText:"success", messageText: "El proceso se elimino con exito", alertType: "success"});
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