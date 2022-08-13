package com.favshare.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.favshare.dto.YoutubeDetailDto;
import com.favshare.dto.YoutubeUserIdDto;
import com.favshare.service.YoutubeService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/youtube")
public class YoutubeController {

	@Autowired
	private YoutubeService youtubeService;

	@ApiOperation(value = "사용자에게 맞는 유튜브 리스트", response = List.class)
	@GetMapping("/{userId}")
	public ResponseEntity<List<HashMap<String, Object>>> showYoutubeList(@PathVariable("userId") int userId) {
		// 로그인 안한 경우에는 userId 값이 0으로 넘어온다.
		try {
			boolean hasInterestIdol = youtubeService.hasInterestIdol(userId);
			List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> urlMap = new HashMap<String, Object>();

			List<String> urlList;

			if (userId == 0 || !hasInterestIdol) {
				urlList = youtubeService.getAlgoUrlByNoId(userId);
			} else {

				urlList = youtubeService.getAlgoUrlByUserId(userId);
			}

			for (int i = 0; i < urlList.size(); i++) {
				urlMap = new HashMap<String, Object>();
				urlMap.put("youtubeId", urlList.get(i));
				result.add(urlMap);
			}

			return new ResponseEntity<List<HashMap<String, Object>>>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<HashMap<String, Object>>>(HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "유튜브 관련 정보", response = YoutubeDetailDto.class)
	@PostMapping("/detail")
	public ResponseEntity<YoutubeDetailDto> showYoutubeDetil(@RequestBody YoutubeUserIdDto youtubeUserIdDto) {
		try {
			YoutubeDetailDto youtubeDetailDto = youtubeService.getDetailByUrl(youtubeUserIdDto);
			return new ResponseEntity<YoutubeDetailDto>(youtubeDetailDto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<YoutubeDetailDto>(HttpStatus.BAD_REQUEST);
		}

	}

}
