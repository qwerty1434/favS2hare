package com.favshare.controller;

import java.util.*;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.favshare.dto.EmailPasswordDto;
import com.favshare.dto.UserAccountDto;
import com.favshare.dto.UserSignUpDto;
import com.favshare.service.UserService;
import com.favshare.service.JwtServiceImpl;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/user")
public class UserController {
	
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	private JwtServiceImpl jwtService;
	
	@Autowired
	private UserService userService;
	
	@ApiOperation(value="로그인",response=ResponseEntity.class)
	//get방식은 안됨 pathvariable만 가능한듯?
	@PostMapping("/login")
	public ResponseEntity userLogin(@RequestBody EmailPasswordDto emailPasswordDto) {
        UserAccountDto result = userService.getByEmail(emailPasswordDto.getEmail());
	public ResponseEntity<Map<String, Object>> userLogin(@RequestBody HashMap<String, String> userInfo) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			UserAccountDto result = userService.getByEmail(userInfo.get("email"));
			if(userInfo.get("email").equals(result.getEmail())&&
					userInfo.get("password").equals(result.getPassword())) {
				String token = jwtService.create("userId", result.getId(), "access-token");// key, data, subject
				resultMap.put("access-token", token);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			}else {        	
				resultMap.put("message", FAIL);
				status = HttpStatus.ACCEPTED;
			}
			
		}catch (Exception e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
        // 보안 강화 필요
        // 반환값 필요한지 확인
        if(emailPasswordDto.getEmail().equals(result.getEmail())&&
        		emailPasswordDto.getPassword().equals(result.getPassword())) {
        	return new ResponseEntity(HttpStatus.OK);
        }else {        	
        	return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

	}
	
	@ApiOperation(value="회원가입",response=ResponseEntity.class)	
	@PostMapping("/signup")	
	public ResponseEntity userSignUp(@RequestBody UserSignUpDto userSignUpDto) {
		try {			
			userService.insertUser(userSignUpDto);
			return new ResponseEntity(HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
	}
	

	

}
