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


<table cellpadding="5" cellspacing="0" width="100%" id="queueList">
<tr align="center" >
	<th align="center">S.No</th>
	<th align="center"><spring:message code="triage.queue.patientId"/></th>
	<th align="center"><spring:message code="triage.queue.patientName"/></th>
	<th align="center">Age</th>
	<th align="center">Gender</th>
	<th align="center"><spring:message code="triage.queue.visitStatus"/></th>
	<c:choose>
	<c:when test="${user eq 'triageUser'}">
	<th align="center">Nurse Processing</th>
	</c:when>
	<c:otherwise>
	<th align="center">Doctor Processing</th>
	</c:otherwise>
	</c:choose>
</tr>
<c:choose>
<c:when test="${not empty patientQueues}">
<c:forEach items="${patientQueues}" var="queue" varStatus="varStatus">
	<tr  align="center" class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } ' onclick="QUEUE.selectPatientInQueue('${queue.id}','${user}');">
		<td align="center"><c:out value="${varStatus.count }"/></td>	
		<td align="center">${queue.patientIdentifier}</td>
		<td align="center">${fn:replace(fn:replace(queue.patientName,',',' '),'null','')}</td>
		<td align="center">${queue.age }</td>
		<td align="center">${queue.sex}</td>
		<td align="center">${queue.referralConceptName}
		<td align="center">${queue.status}</td>
	</tr>
</c:forEach>

</c:when>
<c:otherwise>
<tr align="center" >
	<td colspan="6">No patient found</td>
</tr>
</c:otherwise>
</c:choose>
</table>
