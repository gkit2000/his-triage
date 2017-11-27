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

package org.openmrs.module.triage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.ModuleActivator;

/**
 * This class contains the logic that is run every time this module is either started or shutdown
 */
public class TriageQueueActivator implements ModuleActivator{
	
	private Log log = LogFactory.getLog(this.getClass());
	

	public void contextRefreshed() {
		// TODO Auto-generated method stub
		
	}
	public void started() {
		// TODO Auto-generated method stub
		log.info("Started Triage Module");
	}

	public void stopped() {
		// TODO Auto-generated method stub
		log.info("Stoped Triage Module");
	}
	public void willRefreshContext() {
		// TODO Auto-generated method stub
		
	}

	public void willStart() {
		// TODO Auto-generated method stub
		log.info("Starting Triage Module");
	}

	public void willStop() {
		// TODO Auto-generated method stub
		log.info("Shutting down Triage Module");
	}
	
}
