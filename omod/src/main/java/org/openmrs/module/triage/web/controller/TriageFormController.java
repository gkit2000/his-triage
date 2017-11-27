/**
 *  Copyright 2014 Society for Health Information Systems Programmes, India (HISP India)
 *
 *  This file is part of Patient-dashboard module.
 *
 *  Patient-dashboard module is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.

 *  Patient-dashboard module is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Patient-dashboard module.  If not, see <http://www.gnu.org/licenses/>.
 *
 **/

package org.openmrs.module.triage.web.controller;

import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.openmrs.User;
import org.openmrs.api.AdministrationService;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.PatientQueueService;
import org.openmrs.module.hospitalcore.model.TriagePatientQueue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("TriageFormController")
@RequestMapping("/module/triage/triageForm.htm")
public class TriageFormController {

	@SuppressWarnings("deprecation")
	@RequestMapping(method = RequestMethod.GET)
	public String firstView(
			@RequestParam("patientId") Integer patientId,
			Model model) {

		return "module/triage/triageForm";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String formSummit(@RequestParam(value = "queueId", required = false) Integer queueId,
			@RequestParam(value = "weight", required = false) BigDecimal weight,
			@RequestParam(value = "height", required = false) BigDecimal height,
			
			@RequestParam(value = "mua", required = false) BigDecimal mua,
			@RequestParam(value = "chest", required = false) BigDecimal chest,
			@RequestParam(value = "abdominal", required = false) BigDecimal abdominal,
			
			@RequestParam(value = "temperature", required = false) BigDecimal temperature,
			@RequestParam(value = "sbp", required = false) Integer sbp,
			@RequestParam(value = "dbp", required = false) Integer dbp,
			@RequestParam(value = "resRate", required = false) Integer resRate,
			@RequestParam(value = "pulseRate", required = false) Integer pulseRate,
			@RequestParam(value = "bloodGroup", required = false) String bloodGroup,
			@RequestParam(value = "lastMenstrualPeriod", required = false) Date lastMenstrualPeriod,
			@RequestParam(value = "rhesusFactor", required = false) String rhesusFactor,
			@RequestParam(value = "pitct", required = false) String pitct,
			@RequestParam(value = "opd", required = false) Integer opd,
		
			HttpServletRequest request) throws Exception {
		User user = Context.getAuthenticatedUser();
		//PatientService ps = Context.getPatientService();
		PatientQueueService queueService = Context.getService(PatientQueueService.class);
		TriagePatientQueue queue = queueService.getTriagePatientQueueById(queueId);
		AdministrationService administrationService = Context.getAdministrationService();
		return "module/patientdashboard/triageForm";
	}

}
