jQuery(document).ready( function () {
	console.log("reports ------.js");
	
	
	$('#cboTypeRphard').on('change', function() {
		$("#divReportHardware,#divReportHardwareStock").hide();
		dataTableRepo.clear().draw();
		dataTableRepoStock.clear().draw();
	 })
	
	// $("#formReportHardware").validator();
	
	$(document).on("submit","#formReportHardware",function(e){  
		//e.preventDefault();
//		if (e.isDefaultPrevented()) {
//            return false;
//        }
		e.preventDefault();
		
		frm=$(this)
		console.log($(this));
		console.log($(e.target));
		console.log(e.target.getAttribute('action'));
		
		var typeRphard=$("#cboTypeRphard").val()
		console.log(typeRphard);
		
		$("#divReportHardware,#divReportHardwareStock").hide();
		dataTableRepo.clear().draw();
		dataTableRepoStock.clear().draw();
		$("#msjReportHardware").html("");
		
		if(typeRphard==1){
	       reporteHardware();
		}else if(typeRphard==2){
			
	       reporteHardwareStock();
		}
		
	});
	
	reporteHardware=function(){
		$.ajax({
		    type: 'get',
		    url: baseurl+'reports/hardware/reportHardware',
		    //data: frm.serializeJSON(), 
		}).done(function (dataJson) {
		    console.log('SUCCESS');
		   // var dataJson = $.parseJSON(data);
   		 	//console.log(dataJson.data);
   		 	
   		 	//dataTableRepo.clear().draw();
   		 	//alert("table");
   		 	dataTableRepo.rows.add(dataJson.data).draw();
   		 	$("#divReportHardware").show();
		}).fail(function (msg) {
		    console.log('FAIL');
		    alerts(3,$("#msjReportHardware"),"A ocurrido un error interno !!!");
		}).always(function (msg) {
		    console.log('ALWAYS');
		});
	};
	//<div class="table-responsive">
	const titlebtn1='Report Hardware';	
	dataTableRepo = $('#tbReportHardware').DataTable({
		dom:  "<'row'<'col-sm-4'l><'col-sm-4'B><'col-sm-4'f>>" +
        "<'row'<'col-sm-12'<'table-responsive' tr>>>" +
        "<'row'<'col-sm-5'i><'col-sm-7'p>>",
        buttons: [
        	{	
            	extend: 'copyHtml5',
            	title: titlebtn1,
                exportOptions: {
                    columns: [ 0, 1, 2,3,4 ]
                }
            },
            {	
            	extend: 'excelHtml5',
            	title: titlebtn1,
                exportOptions: {
                    columns: [ 0, 1, 2,3,4 ]
                }
            },
            {	
            	extend: 'csvHtml5',
            	title: titlebtn1,
                exportOptions: {
                    columns: [ 0, 1, 2,3,4 ]
                }
            },
            {	
            	extend: 'pdfHtml5',
            	title: titlebtn1,
            	download: 'open',
                exportOptions: {
                    columns: [ 0, 1, 2,3,4 ]
                }
            },
            {	
            	extend: 'print',
            	title: titlebtn1,
                exportOptions: {
                    columns: [ 0, 1, 2,3,4 ]
                }
            }
        ],
    	processing: true,
    	bJQueryUI: true,
        //"serverSide": true,
        responsive: true,
        autoWidth: false,
        ordering:false,
        data:[],
        "aoColumns": [
        { "mData": "name_hardware"},
        { "mData": "serial" },
        { "mData": "model" },
        { "mData": "registration_date" },
        { "mData": "name_owner" },
        { "mData":null,
            "bSortable": false,
            "sClass": "text-center",
            "mRender": function(data, type, full) {
            	return '<button data-toggle="modal" data-target="#myModalViewPdfHardware" data-remote="false" type="button" data-id="'+data.id_hardware+'" id="btnPrintHardware" class="btn btn-default btn-xs" title="Print"><i class="fa fa-file-pdf-o"></i></button>';
            	//return '<button data-toggle="modal" data-target="#myModalViewReport" data-remote="false" type="button" data-id="'+data.id_hardware+'"  id="btnViewReportVisit" class="btn btn-success btn-xs" href="'+baseurl+'/visit/consult/ActConsultVisitor" ><i class="fa  fa-list-alt"></i></button>';
            }
        }
        ] 
    });
	
	dataTableRepo.buttons().container().appendTo( '#tbReportHardware_wrapper .col-sm-6:eq(0)' );
	
	reporteHardwareStock=function(){
		$.ajax({
		    type: 'get',
		    url: baseurl+'reports/hardware/reportHardwareStock',
		    //data: frm.serializeJSON(), 
		}).done(function (dataJson) {
		    console.log('SUCCESS');
		   // var dataJson = $.parseJSON(data);
   		 	//console.log(dataJson.data);
   		 	
   		 	//dataTableRepoStock.clear().draw();
   		 	//alert("table");
   		 	//console.log("limpia")
   		 	dataTableRepoStock.rows.add(dataJson.data).draw();
   		 	$("#divReportHardwareStock").show();
		}).fail(function (msg) {
		    console.log('FAIL');
		    alerts(3,$("#msjReportHardware"),"A ocurrido un error interno !!!");
		}).always(function (msg) {
		    console.log('ALWAYS');
		});
	}
	
	const titlebtn='Report hardware stock';
	dataTableRepoStock = $('#tbReportHardwareStock').DataTable({
		dom:  "<'row'<'col-sm-4'l><'col-sm-4'B><'col-sm-4'f>>" +
        "<'row'<'col-sm-12'<'table-responsive' tr>>>" +
        "<'row'<'col-sm-5'i><'col-sm-7'p>>",
        buttons: [
        	{	
            	extend: 'copyHtml5',
            	title: titlebtn
            },
            {	
            	extend: 'excelHtml5',
            	title: titlebtn
            },
            {	
            	extend: 'csvHtml5',
            	title: titlebtn
            },
            {	
            	extend: 'pdfHtml5',
            	title: titlebtn,
            	download: 'open'
            },
            {	
            	extend: 'print',
            	title: titlebtn
            }
        ],
    	processing: true,
    	bJQueryUI: true,
        //"serverSide": true,
        responsive: true,
        autoWidth: false,
        ordering:true,
        data:[],
        "aoColumns": [
        { "mData": "name_hardware"},
        { "mData": "model" },
        { "mData": "total" },
        { "mData": "assigned" },
        //{ "mData": "available" },
        { "mData":null,
            "bSortable": true,
            "sClass": "text-center",
            "mRender": function(data, type, full) {
            	//return '<button data-toggle="modal" data-target="#myModalViewPdfHardware" data-remote="false" type="button" data-id="'+data.id_hardware+'" id="btnPrintHardware" class="btn btn-default btn-xs" title="Print"><i class="fa fa-file-pdf-o"></i></button>';
            	return '<span class="badge '+((data.assigned > 0)? "bg-green" : "bg-red")+'">'+data.assigned+'</span>';
            }
        }
        
        //
        ] 
    });
	
	dataTableRepoStock.buttons().container().appendTo( '#tbReportHardwareStock_wrapper .col-sm-6:eq(0)' );
	
	$('#myModalViewPdfHardware').on('show.bs.modal', function (e) { 

        var btn = $(e.relatedTarget);
        var id_hardware=btn.data('id');
        
        //var title="Report Hardware";
        $('#modal-title-pdf').html("Report Hardware");
        $('#modal-body-pdf').html("");
        
        blockUI();
        //$("#teditperfil").msDropdown().data("dd");
      // alert("open");
        $('<iframe  />', {
            name: 'myframe',
            id:   'myframe',
            width:'100%',
            height:'100%', 
            src: baseurl+'hardware/registration/generatePdfHardware/'+id_hardware,
            //src:'http://mmc.geofisica.unam.mx/cursos/mcst-2007-II/Java/Curso_de_Java_Basico.pdf',
            onload : $.unblockUI()
        }).appendTo('#modal-body-pdf');
    });
	
	
	const titlebtn3='Employee Hardware Report';
	var dataTableReportHardwareEmployee = $('#tbReportHardwareEmployee').DataTable({
		dom:  "<'row'<'col-sm-4'l><'col-sm-4'B><'col-sm-4'f>>" +
        "<'row'<'col-sm-12'<'table-responsive' tr>>>" +
        "<'row'<'col-sm-5'i><'col-sm-7'p>>",
        buttons: [
        	{	
            	extend: 'copyHtml5',
            	title: titlebtn3,
                exportOptions: {
                    columns: [ 0, 1, 2]
                }
            },
            {	
            	extend: 'excelHtml5',
            	title: titlebtn3,
                exportOptions: {
                    columns: [ 0, 1, 2 ]
                }
            },
            {	
            	extend: 'csvHtml5',
            	title: titlebtn3,
                exportOptions: {
                    columns: [ 0, 1, 2 ]
                }
            },
            {	
            	extend: 'pdfHtml5',
            	title: titlebtn3,
            	download: 'open',
                exportOptions: {
                    columns: [ 0, 1, 2]
                }
            },
            {	
            	extend: 'print',
            	title: titlebtn3,
                exportOptions: {
                    columns: [ 0, 1, 2]
                }
            }
        ],
	       processing: true,
	       //serverSide: true,
	       responsive: true,
	       autoWidth: false,
	       ajax:{
	           url :baseurl+"reports/assignmentEmployee/reportAssignmentEmployee", // json datasource
	           type: "post",  // method  , by default get
	           complete: function(){
	           },
	           error: function(){  // error handling
	           	console.log("error");
	               $("#tbReportHardwareEmployee").html("");
	               $("#tbReportHardwareEmployee").append('<div class="alert alert-danger alert-dismissable"><tr><th colspan="3">No data found in the server</th></tr></div>');

	           }
	       },
	       "aoColumns": [
	       { "mData": "fullnames" },	   
	       { "mData": "amounthardware" },
	       { "mData": "amountaccessory" },
	       { "mData":null,
	            "bSortable": false,
	            "sClass": "text-center",
	            "mRender": function(data, type, full) {
	            	return '<button data-toggle="modal" data-target="#myModalViewPdfHardwareEmployee" data-remote="false" type="button" data-id="'+data.id_employees+'" class="btn btn-default btn-xs" title="Print"><i class="fa fa-file-pdf-o"></i></button>';
	            	//return '<button data-toggle="modal" data-target="#myModalViewReport" data-remote="false" type="button" data-id="'+data.id_hardware+'"  id="btnViewReportVisit" class="btn btn-success btn-xs" href="'+baseurl+'/visit/consult/ActConsultVisitor" ><i class="fa  fa-list-alt"></i></button>';
	            }
	        }   
	   ] 
	});
	
	dataTableReportHardwareEmployee.buttons().container().appendTo( '#tbReportHardwareEmployee_wrapper .col-sm-6:eq(0)' );

	$('#myModalViewPdfHardwareEmployee').on('show.bs.modal', function (e) { 

        var btn = $(e.relatedTarget);
        var id_employees=btn.data('id');
        
        //var title="Report Hardware";
        $('#modal-title-pdf-2').html("Employee Hardware Report");
        $('#modal-body-pdf-2').html("");
        
        blockUI();
        //$("#teditperfil").msDropdown().data("dd");
      // alert("open");
        $('<iframe  />', {
            name: 'myframe',
            id:   'myframe',
            width:'100%',
            height:'100%', 
            src: baseurl+'reports/assignmentEmployee/reportPdfAssignmentEmployee/'+id_employees,
            //src:'http://mmc.geofisica.unam.mx/cursos/mcst-2007-II/Java/Curso_de_Java_Basico.pdf',
            onload : $.unblockUI()
        }).appendTo('#modal-body-pdf-2');
    });
});
	