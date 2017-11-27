
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
<%@ include file="/WEB-INF/template/include.jsp"%>
<openmrs:require privilege="View Patient Queue" otherwise="/login.htm"
	redirect="index.htm" />
<%@ include file="/WEB-INF/template/header.jsp"%>

<script type="text/javascript">
	var contextPath = "${pageContext.request.contextPath}";	
</script>
<%@ include file="includes/js_css.jsp"%>
<script type="text/javascript">
	jQuery(document).ready(
			function() {
			 $("#opdChooseCombo").val("0");    
			
			
});
</script>
<input type="hidden" id="pageId" value="patientQueue" />
<div class="box">
<b style="text-align: center" class="boxHeader">Patient Queue</b>
<table id="opdWardtable" width="100%">
		<tr >
			<td width="40%" align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Location: </td>
			<td width="50%">
				<select id="opdChooseCombo" onchange="QUEUE.changeOPD(this)">
					<option value="0">-Please select-</option>
					<c:forEach items="${listOPD}" var="opd">
						<option value="${opd.answerConcept.id }"
							<c:if test="${opdId == opd.answerConcept.id  }">selected="selected"</c:if>>${opd.answerConcept.name}</option>
					</c:forEach>
				</select>
	  		</td>
		</tr>
			
</table>
</div>
<%@ include file="/WEB-INF/template/footer.jsp"%>
