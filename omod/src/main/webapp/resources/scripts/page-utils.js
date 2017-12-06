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
QUEUE={
		load : function(url , container)
		{
			jQuery(container).load(url);
		},
		initTableHover : function()
		{
			jQuery("tr").each(function(){
				if( jQuery(this).hasClass("evenRow") || jQuery(this).hasClass("oddRow") )
				{
					jQuery(this).hover(
							function(){jQuery(this).addClass("hover");},
							function(){jQuery(this).removeClass("hover");}
							);
				}
			});
		},
		changeOPD : function(this_)
		{
			var x = jQuery(this_).val();
			if(x != null && x != ''){
				// set cookie
				jQuery.cookie("triageId", x);
				ACT.go("main.htm?triageId="+x);
			
			}
			
		},
		refreshQueue : function()
		{
			jQuery("#Patient_queue").load("triagePatientQueueAjax.htm?triageId="+jQuery("#triageCombo").val(), function(){	QUEUE.initTableHover(); }); 
		},
		searchQueue : function()
		{
			if(jQuery("#queueText").val() != '' && jQuery("#queueText").val() != null && jQuery("#triageCombo").val() !='' && jQuery("#triageCombo").val()!= null ){
				jQuery("#Search_patient_in_queue").load('searchPatientInQueue.htm', {text: jQuery("#queueText").val(), triageId: jQuery("#triageCombo").val() },
					function(){	QUEUE.initTableHover();});
			}else{
				alert('Please enter text for search or choose opd first!');
			}
		},
		searchSystem : function()
		{
			if(jQuery("#phrase").val() != '' && jQuery("#phrase").val() != null && jQuery("#opdCombo").val() !='' && jQuery("#opdCombo").val()!= null ){
				jQuery("#Search_patient_in_system").load('searchPatientInSystem.htm',{phrase: jQuery("#phrase").val(),opdId: jQuery("#opdCombo").val() },
						function(){	QUEUE.initTableHover();  });
			}else{
				alert('Please enter text for search or choose opd first!');
			}
		},
		
		selectPatientInQueue : function(queueId,user)
		{
			ACT.go("selectPatientInQueue.htm?id="+queueId+"&usr="+user);
		},
		selectPatientInSystem : function(patientId)
		{
			ACT.go("selectPatientInSystem.htm?id="+patientId+"&opdId="+jQuery("#opdCombo").val());
		}
		
};


