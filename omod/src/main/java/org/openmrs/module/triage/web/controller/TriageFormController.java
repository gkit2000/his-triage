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
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Location;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.User;
import org.openmrs.api.EncounterService;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.HospitalCoreService;
import org.openmrs.module.hospitalcore.IpdService;
import org.openmrs.module.hospitalcore.PatientQueueService;
import org.openmrs.module.hospitalcore.model.IpdPatientAdmitted;
import org.openmrs.module.hospitalcore.model.OpdPatientQueue;
import org.openmrs.module.hospitalcore.model.TriagePatientData;
import org.openmrs.module.hospitalcore.model.TriagePatientQueue;
import org.openmrs.module.hospitalcore.model.TriagePatientQueueLog;
import org.openmrs.module.hospitalcore.util.PatientUtils;
import org.openmrs.module.triage.util.TriageUtil;
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
			@RequestParam("triageId") Integer triageId,
			@RequestParam("queueId") Integer queueId,
			@RequestParam("referralId") Integer referralId,
			Model model) {
		HospitalCoreService hcs=Context.getService(HospitalCoreService.class);
		PatientService ps = Context.getPatientService();
		Patient patient = ps.getPatient(patientId);
		model.addAttribute("patient",patient);
		model.addAttribute("triage", Context.getConceptService().getConcept(triageId));
		Date birthday = patient.getBirthdate();
		model.addAttribute("age", PatientUtils.estimateAge(birthday));
		model.addAttribute("ageCategory", TriageUtil.calcAgeClass(patient.getAge()));
        List<EncounterType> types = new ArrayList<EncounterType>();
		EncounterType reginit = Context.getEncounterService().getEncounterType("REGINITIAL");
		types.add(reginit);
		EncounterType regrevisit = Context.getEncounterService().getEncounterType("REGREVISIT");
		types.add(regrevisit);
		EncounterType labencounter = Context.getEncounterService().getEncounterType("LABENCOUNTER");
		types.add(labencounter);
		EncounterType radiologyencounter = Context.getEncounterService().getEncounterType("RADIOLOGYENCOUNTER");
		types.add(radiologyencounter);
		EncounterType opdencounter = Context.getEncounterService().getEncounterType("OPDENCOUNTER");
		types.add(opdencounter);
		EncounterType ipdencounter = Context.getEncounterService().getEncounterType("IPDENCOUNTER");
		types.add(ipdencounter);
		EncounterType triageencounter = Context.getEncounterService().getEncounterType("TRIAGEENCOUNTER");
		types.add(triageencounter);
		
		PatientQueueService pqs = Context.getService(PatientQueueService.class);
		IpdService ipdService=Context.getService(IpdService.class);
		TriagePatientQueue triagePatientQueue = pqs.getTriagePatientQueueById(queueId);
		Date createdOn = null;
		if(queueId!=null){
			createdOn = triagePatientQueue.getCreatedOn();
		}
		else{
			createdOn = new Date();
		}
		// get Encounter by date
		Encounter encounter = null;
		EncounterService es = Context.getEncounterService();
		List<Encounter> listEncounter = es.getEncounters(patient, createdOn, createdOn);
		if (1 == listEncounter.size())
			encounter = listEncounter.get(0);
		else {
			encounter = hcs.getLastVisitEncounter(patient, types);
		}
		Concept referralConcept = Context.getConceptService().getConcept("PATIENT REFERRED TO HOSPITAL?");
		Concept referredTypeConcept = Context.getConceptService().getConcept("REASON FOR REFERRAL");
		Obs referral = null;
		if(encounter!=null){
		Set<Obs> setObs = Context.getObsService().getObservations(encounter);
		Iterator<Obs> obs = setObs.iterator();
		Obs o = new Obs();
		while (obs.hasNext()) {
			o = obs.next();
			if (referredTypeConcept.getId().equals(o.getConcept().getId())){
				referral = o;
			}
		  }
		}
		if (null != referral){
			model.addAttribute("referredType", referral.getValueCoded().getName());
	     }
		model.addAttribute("referral", Context.getConceptService().getConcept(referralId));
        IpdPatientAdmitted admitted = ipdService.getAdmittedByPatientId(patientId);
		if (admitted != null) {
			model.addAttribute("admittedStatus", "Admitted");
		}
		model.addAttribute("OPDs", getSubConcepts("OPD WARD"));
		
		List<Obs> opdward=hcs.getObsByPersonAndConcept(Context.getPersonService().getPerson(patientId), Context.getConceptService().getConcept("TRIAGE"));
		if(opdward.size()==1){
			model.addAttribute("visitStatus","New Patient");	
		}
		else if(opdward.size()>1){
			model.addAttribute("visitStatus","revisit");	
		}
		
		return "module/triage/triageForm";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String formSummit(@RequestParam("queueId") Integer queueId,
			@RequestParam("triageId") Integer triageId,
			HttpServletRequest request, Model model,@RequestParam("patientId") Patient patient) throws Exception {

		PatientQueueService pqs = Context.getService(PatientQueueService.class);
		TriagePatientQueue triagePatientQueue=pqs.getTriagePatientQueueById(queueId);
		TriagePatientData triagePatientData = new TriagePatientData();
		Encounter encounter = createEncounter(patient, true);
		Date date = new Date();
		User user = Context.getAuthenticatedUser();
		//vital static
		/*
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
		triagePatientData.setWeight(weights);
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
	triagePatientData.setHeight(heights);
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
	triagePatientData.setSystolic(systolic);
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
  triagePatientData.setDaistolic(daistolic);
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
  triagePatientData.setPulsRate(pulses);
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
  triagePatientData.setBmi(bmiInDouble);
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
  triagePatientData.setTemperature(temperatures);
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
   triagePatientData.setLastMenstrualDate(lmpdate);
}*/
		if(request.getParameter("weight")!=null && request.getParameter("weight")!="")
		{ 
		String weight=request.getParameter("weight");
		Double weights=Double.parseDouble(weight);
		triagePatientData.setWeight(weights);
		}
	if(request.getParameter("height")!=null && request.getParameter("height")!="")
	{
	String height=request.getParameter("height");
	Double heights=Double.parseDouble(height);
	triagePatientData.setHeight(heights);
	}
	if(request.getParameter("systolic")!=null && request.getParameter("systolic")!="")
	{ 
	String sys=request.getParameter("systolic");
	Double systolic=Double.parseDouble(sys);
	triagePatientData.setSystolic(systolic);
	}
if(request.getParameter("diastolic")!=null && request.getParameter("diastolic")!="")
{
  String dai=request.getParameter("diastolic");
  Double daistolic=Double.parseDouble(dai);
  triagePatientData.setDaistolic(daistolic);
}
if(request.getParameter("pulsRate")!=null && request.getParameter("pulsRate")!="")
{
  String pulse=request.getParameter("pulsRate");
  Double pulses=Double.parseDouble(pulse);
  triagePatientData.setPulsRate(pulses);
}
if(request.getParameter("BMI")!=null && request.getParameter("BMI")!="")
{
  String BMI=request.getParameter("BMI");
  Double bmiInDouble=Double.parseDouble(BMI);
  triagePatientData.setBmi(bmiInDouble);
}
if(request.getParameter("temp")!=null && request.getParameter("temp")!="")
{
  String temperature=request.getParameter("temp");
  Double temperatures=Double.parseDouble(temperature);
  triagePatientData.setTemperature(temperatures);
}
if(request.getParameter("lastMenstrualPeriod")!=null && request.getParameter("lastMenstrualPeriod")!="")
{ 
   String lmp=request.getParameter("lastMenstrualPeriod");
   SimpleDateFormat formatterExt = new SimpleDateFormat("dd/MM/yyyy");
   Date lmpdate = (Date)formatterExt.parse(lmp);
   triagePatientData.setLastMenstrualDate(lmpdate);
}

triagePatientData.setCreatedOn(date);

Concept opdWardConcept = Context.getConceptService().getConcept("OPD WARD");
String opdWard=request.getParameter("opdWard");
Concept selectedOPDConcept = Context.getConceptService().getConcept(
    Integer.parseInt(opdWard));
Obs opdObs = new Obs();
opdObs.setConcept(opdWardConcept);
opdObs.setValueCoded(selectedOPDConcept);
encounter.addObs(opdObs);

encounter = Context.getEncounterService().saveEncounter(encounter);

        TriagePatientQueueLog queueLog = new TriagePatientQueueLog();		
		queueLog.setTriageConcept(triagePatientQueue.getTriageConcept());
		queueLog.setTriageConceptName(triagePatientQueue.getTriageConceptName());
		queueLog.setPatient(triagePatientQueue.getPatient());
        queueLog.setCreatedOn(triagePatientQueue.getCreatedOn());
        queueLog.setPatientIdentifier(triagePatientQueue.getPatientIdentifier());
        queueLog.setPatientName(triagePatientQueue.getPatientName());
        queueLog.setReferralConcept(triagePatientQueue.getReferralConcept());
        queueLog.setReferralConceptName(triagePatientQueue.getReferralConceptName());
        queueLog.setSex(triagePatientQueue.getSex());
        queueLog.setUser(user);
        queueLog.setStatus("processed");
        queueLog.setBirthDate(triagePatientQueue.getBirthDate());
        queueLog.setCategory(triagePatientQueue.getCategory());
        queueLog.setRegistrationEncounter(triagePatientQueue.getRegistrationEncounter());
        queueLog.setTriageEncounter(encounter);
		TriagePatientQueueLog triagePatientLog = pqs.saveTriagePatientQueueLog(queueLog);
		triagePatientData.setTriageLogId(triagePatientLog);
		triagePatientData=pqs.saveTriagePatientData(triagePatientData);
		sendPatientToOPDQueue(patient, selectedOPDConcept, false,queueLog.getRegistrationEncounter(),queueLog.getTriageEncounter(),triagePatientData);
        pqs.deleteTriagePatientQueue(triagePatientQueue);
		return "redirect:/module/triage/main.htm?triageId="+triageId;
	}
	
	public static List<Concept> getSubConcepts(String conceptName) {
		Concept opdward = Context.getConceptService().getConcept(conceptName);
		List<Concept> listConcept=new LinkedList<Concept>();
		for (ConceptAnswer ca : opdward.getAnswers()) {
			listConcept.add(ca.getAnswerConcept());	
		}
		return listConcept;
	}
	
	public static Encounter createEncounter(Patient patient, boolean revisit) {
		EncounterType encounterType = Context.getEncounterService().getEncounterType("TRIAGEENCOUNTER");
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
	
	public static void sendPatientToOPDQueue(Patient patient, Concept selectedOPDConcept, boolean revisit,Encounter registrationEncounter,Encounter triageEncounter,TriagePatientData triagePatientData) {
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
			queue.setPatientName(patient.getGivenName() + " " + patient.getFamilyName());
			queue.setReferralConcept(referralConcept);
			queue.setReferralConceptName(referralConcept.getName().getName());
			queue.setSex(patient.getGender());
			queue.setRegistrationEncounter(registrationEncounter);
			queue.setTriageEncounter(triageEncounter);
			queue.setTriageDataId(triagePatientData);
			PatientQueueService queueService = Context.getService(PatientQueueService.class);
			queueService.saveOpdPatientQueue(queue);
			
		}
	}

}
