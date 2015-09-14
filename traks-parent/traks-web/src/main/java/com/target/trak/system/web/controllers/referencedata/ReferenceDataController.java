package com.target.trak.system.web.controllers.referencedata;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.target.trak.system.service.ReferenceDataService;
import com.target.trak.system.service.dto.common.TextSearchCriteriaDto;
import com.target.trak.system.service.dto.referencedata.ReferenceDataApiRequest;
import com.target.trak.system.service.dto.referencedata.ReferenceDataApiResponse;
import com.target.trak.system.service.dto.referencedata.ReferenceDataDto;
import com.target.trak.system.service.util.DateUtil;
import com.target.trak.system.web.ui.ReferenceDataItem;
import com.target.trak.system.web.ui.helpers.ReferenceDataHelper;

@Controller
public class ReferenceDataController {

	private final static Logger logger = Logger.getLogger(ReferenceDataController.class);

	private ReferenceDataService referenceDataService;

	private ReferenceDataHelper referenceDataHelper;

	@RequestMapping(value = "/referencedata/createReferenceData.json", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> createReferenceData(@RequestParam String type, @RequestParam String label, @RequestParam String value, @RequestParam String status) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		ReferenceDataApiRequest request = new ReferenceDataApiRequest();
		ReferenceDataDto dto = new ReferenceDataDto();
		dto.setType(type);
		dto.setLabel(label);
		dto.setValue(value);
		dto.setStatus(status);
		dto.setCreatedBy("admin");
		Calendar calendar = Calendar.getInstance();
		dto.setCreatedDateTime(calendar);
		dto.setLastUpdatedBy("admin");
		dto.setLastUpdatedDateTime(calendar);
		request.setReferenceDataDto(dto);

		ReferenceDataApiResponse response = referenceDataService.createReferenceData(request);
		responseMap.put("data", referenceDataHelper.convertReferenceDataItem(response.getReferenceData()));
		responseMap.put("success", response.isSuccess());
		logger.info("Successful creation of reference data entity by user.");
		responseMap.put("message", response.getMessage());
		return responseMap;
	}

	@RequestMapping(value = "/referencedata/updateReferenceData.json", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> updateReferenceData(@RequestParam long id, @RequestParam String type, @RequestParam String label, @RequestParam String value, @RequestParam String status, @RequestParam int version) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		ReferenceDataApiRequest request = new ReferenceDataApiRequest();
		request.setReferenceDataDto(buildReferenceDataDto(id, type, label, value, status, version));

		ReferenceDataApiResponse response = referenceDataService.updateReferenceDataItem(request);
		responseMap.put("success", response.isSuccess());
		logger.info("Successful update of reference data entity [" + id + "] by user.");
		responseMap.put("message", response.getMessage());
		return responseMap;
	}

	@RequestMapping(value = "/referencedata/getReferenceDataItem.json", method = RequestMethod.GET)
	public @ResponseBody
	Map<String, Object> getReferenceDataItem(@RequestParam Long id) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		ReferenceDataApiRequest request = new ReferenceDataApiRequest();
		ReferenceDataDto dto = new ReferenceDataDto();
		dto.setId(id);
		request.setReferenceDataDto(dto);

