jQuery(document).ready( function () {
    //$idmnus=$("#opmnu").val().split(":");
    console.log("planes.js");
    var dataTablep = $('#tbplanes').DataTable({
        processing: true,
        serverSide: false,
        responsive: true,
        autoWidth: false,
        order: [],
        ajax:{
            url :baseurl+"planes/paginate", // json datasource
            type: "post",  // method  , by default get
            complete: function(){
                table=$('#tbplanes');
            },
            error: function(xhr, textStatus, errorThrown){  // error handling
                $("#tbplans_wrapper").html("");
                $("#tbplans_wrapper").append('<div class="alert alert-danger alert-dismissable"><tr><th colspan="3">No data found in the server</th></tr></div>');
            }
        },
        "aoColumns": [
            { "mData": "id" },
            { "mData": "nombre"},
            { "mData": "fechaInicio"},
            { "mData": "fechaFin"},
            { "mData": "responsable"},
            { "mData":null,
                "sClass": "text-center",
                "mRender": function(data, type, full) {
                     if (data.estadoId==16)
                         return '<span class=\"label label-success\">'+data.estado+'</span>';
                    if (data.estadoId==17)
                        return '<span class="label label-info">'+data.estado+'</span>';
                    if (data.estadoId==18)
                        return '<span class="label label-primary">'+data.estado+'</span>';
                }
            },
            { "mData":null,
                "bSortable": false,
                "sClass": "text-center",
                "mRender": function(data, type, full) {

                    buttons='<button data-toggle="modal" data-target="#myModalViewPlan" data-remote="false" '+
                        'type="button" data-id="'+data.id+'" id="btnEditPlan" class="btn btn-success btn-xs" '+
                        'href="' + baseurl +'planes/view/edit/'+data.id+'" title="Edit" ><i class="fa fa-edit"></i></button> ';
                    buttons+='<button data-toggle="modal" data-target="#myModalViewPlan" data-remote="false" '+
                        'type="button" data-id="'+data.id+'" id="btnViewPlan" class="btn btn-info btn-xs" '+
                        'href="' + baseurl +'planes/view/detail/'+data.id+'" title="Ver" ><i class="fa fa-eye"></i></button> ';
					buttons+='<button type="button" data-id="'+data.id+'" id="btnDeletePlan" class="btn btn-danger btn-xs" title="Eliminar"><i class="fa fa-trash"></i></button>';                   
					return buttons;
                }
            }

        ]
    });


    $('#myModalViewPlan').on('show.bs.modal', function (e) {

        console.log("myModalViewPlan");

        var btn = $(e.relatedTarget);
        //var idplan=btn.data('id');
        var data=null;
        var title="Nuevo plan";
        var frm='#formSavePlan';

        if(btn.attr("id")==="btnEditPlan"){
            console.log("btnEditPlan");
            //data={"idplan":idplan};
            title="Modificar plan";
            frm='#formEditPlan';
        }
		
		if(btn.attr("id")==="btnViewPlan"){
            console.log("btnViewPlan");
            title="Detalle del Plan de Acción";
            frm='#formViewPlan';
        }

        $.get(btn.attr("href"), function( data ) {
            $('#modal-title').html(title);
            $('#modal-body').html(data);
            $('#fechaInicio,#fechaFin').datetimepicker({
                format: 'DD/MM/YYYY'
            });
            //$("#txtmontoPerdida").inputmask('Regex', {regex: "^[0-9]{1,6}(\\.\\d{1,2})?$"});
            $(document).on("input", "#txtmontoPerdida", function (e) {
                this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');
            });

            if(btn.attr("id")==="btnEditPlan" || btn.attr("id")==="btnViewPlan") {
                idsRiesgo = JSON.parse($('#listRiesgos').attr("data-ids"));
                console.log(idsRiesgo)
                $('#listRiesgos').val(idsRiesgo);
            }
			
            $("#listRiesgos").select2();
            /************************************/
            var counter = 1;
            var tbp = $('#tblActividades').DataTable( {
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
						id:'btnAgregar',
                        text: 'Agregar',
                        className: 'btn btn-block btn-default btn-xs btnAgregar',
                        action: function ( e, dt, node, config ) {
                            // alert( 'Button activated' );

                            dt.row.add( [
                                //'<input type="text" style="width:100%" class="form-control input-sm" name="guiaDet[][fld_desart]" required >',
                                '<div class="form-group" style="display: flex;"><input type="text" style="width:100%"  data-error="*" class="form-control input-sm" name="actividades[][descripcion]"  required><div class="help-block with-errors"></div></div>',
                                '<div class="form-group" style="display: flex;">' +
                                '<select id="cboEstado" class="form-control" data-error="*" name="actividades[][estadoId]" required>' +
                                '<option value="">Seleccione</option>' +
                                '<option value="19">No finalizado</option>' +
                                '<option value="20">Finalizado</option>' +
                                '</select><div class="help-block with-errors"></div>' +
                                '</div>',
                                '<a class="btn btn-danger btn-sm btnDeleteItem"><span class="glyphicon glyphicon-trash"></span></a>'
                                //'<button class="btn btn-danger btn-sm btnDeleteItem" ><span class="glyphicon glyphicon-trash"></span></button>'
                            ] ).draw( false );
                            console.log(counter);
                            counter++;

//	        	        $("#demo").rules("add", {
//	        	            required: true
//	        	        });
                            //$("#form1").validator("update")

                            $("#formSavePlan").validator("update");
                            $("#msjtblActividades").text("");
                            //$("input[name*='fld_cantart_dec']")
                            $('[data-type="numerico"]').on('input', function() {
                                this.value = this.value
                                    .replace(/[^\d.]/g, '')             // numbers and decimals only
                                    // .replace(/(^[\d]{2})[\d]/g, '$1')   // not more than 2 digits at the beginning
                                    .replace(/(\..*)\./g, '$1')         // decimal can't exist more than once
                                    .replace(/(\.[\d]{4})./g, '$1');    // not more than 4 digits after decimal
                            });
                        }
                    }
                ],
                "columnDefs": [
                    { "width": "80%", "targets": 0 },
                    { "width": "15%", "targets": 1 },
                    { "width": "5%", "targets": 2 }
                ]

            } );
            $(document).on("click",".btnDeleteItem",function(e){
                console.log($(this).parent());
                tbp.row($(this).parents('tr')).remove().draw(false);
                //sumaColumnas('input[name^="guiaDet[][fld_cantart_dec]"]',"#lblTotalCantDec");
                //sumaColumnas('input[name^="guiaDet[][fld_cantart]"]',"#lblTotalCant");
                //sumaColumnas('input[name^="guiaDet[][fld_pesoart_dec]"]',"#lblTotalPesoDes");
                //sumaColumnas('input[name^="guiaDet[][fld_pesoart]"]',"#lblTotalPeso");
                //calculateTotalTransfer();

            });
			
			if(btn.attr("id")==="btnViewPlan"){
				$("#formViewPlan :input").prop("disabled", true);
				$('#btnClose').removeAttr("disabled");
			}
            /************************************/
            $(frm).validator();
        });
    });


    $(document).on( "change", "#cboTiposplan", function(e) {
        console.log("cboGrado");
        //if(ValidaSession()){ return; }
        var tipoplanId = $(this).val();
        if(tipoplanId){

            $.ajax({
                type:"GET",
                url:baseurl+"planes/tipos/"+tipoplanId+"/categoria",
                //data:({gradoId: gradoId}),
                success:function(dataJson){
                    console.log(dataJson.length);
                    if(dataJson.length > 0){
                        $('#cboCategoriasplan').html('<option value="">Seleccione</option>');
                        option="<option value=''>Seleccione</option>";
                        dataJson.forEach(function (item) {
                            option+="<option value='"+item.id+"'>"+item.nombre+"</option>";
                        });
                    }else{
                        option="<option value=''>Año electivo no disponible</option>";
                    }
                    $('#cboCategoriasplan').html(option);
                }
            });
        }else{
            $('#cboAnioElec').html('<option value="">Seleccione</option>');
            //$('#cboCity').html('<option value="">Select state first</option>');
        }
    });

    $(document).on("submit","#formSavePlan",function(e){

        if (e.isDefaultPrevented()) {
            return false;
        }

        frm=$(this);
        e.preventDefault();
        var msj=$("#msjformplan");
        msj.removeAttr('class');
        msj.html("");

        //$("#popup").modal("show");
        console.log("formSavePlan");

        $.ajax({
            type: "POST",
            url: baseurl+"planes/add",
            contentType: 'application/json',
            data:JSON.stringify(frm.serializeJSON()),
            success: function(result){
                //  alert(result);
                //  chatWith('9','name');
                if(result==1){
                    alerts(0,msj,"El plan se grabo con exito");
                    frm.trigger('reset');
                    $('#tblActividades').DataTable().clear().draw();
                    $("#listRiesgos").val();
                    $("#listRiesgos").trigger('change');
                    loadDataTable("#tbplanes");
                }else{
                    alerts(2,msj,"El plan ya existe");
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

    $(document).on("submit","#formEditPlan",function(e){

        if (e.isDefaultPrevented()) {
            return false;
        }

        frm=$(this);
        e.preventDefault();
        var msj=$("#msjformplan");
        msj.removeAttr('class');
        msj.html("");

        //$("#popup").modal("show");
        console.log("formSavePlan");

        $.ajax({
            type: "PUT",
            url: baseurl+"planes/edit",
            contentType: 'application/json',
            data:JSON.stringify(frm.serializeJSON()),
            success: function(result){
                //  alert(result);
                //  chatWith('9','name');
                if(result==1){
                    alerts(0,msj,"El plan se edito con exito");
                    //frm.trigger('reset');
                    //$('#tblActividades').DataTable().clear().draw();
                    //$("#listRiesgos").val();
                    //$("#listRiesgos").trigger('change');
                    loadDataTable("#tbplanes");
                }else{
                    alerts(2,msj,"El plan ya existe");
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
	
	//btnElimina
    $(document).on("click","#btnDeletePlan",function(e){

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
                    url: baseurl+"planes/delete/"+id,
                    type: 'DELETE',
                    success: function (result) {
                        if(result==1){
                            loadDataTable("#tbplanes");
                            ezBSAlert({ headerText:"success", messageText: "El plan se elimino con exito", alertType: "success"});
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