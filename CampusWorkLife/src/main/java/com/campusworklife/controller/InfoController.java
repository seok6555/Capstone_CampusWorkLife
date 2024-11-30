package com.campusworklife.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.campusworklife.entity.EditHistory;
import com.campusworklife.entity.Workplace;
import com.campusworklife.repository.EditHistoryRepository;
import com.campusworklife.repository.WorkplaceRepository;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("info")
public class InfoController {
	@Autowired WorkplaceRepository workplaceRepository;
	@Autowired EditHistoryRepository editHistoryRepository;
	ModelMapper modelMapper = new ModelMapper();
	private boolean editMode = false;
	
	@GetMapping("infoPage")
	public String infoPage(Model model) {
		List<Workplace> workplaces = workplaceRepository.findAll();
		
		List<Map<String, Object>> workdays = workplaces.stream()
		        .map(workplace -> {
		            // 요일 이름과 상태를 동적으로 생성
		            Map<String, Object> result = new HashMap<>();
		            result.put("workDays", getTrueDays(workplace));
		            return result;
		        })
		        .collect(Collectors.toList());
		
		model.addAttribute("workplaces", workplaces);
		model.addAttribute("workdays", workdays);
		model.addAttribute("editMode", editMode);
		return "info/infoPage";
	}
	
//	@PostMapping("infoPage")
//	public String edit(Model model) {
//		try {
//			editMode = true;
//			model.addAttribute("editMode", editMode);
//			return "info/infoPage";
//			
//		} catch (Exception e) {
//			log.error("errorMsg", e.getMessage());
//			return "info/infoPage";
//		}
//	}
	
	@PostMapping("infoPage")
	public String save(@RequestParam String content) {
		try {
			List<Workplace> workplaces = workplaceRepository.findAll();
			Workplace workplace = new Workplace();
			EditHistory editHistory = new EditHistory();
			
			for (Workplace wp : workplaces) {
				workplace.setId(wp.getId());
				workplace.setManager(wp.getManager());
				workplace.setPhone(wp.getPhone());
				workplace.setWorkStart(wp.getWorkStart());
				workplace.setWorkEnd(wp.getWorkEnd());
				workplace.setTimeStart(wp.getTimeStart());
				workplace.setTimeEnd(wp.getTimeEnd());
				workplace.setDayMon(wp.getDayMon());
				workplace.setDayTue(wp.getDayTue());
				workplace.setDayWed(wp.getDayWed());
				workplace.setDayThu(wp.getDayThu());
				workplace.setDayFri(wp.getDayFri());
				workplace.setDaySat(wp.getDaySat());
				workplace.setDaySun(wp.getDaySun());
				workplace.setEmployeeCount(wp.getEmployeeCount());
				workplace.setDepartment(wp.getDepartment());
				workplace.setGrade(wp.getGrade());
				workplace.setSummary(wp.getSummary());
				workplace.setNotes(wp.getNotes());
				workplace.setOfficelist(wp.getOfficelist());
				workplace.setContent(content);
			}
			
			for (Workplace wp : workplaces) {
				editHistory.setUsername("수정자명");
				editHistory.setEdit_time(Timestamp.valueOf(LocalDateTime.now()));
				editHistory.setContent(content);
				editHistory.setWorkplace(wp);
				break;
			}
			
			Workplace saveWorkplace = workplaceRepository.save(workplace);
			EditHistory saveEditHistory = editHistoryRepository.save(editHistory);
			editMode = false;
            return "redirect:infoPage";

		} catch (Exception e) {
			log.error("errorMsg", e.getMessage());  // 상세 에러 로그
			return "info/infoPage";
		}
	}
	
	private List<String> getTrueDays(Workplace workplace) {
		List<String> activeDays = new ArrayList<>();
		if (workplace.getDayMon()) activeDays.add("월");
		if (workplace.getDayTue()) activeDays.add("화");
		if (workplace.getDayWed()) activeDays.add("수");
		if (workplace.getDayThu()) activeDays.add("목");
		if (workplace.getDayFri()) activeDays.add("금");
		if (workplace.getDaySat()) activeDays.add("토");
		if (workplace.getDaySun()) activeDays.add("일");
	    return activeDays;
	}
}
