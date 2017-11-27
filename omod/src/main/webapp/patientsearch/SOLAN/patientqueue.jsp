 <%--
 *  Copyright 2013 Society for Health Information Systems Programmes, India (HISP India)
 *
 *  This file is part of Triage module.
 *
 *  Triage module is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.

 *  Triage module is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Triage module.  If not, see <http://www.gnu.org/licenses/>.
--%> 
<%@ include file="/WEB-INF/template/include.jsp" %>
<%@ page import="java.util.Map" %>
<%@ page import="org.openmrs.Patient" %>

<script type="text/javascript">
	
	PATIENTSEARCHRESULT = {
		oldBackgroundColor: ""
	};
	
	jQuery(document).ready(function(){
	
		// hover rows
		jQuery(".patientSearchRow").hover(
			function(event){					
				obj = event.target;
				while(obj.tagName!="TR"){
					obj = obj.parentNode;
				}
				PATIENTSEARCHRESULT.oldBackgroundColor = jQuery(obj).css("background-color");
				jQuery(obj).css("background-color", "#00FF99");									
			}, 
			function(event){
				obj = event.target;
				while(obj.tagName!="TR"){
					obj = obj.parentNode;
				}
				jQuery(obj).css("background-color", PATIENTSEARCHRESULT.oldBackgroundColor);				
			}
		);
	});
</script>

<c:choose>
	<c:when test="${not empty patients}" >		
	<table style="width:100%">
		<tr>			
			<td align="center" style:><b>Patient ID</b></td>
			<td align="center"><b>Name</b></td>
			<td align="center"><b>Age</b></td>
			<td align="center"><b>Gender</b></td>			
			<!-- <td><b>Category</b></td>
			<td><b>Relative Name</b></td>
			<td><b>Phone number</b></td> -->
			<td align="center"><b>Date Of Previous Visit</b></td>
		</tr>
		<c:forEach items="${patients}" var="patient" varStatus="varStatus">
			<tr class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } patientSearchRow' onclick="QUEUE.selectPatientInSystem(${patient.patientId})">				
				<td align="center">
					${patient.patientIdentifier.identifier}
				</td>
				<td align="center">${patient.givenName}  ${patient.familyName} ${fn:replace(patient.middleName,',',' ')}</td>
				<td align="center"> 
                	<c:choose>
                		<c:when test="${patient.age == 0}">&lt 1</c:when>
                		<c:otherwise >${patient.age}</c:otherwise>
                	</c:choose>
                </td>
				<%-- <td>
					<c:choose>
                		<c:when test="${patient.gender eq 'M'}">
							<img src="${pageContext.request.contextPath}/images/male.gif"/>
						</c:when>
                		<c:otherwise><img src="${pageContext.request.contextPath}/images/female.gif"/></c:otherwise>
                	</c:choose>
				</td>   --%> 
				<td align="center">${patient.gender}</td>                
				<%-- <td> <%
						Patient patient = (Patient) pageContext.getAttribute("patient");
						Map<Integer, Map<Integer, String>> attributes = (Map<Integer, Map<Integer, String>>) pageContext.findAttribute("attributeMap");						
						Map<Integer, String> patientAttributes = (Map<Integer, String>) attributes.get(patient.getPatientId());						
						String category = patientAttributes.get(14); 
						if(category!=null)
							out.print(category);
					%>
                </td>
				<td> 
                	<%
						String relativeName = patientAttributes.get(8); 
						if(relativeName!=null)
							out.print(relativeName);
					%>
                </td>
                <td> 
                	<%						
						String phoneNumber = patientAttributes.get(16);
						if(phoneNumber!=null)
							out.print(phoneNumber);					
					%>
                </td> --%>
				<td align="center"> 
                <openmrs:formatDate date="${lastVisitTime[patient.patientId]}"/>              	
                </td>                
			</tr>
		</c:forEach>
	</table>
	</c:when>
	<c:otherwise>
	No Patient found.
	</c:otherwise>
</c:choose>