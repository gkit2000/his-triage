
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
				});
</script>
						<label><b class="boxHeader">Current Vitals Details</b></label>
						<form>
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
									<img id="calendarButtonn" src="../../moduleResources/triage/calendar.gif" />
								</td>
							</tr>
							 <tr style="text-align:center">
							 <td><input type="submit" value="Save"></td>
							 </tr>
						</table>
						</form>
<%@ include file="/WEB-INF/template/footer.jsp" %> 