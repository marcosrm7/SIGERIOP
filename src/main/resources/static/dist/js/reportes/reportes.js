jQuery(document).ready( function () {
	
	console.log("reporte.js");
	$("#cboResponsable,#cboProceso,#cboRiesgo").select2({})
	
    var dataTablep = $('#tbReportes').DataTable({
        processing: true,
        serverSide: false,
        responsive: true,
        autoWidth: false,
        order: [],
        ajax:{
            url :baseurl+"reportes/paginate", // json datasource
            type: "post",  // method  , by default get
            complete: function(){
                table=$('#tbReportes');
            },
            error: function(xhr, textStatus, errorThrown){  // error handling

                $("#tbReportes_wrapper").html("");
                $("#tbReportes_wrapper").append('<div class="alert alert-danger alert-dismissable"><tr><th colspan="3">No data found in the server</th></tr></div>');
            }
        },
        "aoColumns": [
            { "mData": "riesgoNombre" },
            { "mData": "impactoRiesgo"},
            { "mData": "frecuenciaRiesgo"},
//			{ "mData": "nivelRiesgo"},
			{ "mData":null,
                "sClass": "text-center",
                "mRender": function(data, type, full) {
                    return (data.nivelRiesgo=="bajo")? '<span class=\"label label-danger\">'+data.nivelRiesgo+'</span>':(data.nivelRiesgo=="medio")?'<span class=\"label label-primary\">'+data.nivelRiesgo+'</span>':'<span class="label label-success">'+data.nivelRiesgo+'</span>';
                }
            },
			{ "mData": "procersoNombre"},
			{ "mData": "controlNombre"},
//			{ "mData": "resultadoControl"},
			{ "mData":null,
                "sClass": "text-center",
                "mRender": function(data, type, full) {
                    return (data.resultadoControl=="bajo")? '<span class=\"label label-danger\">'+data.resultadoControl+'</span>':(data.resultadoControl=="medio")?'<span class=\"label label-primary\">'+data.resultadoControl+'</span>':'<span class="label label-success">'+data.resultadoControl+'</span>';
                }
            },
			{ "mData": "planAccionNombre"},
			{ "mData": "eventoNombre"},
            { "mData":null,
                "bSortable": false,
                "sClass": "text-center",
                "mRender": function(data, type, full) {

                    buttons='<a target="_blank" '+
                        'type="button" id="btnViewRiesgo" class="btn btn-success btn-xs" '+
                        'href="' + baseurl +'reportes/pdf/'+data.riesgoId+'/'+data.procesoId+'/'+data.controlId+'/'+data.planAccionId+'/'+data.eventoId+'" title="Ver" ><i class="fa fa-download"></i></a> ';
                    return buttons;
                }
            }

        ]
    });

   $(document).on("submit","#formSearh",function(e){

        if (e.isDefaultPrevented()) {
            return false;
        }

        frm=$(this);
        e.preventDefault();
        console.log("formSearh");
		$('#tbReportes').DataTable().clear().draw();
        $.ajax({
            type: "POST",
            url: baseurl+"reportes/search",
            contentType: 'application/json',
            data:JSON.stringify(frm.serializeJSON()),
            success: function(result){
				$('#tbReportes').DataTable().rows.add(result).draw();
	            frm.trigger('reset');
               
            },
            error: function(err) {
                console.log(err);
            }
        });
    });

	 $(document).on("click","#btnLimpiar",function(e){
       $("#cboResponsable,#cboProceso,#cboRiesgo").val(0);
	   $("#cboResponsable,#cboProceso,#cboRiesgo").select2({})
       loadDataTable("#tbReportes");
    });
	
});