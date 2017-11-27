/**
 *  Copyright 2009 Society for Health Information Systems Programmes, India (HISP India)
 *
 *  This file is part of Patient-queue module.
 *
 *  Patient-queue module is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.

 *  Patient-queue module is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Patient-queue module.  If not, see <http://www.gnu.org/licenses/>.
 *
*/

var EVT =
{
	ready : function()
	{
		/**
		 * Page Actions
		 */
		var enableCheck = true;
		var pageId = jQuery("#pageId").val();
		if(enableCheck && pageId != undefined && eval("CHECK." + pageId))
		{
			eval("CHECK." + pageId + "()");
		}
		jQuery('.date-pick').datepicker({minDate: '-100y', dateFormat: 'dd/mm/yy'});

		/**
		 * Ajax Indicator when send and receive data
		 */
		if(jQuery.browser.msie)
		{
			jQuery.ajaxSetup({cache: false});
		}
	
	}
};

var CHECK = 
{
	
	
	formExample : function()
	{
		jQuery("#drugCore").autocomplete({source: 'autoCompleteDrugCoreList.htm', minLength: 3 } );
		jQuery('#tabs').tabs();
		var validator = jQuery("#formExample").validate(
		{
			event : "blur",
			rules : 
			{
			
				"drugCore" : { required : true}
				
			}
		});
	},
	patientQueue : function()
	{
		/*var opdId = jQuery.cookie("opdId");
		if( opdId != null && opdId.length> 0)
		{
			jQuery("#opdCombo").val(opdId);
		}*/	
		
		jQuery("#tabs").tabs({cache: true, 
            load : function(event, ui)
            { 
			 	var t = jQuery("#tabs").tabs();
				var selected = t.tabs('option', 'selected');
				
				if( selected == 0 ){
					// patient queue
						
					QUEUE.initTableHover();
					
					clearInterval(jQuery("#intervalId").val()) ;
					var intervalId = setInterval("QUEUE.refreshQueue()"
					, 10000);
					jQuery("#intervalId").val(intervalId);
				}
				if( selected == 1 ){
					// search in queue
					
				}
				if( selected == 2 ){
					// search in system
					
				}
            },
			select : function(event,ui)
			{
				var t = jQuery("#tabs").tabs();
				var selected = t.tabs('option', 'selected');
            	if( selected == 0 ){
					// patient queue
						
					QUEUE.initTableHover();
					
					clearInterval(jQuery("#intervalId").val()) ;
					var intervalId = setInterval("QUEUE.refreshQueue()"
					, 10000);
					jQuery("#intervalId").val(intervalId);
				}
				if( selected == 1 ){
					// search in queue
					
				}
				if( selected == 2 ){
					// search in system
					
				}
			}
            });
	}
	
};

/**
 * Pageload actions trigger
 */

jQuery(document).ready(
	function() 
	{
		EVT.ready();
	}
);



