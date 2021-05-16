package org.mercosur.fondoPrevision.controller;


import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.mercosur.fondoPrevision.dto.ChangePasswordForm;
import org.mercosur.fondoPrevision.entities.User;
import org.mercosur.fondoPrevision.exceptions.UserNotFoundException;
import org.mercosur.fondoPrevision.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import net.bytebuddy.utility.RandomString;

@Controller
public class ForgotPasswordController {

	@Autowired
	UserService userService;

	@Autowired
	private JavaMailSender mailSender;
	
	@GetMapping("/forgotPassword")
	public String showForgotPasswordForm(Model model) {
	
		model.addAttribute("pageTitle", "Resetear Contraseña");
		return "user-form/forgot-password";
	}
	
	@PostMapping("/forgotPassword")
	public String processForgotPasswordForm(HttpServletRequest request, Model model) {
		
		String email = request.getParameter("email");
		String token = RandomString.make(45);
		
		try {
			userService.updateResetPasswordToken(token, email);
			String resetPasswordLink = FuncionesUtiles.getSiteURL(request) + "/reset_password?token=" + token;
			
			sendEmail(email, resetPasswordLink);
			model.addAttribute("formMessage", "Enviamos el link para cambio de contraseña a su email. Por favor chequeelo");
		}
		catch(UserNotFoundException unfe) {
			model.addAttribute("formError", unfe.getMessage());
		}
		catch(UnsupportedEncodingException | MessagingException e) {
			model.addAttribute("formError", "Error al enviar el mail");
		}
		return "user-form/forgot-password";
	}

	private void sendEmail(String email, String resetPasswordLink) throws UnsupportedEncodingException, MessagingException  {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		
		helper.setFrom("fondoprevision@mercosur.int", "Soporte - Fondo Previsión");
		helper.setTo(email);
		
		String subject = "Link para redefinir password";
		String content = "<p>Hola, </p>" +
		"<p>Si ud. solicitó cambiar su contraseña, haga click sobre el link abajo para continuar con el procedimiento.</p>" +
		"<p><b><a href=\""+ resetPasswordLink + "\">Cambiar contraseña</a></b></p>";
		
		helper.setSubject(subject);
		helper.setText(content, true);
		mailSender.send(message);
	}
	
	@GetMapping("/reset_password")
	public String showResetPasswordForm(@Param(value="token") String token, Model model) {
		
		User user = userService.get(token);
		if(user == null) {
			model.addAttribute("message", "Token inválido");
			return "error";
		}
		model.addAttribute("passwordForm",new ChangePasswordForm(user.getId()));		
		model.addAttribute("token", token);
		return "user-form/reset-password";
	}
	
	@PostMapping(value = "/reset_password")
	public String processResetPassword(HttpServletRequest request, Model model) {
		String token = request.getParameter("token");
		String newPassword = request.getParameter("newPassword");
		String confirmPassword = request.getParameter("confirmPassword");
		
		User user = userService.get(token);
		if(user == null) {
			model.addAttribute("formError", "Token inválido");
			model.addAttribute("passwordForm",new ChangePasswordForm());		
			return "user-form/reset-password";
		}
		if(!newPassword.equals(confirmPassword)) {
			model.addAttribute("formError", "Las passwords ingresadas no coinciden!");
			model.addAttribute("passwordForm",new ChangePasswordForm(user.getId()));		
			return "user-form/reset-password";
		}
		else if(newPassword.isEmpty() || confirmPassword.isEmpty()) {
			model.addAttribute("formError", "La password no puede estar en blanco!");
			model.addAttribute("passwordForm",new ChangePasswordForm(user.getId()));		
			return "user-form/reset-password";
		}
		ChangePasswordForm passwordForm = new ChangePasswordForm(user.getId());
		passwordForm.setNewPassword(newPassword);
		passwordForm.setConfirmPassword(confirmPassword);
		try {
			user = userService.resetPassword(passwordForm);
			model.addAttribute("formMessage", "La password ha sido redefinida exitosamente!");
		}
		catch(Exception ex) {
			model.addAttribute("formError", ex.getMessage());
		}
		model.addAttribute("passwordForm",new ChangePasswordForm(user.getId()));		
		
		return "user-form/reset-password";
	}

	/*
	public ResponseEntity<String> processResetPassword(@Valid @RequestBody ChangePasswordForm form, Errors errors) {
		try {
			//If error, just return a 400 bad request, along with the error message
	        if (errors.hasErrors()) {
	            String result = errors.getAllErrors()
	                        .stream().map(x -> x.getDefaultMessage())
	                        .collect(Collectors.joining(""));

	            throw new Exception(result);
	        }
			userService.resetPassword(form);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok("Success");
	}	*/

}
