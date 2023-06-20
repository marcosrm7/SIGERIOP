jQuery(document).ready( function () {
	
	console.log("riesgo.js");
    var dataTablep = $('#tbRiesgos').DataTable({
        processing: true,
        serverSide: false,
        responsive: true,
        autoWidth: false,
        order: [],
        ajax:{
            url :baseurl+"riesgos/paginate", // json datasource
            type: "post",  // method  , by default get
            complete: function(){
                table=$('#tbRiesgos');
            },
            error: function(xhr, textStatus, errorThrown){  // error handling

                $("#tbRiesgos_wrapper").html("");
                $("#tbRiesgos_wrapper").append('<div class="alert alert-danger alert-dismissable"><tr><th colspan="3">No data found in the server</th></tr></div>');
            }
        },
        "aoColumns": [
            { "mData": "id" },
            { "mData": "nombre"},
            { "mData": "afectaNegocio"},
			{ "mData": "frecuenciaEjecucionNombre"},
			{ "mData": "impactoRiesgoNombre"},
			{ "mData": "responsable"},
            { "mData":null,
                "bSortable": false,
                "sClass": "text-center",
                "mRender": function(data, type, full) {
					buttons='<button data-toggle="modal" data-target="#myModalViewRiesgo" data-remote="false" '+
                        'type="button" data-id="'+data.id+'" id="btnEditRiesgo" class="btn btn-info btn-xs" '+
                        'href="' + baseurl +'riesgos/view/edit/'+data.id+'" title="Editar" ><i class="fa fa-edit"></i></button> ';
                    buttons+='<button data-toggle="modal" data-target="#myModalViewRiesgo" data-remote="false" '+
                        'type="button" data-id="'+data.id+'" id="btnViewRiesgo" class="btn btn-success btn-xs" '+
                        'href="' + baseurl +'riesgos/view/detail/'+data.id+'" title="Ver" ><i class="fa fa-eye"></i></button> ';
                    buttons+='<button type="button" data-id="'+data.id+'" id="btnDeleteRiesgo" class="btn btn-danger btn-xs" title="Eliminar"><i class="fa fa-trash"></i></button>';
                    return buttons;
                }
            }

        ]
    });
	
	$('#myModalViewRiesgo').on('show.bs.modal', function (e) {
        console.log("myModalViewRiesgo");
        var btn = $(e.relatedTarget);
        //var idRiesgo=btn.data('id');
        var data=null;
        var title="Nuevo Riesgo";
        var frm='#formSaveRiesgo';

        if(btn.attr("id")==="btnEditRiesgo"){
            console.log("btnEditRiesgo");
            //data={"idRiesgo":idRiesgo};
            title="Modificar Riesgo";
            frm='#formEditRiesgo';
        }

		if(btn.attr("id")==="btnViewRiesgo"){
            console.log("btnViewRiesgo");
            title="Detalle del Riesgo";
            frm='#formViewRiesgo';
        }


        $.get(btn.attr("href"), function( data ) {
            $('#modal-title').html(title);
            $('#modal-body').html(data);
            $(frm).validator();

			if(btn.attr("id")==="btnEditRiesgo" || btn.attr("id")==="btnViewRiesgo") {
                idsProcesos = JSON.parse($('#listprocesos').attr("data-ids"));
                console.log(idsProcesos)
                $('#listprocesos').val(idsProcesos);
            }

            $("#listprocesos").select2({})

			var counter = 1;
            var tbp = $('#tblCausa').DataTable( {
                responsive: true,
                autoWidth: false,
                searching: false,
                ordering: false,
                lengthChange: false,
                "paging": false,
                info: true,
                dom:  "<'row'<'col-sm-4'B><'col-sm-4'l><'col-sm-4'f>>" +
                    "<'row'<'col-sm-12'<'table-responsive' tr>>>" +
                    "<'row'<'col-sm-5'i><'col-sm-7'p>>",
                buttons: [
                    {
                        text: 'Agregar',
                        className: 'btn btn-block btn-default btn-xs',
                        action: function ( e, dt, node, config ) {

                            dt.row.add( [
                               
                                '<div class="form-group" style="display: flex;">' +
                                '<select id="cboCausa" class="form-control " data-error="*" name="causas[][tipoCausaId]" required>' +
                                '<option value="">Seleccione</option>' +
                                '<option value="1">Procesos</option>' +
								'<option value="2">Infraestructura</option>' +
								'<option value="3">Tecnología</option>' +
								'<option value="4">Factores externos</option>' +
								'<option value="5">Recurso humano</option>' +
                                '</select><div class="help-block with-errors"></div>' +
                                '</div>',
								'<div class="form-group" style="display: flex;"><input type="text" style="width:100%"  data-error="*" class="form-control " name="causas[][descripcion]"  required><div class="help-block with-errors"></div></div>',
                                '<a class="btn btn-danger btn-sm btnDeleteItem"><span class="glyphicon glyphicon-trash"></span></a>'
                            ] ).draw( false );
                            console.log(counter);
                            counter++;

                            $("#formSaveRiesgo").validator("update");
                            $("#msjtblCausa").text("");
                        }
                    }
                ],
                "columnDefs": [
                    { "width": "15%", "targets": 0 },
                    { "width": "80%", "targets": 1 },
                    { "width": "5%", "targets": 2 }
                ]

            } );

			$(document).on("click",".btnDeleteItem",function(e){
               // console.log($(this).parent());
                tbp.row($(this).parents('tr')).remove().draw(false);
            });
			 
			var counter2 = 1;
            var tbp1 = $('#tblConsecuencia').DataTable( {
                responsive: true,
                autoWidth: false,
                searching: false,
                ordering: false,
                lengthChange: false,
                "paging": false,
                info: true,
                dom:  "<'row'<'col-sm-4'B><'col-sm-4'l><'col-sm-4'f>>" +
                    "<'row'<'col-sm-12'<'table-responsive' tr>>>" +
                    "<'row'<'col-sm-5'i><'col-sm-7'p>>",
                buttons: [
                    {
                        text: 'Agregar',
                        className: 'btn btn-block btn-default btn-xs',
                        action: function ( e, dt, node, config ) {
                            dt.row.add( [
                               
                                '<div class="form-group" style="display: flex;">' +
                                '<select id="cboConsecuencia" class="form-control " data-error="*" name="consecuencias[][tipoConsecuenciaId]" required>' +
                                '<option value="">Seleccione</option>' +
                                '<option value="1">Legal</option>' +
								'<option value="2">Económico</option>' +
								'<option value="3">Reputacional</option>' +
								'<option value="4">Operacional</option>' +
                                '</select><div class="help-block with-errors"></div>' +
                                '</div>',
								'<div class="form-group" style="display: flex;"><input type="text" style="width:100%"  data-error="*" class="form-control " name="consecuencias[][descripcion]"  required><div class="help-block with-errors"></div></div>',
                                '<a class="btn btn-danger btn-sm btnDeleteItem1"><span class="glyphicon glyphicon-trash"></span></a>'
                            ] ).draw( false );
                            console.log(counter2);
                            counter2++;

                            $("#formSaveRiesgo").validator("update");
                            $("#msjtblConsecuencia").text("");
                        }
                    }
                ],
                "columnDefs": [
                    { "width": "15%", "targets": 0 },
                    { "width": "80%", "targets": 1 },
                    { "width": "5%", "targets": 2 }
                ]

            } );
			
            $(document).on("click",".btnDeleteItem1",function(e){
                //console.log($(this).parent());
                tbp1.row($(this).parents('tr')).remove().draw(false);
            });
			
			if(btn.attr("id")==="btnViewRiesgo"){
				$("#formViewRiesgo :input").prop("disabled", true);
				$('#btnClose').removeAttr("disabled");
			}
        });

    });

    $(document).on("submit","#formSaveRiesgo",function(e){

        if (e.isDefaultPrevented()) {
            return false;
        }

        frm=$(this);
        e.preventDefault();
        var msj=$("#msjmntRiesgo");
        msj.removeAttr('class');
        msj.html("");

        //$("#popup").modal("show");
        console.log("formRiesgo");

        $.ajax({
            type: "POST",
            url: baseurl+"riesgos/add",
            contentType: 'application/json',
            data:JSON.stringify(frm.serializeJSON()),
            success: function(result){
                //  alert(result);
                //  chatWith('9','name');
                if(result==1){
                    alerts(0,msj,"El Riesgo se grabo con exito");
                    loadDataTable("#tbRiesgos");
                    frm.trigger('reset');
		           
					$("#listCausas,#listprocesos,#listConsecuencias").val();
		            $("#listCausas,#listprocesos,#listConsecuencias").trigger('change');
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

	  $(document).on("submit","#formEditRiesgo",function(e){

        if (e.isDefaultPrevented()) {
            return false;
        }

        frm=$(this);
        e.preventDefault();
        var msj=$("#msjmntRiesgo");
        msj.removeAttr('class');
        msj.html("");

        //$("#popup").modal("show");
        console.log("formEditRiesgo");

        $.ajax({
            type: "PUT",
            url: baseurl+"riesgos/edit",
            contentType: 'application/json',
            data:JSON.stringify(frm.serializeJSON()),
            success: function(result){
                if(result==1){
                    alerts(0,msj,"El plan se edito con exito");
                    loadDataTable("#tbRiesgos");
                }else{
                    alerts(2,msj,"Ocurrio un problema al editar el riesgo");
                }
            },
            error: function(err) {
                console.log(err);
                alerts(3,msj,"A ocurrido un error interno !!!");
            }
        });
    });

	//btnElimina
    $(document).on("click","#btnDeleteRiesgo",function(e){

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
                    url: baseurl+"riesgos/delete/"+id,
                    type: 'DELETE',
                    success: function (result) {
                        if(result==1){
                            loadDataTable("#tbRiesgos");
                            ezBSAlert({ headerText:"success", messageText: "El Riesgo se elimino con exito", alertType: "success"});
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