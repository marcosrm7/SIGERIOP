/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
jQuery(document).ready( function () {
	
	console.log("functions.js");
	
    Object.defineProperty(console, '_commandLineAPI', { get : function() { throw 'Nooo!' } });
    
    //ruta principal del proyecto
    baseurl=jQuery("base").attr("href");
	baseUrlError=baseurl+"error";
    //console.log(location.pathname);
    console.log(window.location.pathname.substr(0, window.location.pathname.lastIndexOf('/')));
    //console.log($("#opmnu").data("menu").length);
    if ( $("#opmnu").data("menu") ) {
    	 
    	$.each($("#opmnu").data("menu").split(":"), function(index, value) { 
            //console.log(index + ': ' + value); 
            $(value).addClass("active");
        });

     
    }
    
    $( document ).ajaxError(function(event, jqxhr, settings, thrownError) {
    	  //$( ".log" ).text( "Triggered ajaxError handler." );
      	//console.log(event)
        	console.log(jqxhr);
        	//console.log(settings)
        	//console.log(thrownError)
      	if(jqxhr.status==401)
      		window.location = baseurl;

    	});
         
        
    $(document).ajaxStart(function () {
       // $.blockUI({ message: '<h1><img src="busy.gif" /> Just a moment...</h1>' });
       blockUI();
    });
    
    $(document).ajaxStop(function () {
        $.unblockUI();
    });
    
    alerts = function(op,obj,msj){
        
        switch(op) {
            case 0:
                clase="alert alert-success alert-dismissable";
                break;
            case 1:
                clase="alert alert-info alert-dismissable";
                break;
            case 2:
                clase="alert alert-warning alert-dismissable";
                break;    
            default:
                clase="alert alert-danger alert-dismissable";
        } 
        //console.log(decodeURIComponent(escape(msj)));
        obj.addClass(clase);
        obj.html("<button type=\"button\" class=\"close\" data-hide-closest=\"alert\"  aria-hidden=\"true\"> &#215;</button><p>"+msj+"</p>");
        obj.show();
    };
    //decodeURIComponent(escape(msj))
    $(document).on("click", "[data-hide-closest]", function(e) {
        console.log("alertt:");
        $(this).parent().hide();
        /*var $this = $(this);
        $this.closest($this.attr("data-hide-closest")).hide();*/
    });
    
    loadDataTable = function (idtable) {
        //$(idtable).ajax.reload();
        $(idtable).DataTable().ajax.reload();
    };
    
    
    blockUI=function(p1=null,p2=null){
    	var msj=null;
    	if(p1==1){
    		msj='<div>'+((p2!=null)? p2 : 'Procesando...')+'</div> <img src="'+baseurl+'plugins/blockUI/widget-loader-lg.gif" style="width:100%;" />';
    	
    	}else if(p1==2){
    		msj='<div>'+((p2==null)? 'Procesando...' : p2 )+'</div>';
    	}else{
    		msj='<div>Procesando...</div> <img src="'+baseurl+'plugins/blockUI/widget-loader-lg.gif" style="width:100%;" />';	
    	}
    	
    	
        $.blockUI({
            message:msj, 
            css: {
                border: 'none',
                //width: 'auto',
                //top:  ($(window).height() - 400) /2 + 'px', 
                left: ($(window).width() - 200) /2 + 'px', 
                width: '200px', 
                padding: '15px', 
                'background-color': 'transparent',
                '-webkit-border-radius': '10px',
                'border-radius': '10px',
                opacity: .6,
                color: '#337ab7',
                'font-weight':'bold',
                'z-index':2011
            },
            overlayCSS:  { backgroundColor: '#FFFFFF'},
            
            baseZ: 2000
            //:white
        });
    };
   
    ezBSAlert=function(options) {
	var deferredObject = $.Deferred();
	var defaults = {
		type: "alert", //alert, prompt,confirm 
		modalSize: 'modal-sm', //modal-sm, modal-lg
		okButtonText: 'Ok',
		cancelButtonText: 'Cancel',
		yesButtonText: 'Yes',
		noButtonText: 'No',
		headerText: 'Attention',
		messageText: 'Message',
		alertType: 'default', //default, primary, success, info, warning, danger
		inputFieldType: 'text', //could ask for number,email,etc
                backdrop: 'static'
	};
	$.extend(defaults, options);
  
	var _show = function(){
		var headClass = "navbar-default";
		switch (defaults.alertType) {
			case "primary":
				headClass = "alert-primary";
				break;
			case "success":
				headClass = "alert-success";
				break;
			case "info":
				headClass = "alert-info";
				break;
			case "warning":
				headClass = "alert-warning";
				break;
			case "danger":
				headClass = "alert-danger";
				break;
        }
		$('BODY').append(
			'<div id="ezAlerts" class="modal fade">' +
			'<div class="modal-dialog" class="' + defaults.modalSize + '">' +
			'<div class="modal-content">' +
			'<div id="ezAlerts-header" class="modal-header ' + headClass + '">' +
			'<button id="close-button" type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&#215;</span><span class="sr-only">Close</span></button>' +
			'<h4 id="ezAlerts-title" class="modal-title">Modal title</h4>' +
			'</div>' +
			'<div id="ezAlerts-body" class="modal-body">' +
			'<div id="ezAlerts-message" ></div>' +
			'</div>' +
			'<div id="ezAlerts-footer" class="modal-footer">' +
			'</div>' +
			'</div>' +
			'</div>' +
			'</div>'
		);
/*
		$('.modal-header').css({
			'padding': '15px 15px',
			'-webkit-border-top-left-radius': '5px',
			'-webkit-border-top-right-radius': '5px',
			'-moz-border-radius-topleft': '5px',
			'-moz-border-radius-topright': '5px',
			'border-top-left-radius': '5px',
			'border-top-right-radius': '5px'
		});*/
    
		$('#ezAlerts-title').text(defaults.headerText);
		$('#ezAlerts-message').html(defaults.messageText);

		var keyb = "false", backd = "static";
		var calbackParam = "";
		switch (defaults.type) {
			case 'alert':
				keyb = "true";
				backd = "true";
				$('#ezAlerts-footer').html('<button class="btn btn-' + defaults.alertType + '">' + defaults.okButtonText + '</button>').on('click', ".btn", function () {
					calbackParam = true;
					$('#ezAlerts').modal('hide');
				});
				break;
			case 'confirm':
				var btnhtml = '<button id="ezok-btn" class="btn btn-github">' + defaults.yesButtonText + '</button>';
				if (defaults.noButtonText && defaults.noButtonText.length > 0) {
					btnhtml += '<button id="ezclose-btn" class="btn btn-default">' + defaults.noButtonText + '</button>';
				}
				$('#ezAlerts-footer').html(btnhtml).on('click', 'button', function (e) {
						if (e.target.id === 'ezok-btn') {
							calbackParam = true;
							$('#ezAlerts').modal('hide');
						} else if (e.target.id === 'ezclose-btn') {
							calbackParam = false;
							$('#ezAlerts').modal('hide');
						}
					});
				break;
			case 'prompt':
				$('#ezAlerts-message').html( decodeURIComponent(escape(defaults.messageText)) + '<br /><br /><div class="form-group"><input type="' + defaults.inputFieldType + '" class="form-control" id="prompt" /></div>');
				$('#ezAlerts-footer').html('<button class="btn btn-primary">' + defaults.okButtonText + '</button>').on('click', ".btn", function () {
					calbackParam = $('#prompt').val();
					$('#ezAlerts').modal('hide');
				});
				break;
		}
   
		$('#ezAlerts').modal({ 
          show: false, 
          backdrop: backd, 
          keyboard: keyb 
        }).on('hidden.bs.modal', function (e) {
			$('#ezAlerts').remove();
			deferredObject.resolve(calbackParam);
		}).on('shown.bs.modal', function (e) {
			if ($('#prompt').length > 0) {
				$('#prompt').focus();
			}
		}).modal('show').css('zIndex',2100);;
	};
    
  _show();  
  return deferredObject.promise();    
};

    
 //-----------------------------------------------------------------------------
	if ( $.fn.dataTable!=null) {
		 
		$.fn.dataTable.ext.errMode = function ( settings, helpPage, message ) { 
	        console.warn(message);
	    }; 
	 
	}
    

    $('#myModalViewPassword').on('show.bs.modal', function (e) {
       $("#formChangePassword").trigger('reset');
       var msj=$("#msjcmbclave");
           msj.removeAttr('class');
           msj.html("");
   });

   $(document).on("submit","#formChangePassword",function(e){    
     //alert("sssss");
       
        if (e.isDefaultPrevented()) {
            return false;
        }
        e.preventDefault();
        
       // frm=$(this);        
        var frm = $(this);
        var formData = frm.serializeArray();
        formData.push({
            name:"id",
            value: $("#iduser").data('id')
        });

        //console.log(formData);
       /* $.each(formData, function(key, obj)
        {
            if (obj.name == "numero")
                obj.value = "51" + obj.value;
        });*/
       
       var msj=$("#msjcmbclave");
           msj.removeAttr('class');
           msj.html("");
           
        
        //console.log(":::frmActualizarPassword");
        //return;
       $.ajax({
            type: "POST",
            url: baseurl+"/security/user/changePassword",
            //contentType: 'application/json',
            data:formData,
            success: function(result){
              //  alert(result);
            //  chatWith('9','name');
                if(result==1){
                    alerts(0,msj,"La contraseña se cambio correctamente");   
                    frm.trigger('reset');
                }else{
                    alerts(2,msj,"La contraseña actual no es valida");
                }
            },
            error: function() {
                alerts(3,msj,"A ocurrido un error interno !!!");
            } 
        });
        
   }); 
   
   /*Encode:*/
    function encode_utf8(s) { 
     return unescape(encodeURIComponent(s)); 
    } 
    
    /*Decode:*/
      function decode_utf8(s) { 
         return decodeURIComponent(escape(s)); 
     }
    
    //alert("cccccccccc");
    /*
    $.fn.datepicker.defaults.format = "dd-mm-yyyy";
    $.fn.datepicker.defaults.language = 'es';
    */
    /*$.fn.datepicker.defaults, {
        format: 'dd-mm-yyyy',
        language: 'es'
    };*/
   
   if ( $.fn.datepicker!=null) {
   $.extend( $.fn.datepicker.defaults, {
	   format: 'dd-mm-yyyy', 
	   language: 'es'
	 
	} );
   }
   
	$(window).resize(function() {
	 	//console.log($(window).height());
	    $('#content').height($(window).height() - 230);
	});
	$(window).trigger('resize');


	jQuery.fn.dataTableExt.oApi.fnFilterOnReturn = function (oSettings) {
		var _that = this;

		this.each(function (i) {
			$.fn.dataTableExt.iApiIndex = i;
			var $this = this;
			var anControl = $('input', _that.fnSettings().aanFeatures.f);
			anControl
				.unbind('keyup search input')
				.bind('keypress', function (e) {
					if (e.which == 13) {
						$.fn.dataTableExt.iApiIndex = i;
						_that.fnFilter(anControl.val());
					}
				});
			return this;
		});
		return this;
	};
});

$(document).ready(function(){
	$('#whole').bind('DOMMouseScroll mousewheel', function(e){
		if(e.originalEvent.wheelDelta > 0 || e.originalEvent.detail < 0) {
			alert("up");
		}
		else{
			alert("down");
		}
	});
});


/*
 
$(document).ready(function(){
  $("#btnAlert").on("click", function(){  	
    var prom = ezBSAlert({
      messageText: "hello world",
      alertType: "danger"
    }).done(function (e) {
      $("body").append('<div>Callback from alert</div>');
    });
  });   
  
  $("#btnConfirm").on("click", function(){  	
    ezBSAlert({
      type: "confirm",
      messageText: "hello world",
      alertType: "info"
    }).done(function (e) {
      $("body").append('<div>Callback from confirm ' + e + '</div>');
    });
  });   

  $("#btnPrompt").on("click", function(){  	
    ezBSAlert({
      type: "prompt",
      messageText: "Enter Something",
      alertType: "primary"
    }).done(function (e) {
      ezBSAlert({
        messageText: "You entered: " + e,
        alertType: "success"
      });
    });
  });   
  
});
 */