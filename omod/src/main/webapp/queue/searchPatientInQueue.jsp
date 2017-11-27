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
<table>
<tr>
   <td>Patient Name/ID: </td>
   <td><input type="text" id="queueText" value="${identifier}" /></td>
   <td><input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="Search" onClick="QUEUE.searchQueue();"/></td>
</tr>

</table>
<c:choose>
	<c:when test="${not empty patientQueues}" >
	<span class="boxHeader">List Patients</span> 
	<table class="box">
		<tr>
			<th>Identifier</th>
			<th>Name</th>
			<th>Age</th>
		</tr>
		<c:forEach items="${patientQueues }" var="queue" varStatus="varStatus">
			<tr class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '  onclick="QUEUE.selectPatientInQueue('${queue.id}','${user}')">
				<td >${queue.patientIdentifier}</td>
				<td> ${fn:replace(queue.patientName,',',' ')}  </td>
             	<td>${queue.age }</td>
			</tr>
		</c:forEach>
	</table>
	</c:when>
	<c:otherwise>
	No Patient found.
	</c:otherwise>
</c:choose>