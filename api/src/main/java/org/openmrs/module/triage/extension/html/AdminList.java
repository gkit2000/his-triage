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

package org.openmrs.module.triage.extension.html;

import java.util.HashMap;
import java.util.Map;

import org.openmrs.module.Extension;
import org.openmrs.module.web.extension.AdministrationSectionExt;

/**
 * This class defines the links that will appear on the administration page under the
 * "basicmodule.title" heading. This extension is enabled by defining (uncommenting) it in the
 * /metadata/config.xml file.
 */
public class AdminList extends AdministrationSectionExt {
	
	/**
	 * @see org.openmrs.module.web.extension.AdministrationSectionExt#getMediaType()
	 */
	public Extension.MEDIA_TYPE getMediaType() {
		return Extension.MEDIA_TYPE.html;
	}
	
	/**
	 * @see org.openmrs.module.web.extension.AdministrationSectionExt#getTitle()
	 */
	public String getTitle() {
		return "triage.title";
	}
	
	/**
	 * @see org.openmrs.module.web.extension.AdministrationSectionExt#getLinks()
	 */
	public Map<String, String> getLinks() {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("module/triage/chooseTriage.htm", "triage.title");
		return map;
	}
	
}
