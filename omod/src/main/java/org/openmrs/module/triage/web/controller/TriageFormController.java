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

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Location;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.PatientQueueService;
import org.openmrs.module.hospitalcore.model.OpdPatientQueue;
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
		model.addAttribute("OPDs", getSubConcepts("OPD WARD"));
		return "module/triage/triageForm";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String formSummit(HttpServletRequest request, Model model,@RequestParam("patient") Patient patient) throws Exception {

		Encounter encounter = createEncounter(patient, true);
		Date date = new Date();
		User user = Context.getAuthenticatedUser();
		if(request.getParameter("weight")!=null && request.getParameter("weight")!="")
		{ 
		String weight=request.getParameter("weight");
		Double weights=Double.parseDouble(weight);
		Obs vitalstaticweight=new Obs();
		vitalstaticweight.setPatient(patient);
		vitalstaticweight.setEncounter(encounter);
		vitalstaticweight.setConcept(Context.getConceptService().getConcept("WEIGHT"));
		vitalstaticweight.setDateCreated(date);
		//vitalstaticweight.setObsGroup(obsGroup);
		vitalstaticweight.setValueNumeric(weights);
		vitalstaticweight.setCreator(user);
		encounter.addObs(vitalstaticweight);
		}
	if(request.getParameter("height")!=null && request.getParameter("height")!="")
	{
	String height=request.getParameter("height");
	Double heights=Double.parseDouble(height);
    Obs vitalstaticweight=new Obs();
	vitalstaticweight.setPatient(patient);
	vitalstaticweight.setEncounter(encounter);
	vitalstaticweight.setConcept(Context.getConceptService().getConcept("HEIGHT"));
	vitalstaticweight.setDateCreated(date);
	//vitalstaticweight.setObsGroup(obsGroup);
	vitalstaticweight.setValueNumeric(heights);
	vitalstaticweight.setCreator(user);
	encounter.addObs(vitalstaticweight);
	}
	if(request.getParameter("systolic")!=null && request.getParameter("systolic")!="")
	{ 
	String sys=request.getParameter("systolic");
	Double systolic=Double.parseDouble(sys);
	Obs vitalstaticweight=new Obs();
	vitalstaticweight.setPatient(patient);
	vitalstaticweight.setEncounter(encounter);
	vitalstaticweight.setConcept(Context.getConceptService().getConcept("SYSTOLIC BLOOD PRESSURE"));
	vitalstaticweight.setDateCreated(date);
	//vitalstaticweight.setObsGroup(obsGroup);
	vitalstaticweight.setValueNumeric(systolic);
	vitalstaticweight.setCreator(user);
	encounter.addObs(vitalstaticweight);
	}
if(request.getParameter("diastolic")!=null && request.getParameter("diastolic")!="")
{
  String dai=request.getParameter("diastolic");
  Double daistolic=Double.parseDouble(dai);
  Obs vitalstaticweight=new Obs();
  vitalstaticweight.setPatient(patient);
  vitalstaticweight.setEncounter(encounter);
  vitalstaticweight.setConcept(Context.getConceptService().getConcept("DAISTOLIC BLOOD PRESSURE"));
  vitalstaticweight.setDateCreated(date);
  //vitalstaticweight.setObsGroup(obsGroup);
  vitalstaticweight.setValueNumeric(daistolic);
  vitalstaticweight.setCreator(user);
  encounter.addObs(vitalstaticweight);
}
if(request.getParameter("pulsRate")!=null && request.getParameter("pulsRate")!="")
{
  String pulse=request.getParameter("pulsRate");
  Double pulses=Double.parseDouble(pulse);
  Obs vitalstaticweight=new Obs();
  vitalstaticweight.setPatient(patient);
  vitalstaticweight.setEncounter(encounter);
  vitalstaticweight.setConcept(Context.getConceptService().getConcept("PULSE RATE"));
  vitalstaticweight.setDateCreated(date);
  //vitalstaticweight.setObsGroup(obsGroup);
  vitalstaticweight.setValueNumeric(pulses);
  vitalstaticweight.setCreator(user);
  encounter.addObs(vitalstaticweight);
}
if(request.getParameter("BMI")!=null && request.getParameter("BMI")!="")
{
  String BMI=request.getParameter("BMI");
  Double bmiInDouble=Double.parseDouble(BMI);
  Obs vitalstaticweight=new Obs();
  vitalstaticweight.setPatient(patient);
  vitalstaticweight.setEncounter(encounter);
  vitalstaticweight.setConcept(Context.getConceptService().getConcept("BMI"));
  vitalstaticweight.setDateCreated(date);
  //vitalstaticweight.setObsGroup(obsGroup);
  vitalstaticweight.setValueNumeric( bmiInDouble);
  vitalstaticweight.setCreator(user);
  encounter.addObs(vitalstaticweight);
}
if(request.getParameter("temp")!=null && request.getParameter("temp")!="")
{
  String temperature=request.getParameter("temp");
  Double temperatures=Double.parseDouble(temperature);
  Obs temperatureValue=new Obs();
  temperatureValue.setPatient(patient);
  temperatureValue.setEncounter(encounter);
  temperatureValue.setConcept(Context.getConceptService().getConcept("TEMPERATURE"));
  temperatureValue.setDateCreated(date);
  //temperatureValue.setObsGroup(obsGroup);
  temperatureValue.setValueNumeric(temperatures);
  temperatureValue.setCreator(user);
  encounter.addObs(temperatureValue);
}
if(request.getParameter("lastMenstrualPeriod")!=null && request.getParameter("lastMenstrualPeriod")!="")
{ 
   String lmp=request.getParameter("lastMenstrualPeriod");
   SimpleDateFormat formatterExt = new SimpleDateFormat("dd/MM/yyyy");
   Date lmpdate = (Date)formatterExt.parse(lmp);
   Obs vitalstaticweight=new Obs();
   vitalstaticweight.setPatient(patient);
   vitalstaticweight.setEncounter(encounter);
   vitalstaticweight.setConcept(Context.getConceptService().getConcept("LAST MENSTRUAL PERIOD"));
   vitalstaticweight.setDateCreated(date);
   //vitalstaticweight.setObsGroup(obsGroup);
   vitalstaticweight.setValueDatetime(lmpdate);
   vitalstaticweight.setCreator(user);
   encounter.addObs(vitalstaticweight);
}

Concept opdWardConcept = Context.getConceptService().getConcept("OPD WARD");
String opdWard=request.getParameter("opdWard");
Concept selectedOPDConcept = Context.getConceptService().getConcept(
    Integer.parseInt(opdWard));
Obs opdObs = new Obs();
opdObs.setConcept(opdWardConcept);
opdObs.setValueCoded(selectedOPDConcept);
encounter.addObs(opdObs);

encounter = Context.getEncounterService().saveEncounter(encounter);

sendPatientToOPDQueue(patient, selectedOPDConcept, false,encounter);
Integer triageId=Integer.parseInt(request.getParameter("triageId"));
		return "module/triage/triagePatientQueue.htm?triageId="+triageId;
	}
	
	public static String getSubConcepts(String conceptName) {
		Concept opdward = Context.getConceptService().getConcept(conceptName);
		StringBuilder sb = new StringBuilder();
		for (ConceptAnswer ca : opdward.getAnswers()) {
			sb.append(ca.getAnswerConcept().getConceptId() + "," + ca.getAnswerConcept().getName().getName() + "|");
		}
		return sb.toString();
	}
	
	public static Encounter createEncounter(Patient patient, boolean revisit) {
		EncounterType encounterType = null;
		if (!revisit) {
			encounterType = Context.getEncounterService().getEncounterType("TRIAGEINITIAL");
		} else {
			encounterType = Context.getEncounterService().getEncounterType("TRIAGEREVISIT");
		}
		
		// get location
		Location location = new Location(1);
		
		// create encounter
		Encounter encounter = new Encounter();
		encounter.setEncounterType(encounterType);
		encounter.setCreator(Context.getAuthenticatedUser());
		encounter.setProvider(Context.getAuthenticatedUser().getPerson());
		encounter.setEncounterDatetime(new Date());
		encounter.setPatient(patient);
		encounter.setLocation(location);
		return encounter;
	}
	
	public static void sendPatientToOPDQueue(Patient patient, Concept selectedOPDConcept, boolean revisit,Encounter encounter) {
		Concept referralConcept = null;
		User user = Context.getAuthenticatedUser();
		if (!revisit) {
			referralConcept = Context.getConceptService().getConcept("New Patient");
		} else {
			referralConcept = Context.getConceptService().getConcept("Revisit");
		}
		
		OpdPatientQueue queue = Context.getService(PatientQueueService.class).getOpdPatientQueue(
		    patient.getPatientIdentifier().getIdentifier(), selectedOPDConcept.getConceptId());
		if (queue == null) {
			queue = new OpdPatientQueue();
			queue.setUser(user);
			queue.setPatient(patient);
			queue.setCreatedOn(new Date());
			queue.setBirthDate(patient.getBirthdate());
			queue.setPatientIdentifier(patient.getPatientIdentifier().getIdentifier());
			queue.setOpdConcept(selectedOPDConcept);
			queue.setOpdConceptName(selectedOPDConcept.getName().getName());
			queue.setPatientName(patient.getGivenName() + " " + patient.getMiddleName() + " " + patient.getFamilyName());
			queue.setReferralConcept(referralConcept);
			queue.setReferralConceptName(referralConcept.getName().getName());
			queue.setSex(patient.getGender());
			queue.setRegistrationEncounter(encounter);
			PatientQueueService queueService = Context.getService(PatientQueueService.class);
			queueService.saveOpdPatientQueue(queue);
			
		}
	}

}
