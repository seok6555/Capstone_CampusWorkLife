package com.campusworklife.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.campusworklife.entity.EditHistory;
import com.campusworklife.entity.Workplace;
import com.campusworklife.repository.EditHistoryRepository;
import com.campusworklife.repository.WorkplaceRepository;

@Controller
@RequestMapping("admin")
public class PageAdminController {

	@Autowired
	private EditHistoryRepository editHistoryRepository;

	@Autowired
	private WorkplaceRepository workplaceRepository;

	@GetMapping("pageAdminPage")
	public String pageAdminPage(Model model) {
		// 모든 수정 이력을 최신 순으로 가져오기
		List<EditHistory> editHistories = editHistoryRepository.findAll();

		// 데이터를 Map 구조로 가공
		List<Map<String, Object>> historyViews = editHistories.stream().map(history -> {
			Map<String, Object> view = new HashMap<>();
			Workplace workplace = history.getWorkplace();
			view.put("workplaceName", workplace != null ? workplace.getOfficelist() : "알 수 없음");
			view.put("oldContent", getPreviousContent(history, editHistories)); // 기존 내용
			view.put("editHistory", history); // 수정된 이력 (EditHistory 객체 자체)
			return view;
		}).collect(Collectors.toList());

		model.addAttribute("historyViews", historyViews);
		return "admin/pageAdminPage";
	}

	@PostMapping("rollbackEdit")
	public String rollbackEdit(@RequestParam Integer historyId) {
		// EditHistory를 ID로 조회
		Optional<EditHistory> historyOptional = editHistoryRepository.findById(historyId);
		if (historyOptional.isPresent()) {
			EditHistory history = historyOptional.get();

			// EditHistory에서 workplace를 가져옴
			Workplace workplace = history.getWorkplace();
			if (workplace != null) {
				// workplace의 content를 EditHistory의 content로 업데이트
				workplace.setContent(history.getContent());
				workplaceRepository.save(workplace); // 변경된 내용 저장
			}

			// 해당 수정 이력 삭제
			editHistoryRepository.deleteById(historyId);
		}
		return "redirect:/admin/pageAdminPage"; // 페이지를 리다이렉트
	}

	private String getPreviousContent(EditHistory current, List<EditHistory> histories) {
		return histories.stream()
				.filter(history -> history.getWorkplace().getId().equals(current.getWorkplace().getId())
						&& history.getEdit_time().before(current.getEdit_time()))
				.map(EditHistory::getContent).reduce((first, second) -> second) // 가장 최근 내용
				.orElse(""); // 기존 내용이 없으면 빈 문자열
	}
}
