package com.campusworklife.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.campusworklife.entity.Workplace;
import com.campusworklife.repository.WorkplaceRepository;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("info")
public class InfoController {
	@Autowired WorkplaceRepository workplaceRepository;
	ModelMapper modelMapper = new ModelMapper();
	
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
		return "info/infoPage";
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
