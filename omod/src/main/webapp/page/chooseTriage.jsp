<%--
 *  Copyright 2012 Society for Health Information Systems Programmes, India (HISP India)
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
 *
--%>
<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="../includes/js_css.jsp"%>
<br/>
<openmrs:require privilege="Access Triage" otherwise="/login.htm" redirect="/module/triage/chooseTriage.htm" />

<script type="text/javascript">
	jQuery(document).ready(
			function() {
			 $("#opdChooseCombo").val("0");    
			
			
});

function abcd(this_){
var x = jQuery(this_).val();
alert(x);
if(x != null && x != ''){
ACT.go("main.htm?opdId="+x);
}
}
</script>

<input type="hidden" id="pageId" value="patientQueue" />
<div class="box">
<b style="text-align: center" class="boxHeader">Patient Queue</b>
<table id="opdWardtable" width="100%">
		<tr >
			<td width="40%" align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Location: </td>
			<td width="50%">
				<select id="opdChooseCombo" onchange="abcd(this);">
					<option value="0">-Please select-</option>
					<c:forEach items="${listTriage}" var="opd">
						<option value="${opd.answerConcept.id }"
							<c:if test="${opdId == opd.answerConcept.id  }">selected="selected"</c:if>>${opd.answerConcept.name}</option>
					</c:forEach>
				</select>
	  		</td>
		</tr>
			
</table>
</div>

<%@ include file="/WEB-INF/template/footer.jsp"%>