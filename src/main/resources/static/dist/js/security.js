jQuery(document).ready( function () {
    //$idmnus=$("#opmnu").val().split(":");
   console.log("security.js")
   
    var dataTable = $('#tbusuarioxxxx').DataTable({
            processing: true,
            //serverSide: true,
            responsive: true,
            autoWidth: false,
            ajax:{
                url :baseurl+"security/user/getListUsersJson", // json datasource
                //type: "post",  // method  , by default get
                complete: function(){
                 //  table=$('#tbusuario');
                  //alert(dataTable);
                  //console.log(table.parent());
                 // table.parent().addClass("table-responsive");
                 // table.parent().addClass('table-responsive');//.removeClass('col-sm-12');
                },
                error: function(){  // error handling
                	console.log("errorr");
//                    $(".tbusuario-grid-error").html("");
//                    $("#tbusuario-grid").append('<tbody class="tbusuario-grid-error"><tr><th colspan="3">No data found in the server</th></tr></tbody>');
//                    $("#tbusuario-grid_processing").css("display","none");
                    $("#tbusuario_wrapper").html("");
                    $("#tbusuario_wrapper").append('<div class="alert alert-danger alert-dismissable"><tr><th colspan="3">No data found in the server</th></tr></div>');
 
                }
            },
            "aoColumns": [
            { "mData": "username" },
            { "mData":null,
              "bSortable": false, 
              "sClass": "text-center",
	          "mRender": function(data, type, full) {
	            	return data.profile.name_profile;
	            }
	        },
	        { "mData": "registration_date" },
	        { "mData":null,
	              "bSortable": false, 
	              "sClass": "text-center",
		          "mRender": function(data, type, full) {
		            	return (data.state==1)?'<span class="label label-success">A</span>' : "<span class=\"label label-danger\">D</span>";
		            }
		    },
	        { "mData":null,
	              "bSortable": false, 
	              "sClass": "text-center",
		          "mRender": function(data, type, full) {
		        	  	
		        	  buttons='<button data-toggle="modal" data-target="#myModalViewUser" data-remote="false" '+ 
		        		      'type="button" data-id="'+data.id_user+'" id="btnEditUser" class="btn btn-info btn-xs" '+ 
		        		      'href="' + baseurl +'security/user/view/edit/'+data.id_user+'" title="Edit" ><i class="fa fa-edit"></i></button> ';
		        	  buttons+='<button type="button" data-id="'+data.id_user+'" id="btnDeleteUser" class="btn btn-danger btn-xs" title="Delete"><i class="fa fa-trash"></i></button>';
		        	  return buttons;
		            }
		        }
            
        ] 
    });


    var dataTable = $('#tbUsuario').DataTable({
        oLanguage : { "sSearch": '<a class="btn btn-sm searchBtn" id="searchBtn"><i class="fa fa-trash"></i></a>' },
        processing: true,
        serverSide: true,
        // processing: true,
        pageLength: 10,
        //ordering:false,
        order: [[ 0, "asc" ]],
        responsive: true,
        autoWidth: false,
        ajax:{
            url :baseurl+"security/user/lista/paginate",
            type: "post",
            dataType: "json",
            processData: false,
            contentType: 'application/json;charset=UTF-8',
            data:function(data)
            {
                data.searchColumn = $("#columfilter").val();
                return data = JSON.stringify(data);
            },
            complete: function(){
            },
            error: function(){
                console.log("errorr");
                $("#tbUsuario").html("");
                $("#tbUsuario").append('<div class="alert alert-danger alert-dismissable"><tr><th colspan="3">No data found in the server</th></tr></div>');
            }
        },
        "aoColumns": [
            { "mData": "username" , sName: "usuario_nombre" },
            { "mData":null,
                "sName":"perfil_nombre",
                //"bSortable": true,
                "sClass": "text-center",
                "mRender": function(data, type, full) {
                    return data.profile.name_profile;
                }
            },
            { "mData": "registration_date", sName: "usuario_fecha_reg" },
            { "mData":null,
                "bSortable": false,
                "sClass": "text-center",
                "mRender": function(data, type, full) {
                    return (data.state==2)?'<span class="label label-success">A</span>' : "<span class=\"label label-danger\">D</span>";
                }
            },
            { "mData":null,
                "bSortable": false,
                "sClass": "text-center",
                "mRender": function(data, type, full) {

                    buttons='<button data-toggle="modal" data-target="#myModalViewUser" data-remote="false" '+
                        'type="button" data-id="'+data.id_user+'" id="btnEditUser" class="btn btn-info btn-xs" '+
                        'href="' + baseurl +'security/user/view/edit/'+data.id_user+'" title="Edit" ><i class="fa fa-edit"></i></button> ';
                    buttons+='<button type="button" data-id="'+data.id_user+'" id="btnDeleteUser" class="btn btn-danger btn-xs" title="Delete"><i class="fa fa-trash"></i></button>';
                    return buttons;
                }
            }

        ],
      /*  columnDefs:[
            // { targets : 1 ,visible: false  },
            {targets:2, render:function(fecenvio){
                    return moment(fecenvio).format('YYYY-MM-DD HH:mm:ss');
                }},
            {targets:11, render:function(fec_recojo){
                    return moment(fec_recojo).format('YYYY-MM-DD HH:mm:ss');
                }},
            {targets:12, render:function(check_entrega){
                    //console.log(check_entrega)
                    if(check_entrega == "PU"){
                        classColor="label-primary";
                    }else if(check_entrega == "TR"){
                        classColor="label-warning";
                    }else if(check_entrega == "DL"){
                        classColor="label-success";
                    }

                    return (check_entrega==null)? check_entrega :'<span class="label '+classColor+'">'+check_entrega+'</span>';
                }},
            {targets:13, render:function(fec_entrega){
                    return (fec_entrega==null)? fec_entrega : moment(fec_entrega).format('YYYY-MM-DD HH:mm:ss');
                }},
            { targets : 14 ,visible: false  },
            //,{ targets : 15 ,"width": "8%",  }
        ],*/
        initComplete : function() {
            //$('.dataTables_filter').append("\n");
            /*
            var dropdown=`<div class="keep-open btn-group" title="Columns">
						    <button type="button" aria-label="columns" class="btn btn-sm btn-default dropdown-toggle" data-toggle="dropdown"><i class="fa fa-columns"></i> <span class="caret"></span></button>
						    <ul id="dropdown-menu-consulta" class="dropdown-menu" role="menu">
						        <li role="menuitem">
						            <label><input type="checkbox"  value="1" checked="checked"> Para</label>
						        </li>
						        <li role="menuitem">
						            <label><input type="checkbox"  value="14" > Tiempo de entrega</label>
						        </li>
						    </ul>
						</div>`;
            */
            //<a class="btn btn-sm searchBtn" id="searchBtn"><i class="fa fa-trash"></i></a>
            //var cboSearch=`<div class="input-group"><div class="input-group-btn">`+dropdown+`</div>
            var cboSearch=`<div class="input-group">
								  <select id="columfilter" class="form-control input-sm">
								  <option value="a.nombre" >Usuario</option>
								  <option value="b.nombre" >Perfil</option>
								  <div>`;
            $('#tbUsuario_filter').prepend(cboSearch);
            //$('#tbGuiaCliente_filter').prepend(dropdown);
            //$('.dataTables_filter').insertBefore("\n");
        }
    });

    $("#searchBtn").click(function(){
        // $(".second").click();
        //return false;
        console.log("searchBtn reset");
        //loadDataTable("#tbConsultasGuia");
        dataTable.search('').columns().search('').draw();
    })

    $(document).on('click', '#dropdown-menu-consulta', function (e) {
        e.stopPropagation();
        // console.log($(this));
        $("#dropdown-menu-consulta input[type=checkbox]").each(function() {
            //console.log($(this).is(":checked"))
            //console.log($(this).val())
            if($(this).is(":checked")) {
                //console.log($(this));
                dataTable.column( $(this).val() ).visible( true );
                // line += $("+ span", this).text() + ";";
            }else{
                dataTable.column( $(this).val() ).visible( false );
            }

        });
    });

    $('#tbUsuario').dataTable().fnFilterOnReturn();

    
   /* $("#frmrRegistraUsuario").submit(function(event){
    // cancels the form submission
        var msj=$("#msjregusu");
        msj.removeAttr('class');
        msj.html("");
        
        event.preventDefault();
        alert("sssss");
        msj.addClass("callout callout-success");
        msj.html("<p>This is a green callout.</p>");
        //callout callout-success
        //submitForm();
    });*/
    
   /* $("#myModal12").on("show.bs.modal", function(e) {
        //alert("ssssss");
        var link = $(e.relatedTarget);
        $(this).find(".modal-body").load(link.attr("href"));
        alert("aaaa");
        $('#frmrRegistraUsuario').validator();
        //$('#frmrRegistraUsuario').formValidation('resetForm', true);
        
    });*/
   /*
    $('#myModal').on('show.bs.modal', function (e) { 
        var link = $(e.relatedTarget);
       // alert(link.attr("href"));
        $.get(link.attr("href"), function( data ) { 
            $('.modal-body').html(data); 
            $('#frmrRegistraUsuario').validator();
            //do somethings that i want 
            
        }); 
    
    });*/
    
    $('#myModalViewUser').on('show.bs.modal', function (e) { 
        
        console.log("myModalViewUser");
        
        var btn = $(e.relatedTarget);
        //var idUser=btn.data('id');
        var data=null;
        var title="Nuevo usuario";
        var frm='#formSaveUser';
       
        if(btn.attr("id")==="btnEditUser"){
            console.log("btnEditUser");
            //data={"idUser":idUser};
            title="Modificar usuario";
            frm='#formEditUser';
        }
        $.get(btn.attr("href"), function( data ) {
            $('#modal-title').html(title);
            $('#modal-body').html(data);
            $(frm).validator();
        });
    });
    
   $(document).on("submit","#formSaveUser",function(e){    
      
        if (e.isDefaultPrevented()) {
            return false;
        }
       
       frm=$(this);
       e.preventDefault();
       var msj=$("#msjregusu");
           msj.removeAttr('class');
           msj.html("");
           
       //$("#popup").modal("show"); 
           console.log("formSaveUser");
       
       $.ajax({
            type: "POST",
            url: baseurl+"security/user/saveUser",
            contentType: 'application/json',
            data:JSON.stringify(frm.serializeJSON()),
            success: function(result){
              //  alert(result);
            //  chatWith('9','name');
                if(result==1){
                    alerts(0,msj,"El usuario se grabo con exito");   
                    loadDataTable("#tbUsuario");
                    frm.trigger('reset');
                }else{
                    alerts(2,msj,"El usuario ya existe");
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
    
   //btnEditUser
   $(document).on("submit","#formEditUser",function(e){    
     
       if (e.isDefaultPrevented()) {
           return false;
       }
      e.preventDefault();
      
      frm=$(this);
      
      var msj=$("#msjregusu");
          msj.removeAttr('class');
          msj.html("");
          
       
       console.log(":::formEditUser");
       //return;
      $.ajax({
           type: "PUT",
           url: baseurl+"security/user/editUser",
           contentType: 'application/json',
           data:JSON.stringify(frm.serializeJSON()),
           success: function(result){
             //  alert(result);
           //  chatWith('9','name');
               if(result==1){
                   alerts(0,msj,"El usuario se modifico con exito");   
                   loadDataTable("#tbusuario");
                   //frm.trigger('reset');
               }else{
                   alerts(2,msj,"No se completo el proceso.. !!!");
               }
               
               
           },
           error: function() {
               //estableceAlerta("#msj_urs","errors","A ocurrido un error interno !!!");
               
               alerts(3,msj,"A ocurrido un error interno !!!");
           } 
       });
     //  alert("ajax");
   });
   
   //btnEliminaUsuario
   $(document).on("click","#btnDeleteUser",function(e){    

       var obj = this;
       ezBSAlert({
       type: "confirm",
       headerText:"Confirm",
       messageText: "Esta seguro de esto?",
       alertType: "warning"
       }).done(function (e) {
         var idUser = $(obj).data('id');
         //console.log("confirma::"+idProduct);
         //var url =baseurl+"/usuarios/ActEliminarUsuario";
         if(e){
             $.ajax({
               url: baseurl+"security/user/delete/"+idUser,
               type: 'DELETE',
               //data: { idUser:idUser},
               //contentType: 'application/json; charset=utf-8',
               success: function (result) {
                   if(result==1){
                       //alerts(0,msj,"");   
                       loadDataTable("#tbUsuario");
                       ezBSAlert({ headerText:"success", messageText: "El usuario se elmino con exito", alertType: "success"});
                   }else{
                      // alerts(2,msj,"No se completo el proceso.. !!!");
                       ezBSAlert({ headerText:"Error",messageText: "No se completo el proceso.. !!!", alertType: "danger"});
                   }


               },
               error: function () {
                   //alerts(3,msj,"A ocurrido un error interno !!!");
                   ezBSAlert({ headerText:"Error",messageText: "A ocurrido un error interno !!!", alertType: "danger"});
               }
             });
           
         }
       });
  });
   
   $(document).on("submit","#formConfigNewPass",function(e){    
	     
       if (e.isDefaultPrevented()) {
           return false;
       }
      e.preventDefault();
      
      frm=$(this);
      
      var msj=$("#msjConfNewPass");
          msj.removeAttr('class');
          msj.html("");
          
       
       console.log(":::formEditUser");
       //return;
      $.ajax({
           type: "PUT",
           url: baseurl+"security/setting/setNewPassword",
           contentType: 'application/json',
           data:JSON.stringify(frm.serializeJSON()),
           success: function(result){
             //  alert(result);
           //  chatWith('9','name');
               if(result==1){
                   //alerts(0,msj,"El usuario se modifico con exito");
                   console.log("El usuario se modifico con exito");
                   window.location.href = baseurl;
                  // loadDataTable("#tbusuario");
                   //frm.trigger('reset');
               }else if(result==2){
            	   alerts(2,msj,"La confirmación de la contraseña no coincide");
               }else if(result==3){
            	   alerts(2,msj,"La contraseña actual no es valida");	   
               }else{
                   alerts(2,msj,"No se completo el proceso.. !!!");
               }
               
               
           },
           error: function() {
               //estableceAlerta("#msj_urs","errors","A ocurrido un error interno !!!");
               
               alerts(3,msj,"A ocurrido un error interno !!!");
           } 
       });
     //  alert("ajax");
   });
    
   /************************************** profile ***********************************************/
    //$.fn.DataTable.ext.errMode = 'throw';
   //$.fn.dataTable.ext.errMode = 'none';
/*
$('#tbperfil')
    .on( 'error.dt', function ( e, settings, techNote, message ) {
        console.log( 'An error has been reported by DataTables: ', message );
    } )
    .DataTable();*/
    
    var dataTablep = $('#tbProfile').DataTable({
           processing: true,
            //"serverSide": true,
            responsive: true,
            autoWidth: false,
            ajax:{
                url :baseurl+"security/profile/paginate", // json datasource
                type: "post",  // method  , by default get
                complete: function(){
                   table=$('#tbProfile');
                  //alert(dataTable);
                  //console.log(table.parent());
                  //table.parent().addClass("table-responsive");
                 // table.parent().addClass('table-responsive');//.removeClass('col-sm-12');
                },
                error: function(xhr, textStatus, errorThrown){  // error handling
                   
                    $("#tbProfile_wrapper").html("");
                    $("#tbProfile_wrapper").append('<div class="alert alert-danger alert-dismissable"><tr><th colspan="3">No data found in the server</th></tr></div>'); 
                }
            },
            "aoColumns": [
            { "mData": "name_profile" },
            { "mData": "registration_date"},
	        { "mData":null,
	              "bSortable": false, 
	              "sClass": "text-center",
		          "mRender": function(data, type, full) {
		            	return (data.state==2)?'<span class="label label-success">A</span>' : "<span class=\"label label-danger\">D</span>";
		            }
		    },
		    { "mData":null,
	              "bSortable": false, 
	              "sClass": "text-center",
		          "mRender": function(data, type, full) {
		        	  	
		        	  buttons='<button data-toggle="modal" data-target="#myModalViewProfile" data-remote="false" '+ 
		        		      'type="button" data-id="'+data.id_profile+'" id="btnEditProfile" class="btn btn-info btn-xs" '+ 
		        		      'href="' + baseurl +'security/profile/view/edit/'+data.id_profile+'" title="Edit" ><i class="fa fa-edit"></i></button> ';
		        	  buttons+='<button data-toggle="modal" data-target="#myModalViewPermit" data-remote="false" type="button" data-id="'+data.id_profile+'" id="btnEditPermit" class="btn btn-warning btn-xs" href="'+baseurl+'security/profile/'+data.id_profile+'/view/permit" ><i class="fa fa-lock"></i></button>';
		        	  return buttons;
		            }
		        }
            
            ]/*,
            "columnDefs": [
            { className: "text-center", "targets": [4,5] }
            //{ className: "text-nowrap", "targets": [0,1] }
          ]*/
    });
    
    
    $('#myModalViewProfile').on('show.bs.modal', function (e) { 
        
        console.log("myModalViewProfile");
        
        var btn = $(e.relatedTarget);
        var idProfile=btn.data('id');
       var data=null;
       var title="Nuevo Perfil";
       var frm='#formSaveProfile';
       
       if(btn.attr("id")==="btnEditProfile"){ 
           console.log("btnViewEditProfile");
            data={"idProfile":idProfile};
            title="Edita Perfil";
            frm='#formEditProfile';
       }
        $.get(btn.attr("href"), function( data ) {
            $('#myModalLabel').html(title);
            $('#modal-body').html(data); 
            $(frm).validator();
        }); 
    });
    
    
    
    $(document).on("submit","#formSaveProfile",function(e){    
      
        if (e.isDefaultPrevented()) {
            return false;
        }
       e.preventDefault();
       
       frm=$(this);
       
       var msj=$("#msjmntPerfil");
           msj.removeAttr('class');
           msj.html("");
           
        
        console.log(":::formSaveProfile");
        //return;
       $.ajax({
            type: "POST",
            url: baseurl+"security/profile/saveProfile",
            contentType: 'application/json',
            data:JSON.stringify(frm.serializeJSON()),
            success: function(result){
              //  alert(result);
            //  chatWith('9','name');
                if(result==1){
                    alerts(0,msj,"El perfil se grabo con exito");   
                    loadDataTable("#tbProfile");
                    frm.trigger('reset');
                }else{
                    alerts(2,msj,"El perfil ya existe");
                }
                
                
            },
            error: function() {
                //estableceAlerta("#msj_urs","errors","A ocurrido un error interno !!!");
                
                alerts(3,msj,"A ocurrido un error interno !!!");
            } 
        });
      //  alert("ajax");
    });
    
    $(document).on("submit","#formEditProfile",function(e){    
      
        if (e.isDefaultPrevented()) {
            return false;
        }
       e.preventDefault();
       
       frm=$(this);
       
       var msj=$("#msjmntPerfil");
           msj.removeAttr('class');
           msj.html("");
           
        
        console.log(":::formEditProfile");
        //return;
       $.ajax({
            type: "POST",
            url: baseurl+"security/profile/editProfile",
            contentType: 'application/json',
            data:JSON.stringify(frm.serializeJSON()),
            success: function(result){
              //  alert(result);
            //  chatWith('9','name');
                if(result==1){
                    alerts(0,msj,"El perfil se modifico con exito");   
                    loadDataTable("#tbProfile");
                    //frm.trigger('reset');
                }else{
                    alerts(2,msj,"No se completo el proceso.. !!!");
                }
            },
            error: function() {
                //estableceAlerta("#msj_urs","errors","A ocurrido un error interno !!!");
                
                alerts(3,msj,"A ocurrido un error interno !!!");
            } 
        });
      //  alert("ajax");
    });
    
    
   
    

        
    $('#myModalViewPermit').on('show.bs.modal', function (e) { 

         console.log("myModalViewPermit");

         var btn = $(e.relatedTarget);
         var idProfile=btn.data('id');
         var data={"idProfile":idProfile};
         
         $('#treeview-checkable').attr("data-id",idProfile);
         //$.data($('#treeview-checkable'), 'id', idPerfil);
         //$('#treeview-checkable').data('id', idPerfil);
        // console.log
        //var title="Registrar usuario";
        //var frm='#frmrRegistraUsuario';
        /*
        if(btn.attr("id")==="btnViewEditUsuario"){ 
            console.log("btnViewEditPerfil");
             data={"idUsuario":idUsuario};
             title="Modificar usuario";
             frm='#frmrModificaUsuario';
        }*/
        var msj=$("#msjasgpms");
        msj.removeAttr('class');
        msj.html("");
         $.get(btn.attr("href"), function( result ) {
             $('#treeview-checkable').treeview({
                    data: result,
                    showIcon: false,
                    showCheckbox: true,
                    levels: 99,
                    onNodeChecked: function(event, node) {
                      
                       var rpta=assignPermits(true,$(this)[0].dataset.id,node.id);
                       console.log(rpta);
                    },
                    onNodeUnchecked: function (event, node) {
                      //$('#checkable-output').prepend('<p>' + node.text + ' was unchecked</p>');
                      //asignaPermiso(false,1,node.id);
                    	$("#msjasgpms").html("");
                      var rpta=assignPermits(false,$(this)[0].dataset.id,node.id);
                      console.log(rpta);
                    } 
                });
             //$("#frmActualizarPassword").validator();
         }); 
     });
     
       function assignPermits(option, id_profile, id_menu){

          return $.post(baseurl+"security/profile/"+id_profile+"/permits",
            {
        	  	option: option,
                id_menu:id_menu
            },
            function(data, status){
                console.log(data);
            }).fail(function() {
                //alert( "error" );
                alerts(3,$("#msjasgpms"),"A ocurrido un error interno !!!");
            });        
       };
    
    $('#permisos-box').slimScroll({
        height: '450px'
    });
     
     $('#btn-expand-all').on('click', function (e) {
      var levels = $('#select-expand-all-levels').val();
      $expandibleTree.treeview('expandAll', { levels: levels, silent: $('#chk-expand-silent').is(':checked') });
    });
     
     /***************************************************************************/
     
     
     $(document).on("submit","#formSettingProfile",function(e){    
         
         if (e.isDefaultPrevented()) {
             return false;
         }
        e.preventDefault();
        
        frm=$(this);
        
        var msj=$("#msjSettiProfile");
            msj.removeAttr('class');
            msj.html("");
            
         
         console.log(":::formEditUser");
         //return;
        $.ajax({
             type: "PUT",
             url: baseurl+"security/setting/profile",
             contentType: 'application/json',
             data:JSON.stringify(frm.serializeJSON()),
             success: function(result){
               //  alert(result);
             //  chatWith('9','name');
                 if(result==1){
                     alerts(0,msj,"El perfil se modifico con exito");   
                     loadDataTable("#tbusuario");
                     //frm.trigger('reset');
                 }else{
                     alerts(2,msj,"No se completo el proceso.. !!!");
                 }
                 
                 
             },
             error: function() {
                 //estableceAlerta("#msj_urs","errors","A ocurrido un error interno !!!");
                 
                 alerts(3,msj,"A ocurrido un error interno !!!");
             } 
         });
       //  alert("ajax");
     });

});