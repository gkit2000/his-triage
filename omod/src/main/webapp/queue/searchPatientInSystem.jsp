 <%--
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
--%> 
<%@ include file="/WEB-INF/template/include.jsp" %>
<openmrs:require privilege="Access Triage" otherwise="/login.htm" redirect="index.htm" />
<openmrs:globalProperty key="hospitalcore.hospitalName" defaultValue="ddu" var="hospitalName"/>
<div id="searchbox"></div>
<div id="patientResult"></div>

<script type="text/javascript">

	jQuery(document).ready(function() {
		
		jQuery("#searchbox").showPatientSearchBox({		
			searchBoxView: "${hospitalName}/default",
			resultView: "/module/patientqueue/patientsearch/${hospitalName}/patientqueue",		
			target: "#patientResult",
			success: function(data){
				QUEUE.initTableHover();
			} 
		});	
    });
</script>	