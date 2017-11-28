/**
 *  Copyright 2010 Society for Health Information Systems Programmes, India (HISP India)
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
 **/

package org.openmrs.module.triage.web.controller.queue;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.Patient;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.PatientQueueService;
import org.openmrs.module.hospitalcore.model.TriagePatientQueue;
import org.openmrs.module.triage.util.TriagePatientQueueConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("TriageQueueController")
public class TriageQueueController {
	Log log = LogFactory.getLog(getClass());

	@RequestMapping(value = "/module/triage/triagePatientQueue.htm", method = RequestMethod.GET)
	public String viewOpdPatientQueue(
			@RequestParam(value = "triageId", required = false) Integer triageId,
			Map<String, Object> model, Model modl, HttpServletRequest request) {

		if (triageId != null && triageId > 0) {
			ConceptService conceptService = Context.getConceptService();
			Concept con = conceptService.getConcept(triageId);
			PatientQueueService patientQueueService = Context
					.getService(PatientQueueService.class);
				List<TriagePatientQueue> patientQueues = patientQueueService
						.listTriagePatientQueue(null, triageId, "", 0, 0);
				System.out.println("xxxxxxxxxxxxxxxx------"+patientQueues.size());
				model.put("patientQueues", patientQueues);
		}

		return "/module/triage/queue/triagePatientQueue";
	}

	@RequestMapping(value = "/module/triage/searchPatientInQueue.htm")
	public String searchOpdPatientQueue(
			@RequestParam(value = "opdId", required = false) Integer opdId,
			@RequestParam(value = "text", required = false) String text,
			Map<String, Object> model, Model modl, HttpServletRequest request) {

		if (StringUtils.isNotBlank(text) && opdId != null && opdId > 0) {
			ConceptService conceptService = Context.getConceptService();
			Concept con = conceptService.getConcept(opdId);
			PatientQueueService patientQueueService = Context
					.getService(PatientQueueService.class);
				List<TriagePatientQueue> patientQueues = patientQueueService
						.listTriagePatientQueue(text.trim(), opdId, "", 0, 0);
				model.put("patientQueues", patientQueues);
		}
		model.put("queueText", text);

		return "/module/triage/queue/searchPatientInQueue";
	}

	@RequestMapping(value = "/module/triage/searchPatientInSystem.htm")
	public String searchOpdPatientSystem(
			@RequestParam(value = "opdId", required = false) Integer referralConceptId,
			@RequestParam(value = "phrase", required = false) String phrase,
			Model model, HttpServletRequest request) {

		if (StringUtils.isNotBlank(phrase)) {
			String prefix = Context.getAdministrationService()
					.getGlobalProperty("registration.identifier_prefix");
			if (phrase.contains("-") && !phrase.contains(prefix)) {
				phrase = prefix + phrase;
			}
			List<Patient> patientsList = Context.getPatientService()
					.getPatients(phrase.trim());
			model.addAttribute("listPatients", patientsList);
			model.addAttribute("phrase", phrase);
		}
		return "/module/triage/queue/searchPatientInSystem";
	}
	
	@RequestMapping(value = "/module/triage/selectPatientInQueue.htm", method = RequestMethod.GET)
	public String selectPatientInQueue(@RequestParam("id") Integer queueItemId,
			@RequestParam(value = "usr", required = false) String usr) {
		PatientQueueService queueService = Context
				.getService(PatientQueueService.class);
			TriagePatientQueue queue = queueService
					.getTriagePatientQueueById(queueItemId);
			queue.setStatus(Context.getAuthenticatedUser().getGivenName() + " "
					+ TriagePatientQueueConstants.STATUS);
			queue.setUser(Context.getAuthenticatedUser());
			queueService.saveTriagePatientQueue(queue);
			return "redirect:/module/triage/triageForm.htm?patientId="
					+ queue.getPatient().getPatientId()
					+ "&triageId="
					+ queue.getTriageConcept().getConceptId()
					+ "&visitStatus="
					+ queue.getVisitStatus()
					+ "&queueId="
					+ queue.getId();
	}
}
