/**
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
 **/

package org.openmrs.module.triage.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.triage.TriageService;
import org.openmrs.module.triage.db.TriageDAO;

/**
 * It is a default implementation of {@link TriageService}.
 */
public class TriageServiceImpl extends BaseOpenmrsService implements TriageService {

	protected final Log log = LogFactory.getLog(this.getClass());

	private TriageDAO dao;

	/**
	 * @param dao
	 *            the dao to set
	 */
	public void setDao(TriageDAO dao) {
		this.dao = dao;
	}

	/**
	 * @return the dao
	 */
	public TriageDAO getDao() {
		return dao;
	}
}