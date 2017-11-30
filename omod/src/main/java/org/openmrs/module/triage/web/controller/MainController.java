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

package org.openmrs.module.triage.web.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.util.ConceptAnswerComparator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("TriageQueueMainController")
@RequestMapping("/module/triage/main.htm")
public class MainController {
	
	@RequestMapping(method=RequestMethod.GET)
	public String firstView(@RequestParam(value="triageId",required=false) Integer triageId, Model model, HttpSession session){
				Concept triageConcept = Context.getConceptService().getConceptByName("TRIAGE");
				List<ConceptAnswer> list = (triageConcept!= null ?  new ArrayList<ConceptAnswer>(triageConcept.getAnswers()) : null);
				if(CollectionUtils.isNotEmpty(list)){
					Collections.sort(list, new ConceptAnswerComparator());
				}
				model.addAttribute("listTriage",list);
				
				if( triageId == null ){
					triageId =  (Integer) session.getAttribute("triageId");
				}else{
					session.setAttribute("triageId", triageId);
				}
				model.addAttribute("triageId", triageId);
			return "module/triage/mainTriage";	
	}
}
