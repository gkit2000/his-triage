/**
 *  Copyright 2010 Society for Health Information Systems Programmes, India (HISP India)
 *
 *  This file is part of Patient-queue module.
 *
 *  Patient-queue module is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.

 *  Patient-queue module is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Patient-queue module.  If not, see <http://www.gnu.org/licenses/>.
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

@Controller("TriageMainController")
@RequestMapping("/module/triage/main.htm")
public class TriageMainController {
	
	@RequestMapping(method=RequestMethod.GET)
	public String firstView(@RequestParam(value="opdId",required=false) Integer opdId, Model model, HttpSession session){
		System.out.println("ppppppppppppppppppppppp---------------");		
		Concept triageConcept = Context.getConceptService().getConceptByName("TRIAGE");
				List<ConceptAnswer> list = (triageConcept!= null ?  new ArrayList<ConceptAnswer>(triageConcept.getAnswers()) : null);
				if(CollectionUtils.isNotEmpty(list)){
					Collections.sort(list, new ConceptAnswerComparator());
				}
				model.addAttribute("listOPD",list);
				
				if( opdId == null ){
					opdId =  (Integer) session.getAttribute("opdRoomId");
				}else{
					session.setAttribute("opdRoomId", opdId);
				}
				model.addAttribute("opdId", opdId);
				System.out.println("xxxxxxxxxxxxxxxxxx");
			return "module/triage/page/mainTriage";	
	}
}
