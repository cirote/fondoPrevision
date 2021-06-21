package org.mercosur.fondoPrevision.controller;

import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.mercosur.fondoPrevision.dto.ChangePasswordForm;
import org.mercosur.fondoPrevision.service.UserService;

@RestController
public class UserRestController {
	@Autowired
	UserService userService;
	
	@PostMapping(value = "/api/user/changePassword")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER', 'ROLE_SUPERVISOR')")
	public ResponseEntity<String> postEditUserChangePassword(@Valid @RequestBody ChangePasswordForm form, Errors errors) {
		try {
			//If error, just return a 400 bad request, along with the error message
	        if (errors.hasErrors()) {
	            String result = errors.getAllErrors()
	                        .stream().map(x -> x.getDefaultMessage())
	                        .collect(Collectors.joining(""));

	            throw new Exception(result);
	        }
			userService.changePassword(form);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok("Success");
	}

}
