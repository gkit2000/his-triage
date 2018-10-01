
<%--
 *  Copyright 2009 Society for Health Information Systems Programmes, India (HISP India)
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
<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="includes/js_css.jsp"%>
<openmrs:require privilege="Access Triage" otherwise="/login.htm" redirect="index.htm" />
<script type="text/javascript">
	jQuery(document).ready(
			function() {
				jQuery('#lastMenstrualPeriod').datepicker({
					yearRange : 'c-100:c+100',
					dateFormat : 'dd/mm/yy',
					changeMonth : true,
					changeYear : true,
					maxDate:'0'
				});
	if ("${patient.gender}" == "M" || "${patient.gender}" == "O") {
    jQuery("#lastMenstrualPeriod").attr("disabled", "disabled");
    jQuery("#calendarButtonn").hide();
    }
    jQuery("#calendarButtonn").click(function() {
	jQuery("#lastMenstrualPeriod").datepicker("show");
	 });
   });
				
function calculateBmi(){
var weight = jQuery("#weight").val();
var height = jQuery("#height").val();	
var Bmi =  jQuery("#weight").val()/((jQuery("#height").val()/100)*(jQuery("#height").val()/100));

var b=Math.round(Bmi);
jQuery("#BMI").val(b);
}
</script>
<script type="text/javascript">
function validate(){
var intRegex=/^(?:[1-9]\d*(?:\.\d\d?)?|0\.[1-9]\d?|0\.0[1-9])$/;

if(!StringUtils.isBlank(jQuery("#weight").val())) {
      if (!jQuery("#weight").val().match(intRegex)) {
	  alert("Please enter weight in correct format");
	  return false;
	  }
}

if(!StringUtils.isBlank(jQuery("#height").val())) {
      if (!jQuery("#height").val().match(intRegex)) {
	  alert("Please enter height in correct format");
	  return false;
	  }
}

if(!StringUtils.isBlank(jQuery("#temp").val())) {
      if (!jQuery("#temp").val().match(intRegex)) {
	  alert("Please enter temperature in correct format");
	  return false;
	  }
}

if(!StringUtils.isBlank(jQuery("#systolic").val())) {
      if (!jQuery("#systolic").val().match(intRegex)) {
	  alert("Please enter Systolic B.P in correct format");
	  return false;
	  }
}

if(!StringUtils.isBlank(jQuery("#diastolic").val())) {
      if (!jQuery("#diastolic").val().match(intRegex)) {
	  alert("Please enter Diastolic B.P in correct format");
	  return false;
	  }
}

if(!StringUtils.isBlank(jQuery("#pulsRate").val())) {
      if (!jQuery("#pulsRate").val().match(intRegex)) {
	  alert("Please enter Pulse Rate in correct format");
	  return false;
	  }
}

 re = /^\d{1,2}\/\d{1,2}\/\d{4}$/; 
 if(jQuery("#lastMenstrualPeriod").val() != '' && !jQuery("#lastMenstrualPeriod").val().match(re)) 
 {
 alert("Invalid date format");
 jQuery("#lastMenstrualPeriod").val('');
 return false; 
 } 					
jQuery("#lastMenstrualPeriod").removeAttr("disabled");

if (StringUtils.isBlank(jQuery("#opdWard").val())) {
	alert("Please select OPD Room to visit");
	return false;
}
			
}
</script>
						<label><b class="boxHeader">Current Vitals Details</b></label>
						<form id="triageForm" method="POST" onsubmit="javascript:return validate();">
						<table width="65%" height="50%" class=box>
						<tr>
		<td width="40%"><b>Patient ID:</b>
		<b><a href="http://192.168.1.10/pcs/api/apps/TEI-Search-App/index.html?tei=${patient.patientIdentifier.identifier}">
		${patient.patientIdentifier.identifier}
	</a>
	</b>
		</td>
		<td width="30%">
			<c:if test="${not empty admittedStatus }">
				<span style="background-color:red; color:white">Admitted patient</span>				
			</c:if>
		</td>
		<td width="30%"><b>Triage Name:</b> ${triage.name } </td>
	</tr>
	<tr>
		<td width="40%"><b>Name:</b> ${patient.givenName}&nbsp;${patient.familyName}</td>
		<td width="30%"><b>Age:</b> ${age }</td>
		<td width="30%"><b>Gender:</b> ${patient.gender }</td>
	</tr>

		<td width="30%"><b>Age category:</b> ${ageCategory }</td>
		<td width="30%"><b>Visit Status:</b>
		${visitStatus}	
		</td>
		
	</tr>
						</table>
						<table width="65%" height="50%" class=box>
							<tr>
								<th></th>
								<th></th>
								<th></th>
								<th>Range</th>
								<th>Unit</th>
							</tr>
							<tr>
								<td>Weight</td>
								<td><input type="text" id="weight" name="weight" size="11">
								</td>
								<td></td>
								<td></td>
								<td>Kg</td>
							</tr>
							<tr>
								<td>Height</td>
								<td><input type="text" id="height" name="height" size="11"
									oninput="calculateBmi()"></td>
								<td></td>
								<td></td>
								<td>cm</td>
							</tr>
							<tr>
								<td>BMI</td>
								<td><input type="text" id="BMI" name="BMI" size="11" maxlength="7" readonly="readonly"></td>
								<td></td>
								<td>18.5-24.9</td>

								<td></td>
							</tr>
							<tr>
								<td>Temperature</td>
								<td><input type="text" id="temp" name="temp" size="11"
									maxlength="7"></td>
								<td></td>
								<td>97.7-98.96</td>

								<td>F</td>
							</tr>
							<tr>
								<td>B.P</td>
								<td><input type="text" id="systolic" name="systolic"
									size="5">/<input type="text" id="diastolic"
									name="diastolic" size="5"></td>
								<td></td>
								<td>110/70-140/90</td>

								<td>mm/Hg</td>
							</tr>
							<tr>
								<td>Pulse Rate</td>
								<td><input type="text" id="pulsRate" name="pulsRate"
									size="11"></td>
								<td></td>
								<td>60-90</td>

								<td>/min</td>
							</tr>
							<tr>
								<td>LMP</td>
								<td><input type="text" id="lastMenstrualPeriod" name="lastMenstrualPeriod" size="11" readonly="readonly">
									<img id="calendarButtonn" name="calendarButtonn" src="../../moduleResources/triage/calendar.gif" />
								</td>
							</tr>
							</table>
							<table class=box>
							<tr>
							<td><b>Opd Room to Visit: <label style="color:red">*</label></b>
							<select id="opdWard" name="opdWard"><option value=""><------Select
						OPD------></option>
					<c:forEach items="${OPDs}" var="opd">
						<option value="${opd.id}">${opd.name}</option>
					</c:forEach>
			</select>
			                </td>
							</tr>
							 <tr style="text-align:center">
							 <td><input type="submit" value="Save"></td>
							 </tr>
						</table>
						</form>
<%@ include file="/WEB-INF/template/footer.jsp" %> 