package org.openmrs.module.triage.util;

public class TriageUtil {
	public static String calcAgeClass(int patientAge) {
		if (patientAge >= 0 && patientAge <= 12)
			return "Child";
		else if (patientAge > 12 && patientAge <= 19)
			return "Adolescent";
		else if (patientAge > 19 && patientAge <= 59)
			return "Adult";
		else
			return "Senior Citizen";
	}
}