		ReferenceDataApiResponse response = referenceDataService.getReferenceDataItemById(request);
		responseMap.put("success", response.isSuccess());
		responseMap.put("data", referenceDataHelper.convertReferenceDataItem(response.getReferenceData()));
		return responseMap;
	}

	@RequestMapping(value = "/referencedata/getReferenceDataByType.json", method = RequestMethod.GET)
	public @ResponseBody
	Map<String, Object> getReferenceDataByType(@RequestParam String type) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		ReferenceDataApiRequest request = new ReferenceDataApiRequest();
		ReferenceDataDto dto = new ReferenceDataDto();
		dto.setType(type);
		request.setReferenceDataDto(dto);
		ReferenceDataApiResponse response = referenceDataService.getReferenceDataByType(request);
		if (response.isSuccess()) {
			responseMap.put("data", referenceDataHelper.buildReferenceDataList(response.getReferenceDataList()));
		} else {
			responseMap.put("message", response.getMessage());
		}

		return responseMap;
	}

	@RequestMapping(value = "/referencedata/getReferenceData.json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Map<String, Object> getReferenceData(@RequestParam int start, @RequestParam int page, @RequestParam int limit) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		ReferenceDataApiRequest request = new ReferenceDataApiRequest();
		TextSearchCriteriaDto searchCriteria = new TextSearchCriteriaDto();
		searchCriteria.setPage(page);
		searchCriteria.setStart(start);
		searchCriteria.setEnd(limit);
		request.setTextSearchCriteria(searchCriteria);

		ReferenceDataApiResponse response = referenceDataService.getReferenceDataByCriteria(request);
		if (response.isSuccess()) {
			responseMap.put("data", buildReferenceDataItems(response.getReferenceDataList()));
			responseMap.put("totalSize", response.getTotalSize());
		} else {
			responseMap.put("message", response.getMessage());
		}
		responseMap.put("success", response.isSuccess());
		return responseMap;
	}

	@RequestMapping(value = "/referencedata/searchReferenceData.json", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> searchReferenceData(@RequestParam String text, @RequestParam int start, @RequestParam int page, @RequestParam int limit) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		ReferenceDataApiRequest request = new ReferenceDataApiRequest();
		TextSearchCriteriaDto searchCriteria = new TextSearchCriteriaDto();
		searchCriteria.setText(text);
		searchCriteria.setPage(page);
		searchCriteria.setStart(start);
		searchCriteria.setEnd(limit);
		request.setTextSearchCriteria(searchCriteria);

		ReferenceDataApiResponse response = referenceDataService.getReferenceDataByCriteria(request);
		if (response.isSuccess()) {
			responseMap.put("data", buildReferenceDataItems(response.getReferenceDataList()));
			responseMap.put("totalSize", response.getTotalSize());
		} else {
			responseMap.put("message", response.getMessage());
		}
		responseMap.put("success", response.isSuccess());
		return responseMap;
	}

	private ReferenceDataDto buildReferenceDataDto(long id, String type, String label, String value, String status, int version) {
		ReferenceDataDto dto = new ReferenceDataDto();
		dto.setId(id);
		dto.setType(type);
		dto.setLabel(label);
		dto.setValue(value);
		dto.setStatus(status);
		dto.setVersion(version);
		dto.setLastUpdatedBy("anonymous user");
		dto.setLastUpdatedDateTime(Calendar.getInstance());
		return dto;
	}

	private List<ReferenceDataItem> buildReferenceDataItems(List<ReferenceDataDto> referenceDataList) {
		List<ReferenceDataItem> referenceDataItems = new ArrayList<ReferenceDataItem>();
		if (referenceDataList != null && !referenceDataList.isEmpty()) {
			ReferenceDataItem item = null;
			for (ReferenceDataDto referenceData : referenceDataList) {
				item = new ReferenceDataItem();
				item.setId(referenceData.getId());
				item.setType(referenceData.getType());
				item.setLabel(referenceData.getLabel());
				item.setValue(referenceData.getValue());
				item.setStatus(referenceData.getStatus());
				item.setCreatedBy(referenceData.getCreatedBy());
				item.setCreatedDateTime(DateUtil.convertDateToIso8601(referenceData.getCreatedDateTime()));
				item.setLastUpdatedBy(referenceData.getLastUpdatedBy());
				item.setLastUpdatedDateTime(DateUtil.convertDateToIso8601(referenceData.getLastUpdatedDateTime()));
				item.setVersion(referenceData.getVersion());
				referenceDataItems.add(item);
			}
		}
		return referenceDataItems;
	}

	public void setReferenceDataService(ReferenceDataService referenceDataService) {
		this.referenceDataService = referenceDataService;
	}

	public void setReferenceDataHelper(ReferenceDataHelper referenceDataHelper) {
		this.referenceDataHelper = referenceDataHelper;
	}
}