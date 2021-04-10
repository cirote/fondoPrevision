package org.mercosur.fondoPrevision.controller;

import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.mercosur.fondoPrevision.dto.ProcedimientoDto;
import org.mercosur.fondoPrevision.service.RunBatFileService;

@RestController
public class BackupRestoreRestController {

	@Autowired
	RunBatFileService runBatFileService;
	
	@PostMapping("/api/fondo/backup")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	ResponseEntity<String> postBackup(@Valid @RequestBody String param, Errors errors) {
		try {
			//If error, just return a 400 bad request, along with the error message
	        if (errors.hasErrors()) {
	            String result = errors.getAllErrors()
	                        .stream().map(x -> x.getDefaultMessage())
	                        .collect(Collectors.joining(""));

	            throw new Exception(result);
	        }

	        String[] jsonvector = param.split(":");
	        
	        String dos = jsonvector[1];
	        dos = dos.replaceAll("\"", "");
	        dos = dos.replaceAll("[{}]", "");
	        
			String res = runBatFileService.ejecutarBatOrSSH("resp" + dos);
			if(!res.contains("exitosa")) {
				return ResponseEntity.badRequest().body("No se completó la ejecución del procedimiento");
			}

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok("Success");		
	}

	@PostMapping("/api/fondo/restore")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	ResponseEntity<String> postRestore(@Valid @RequestBody String param, Errors errors) {
		try {
			//If error, just return a 400 bad request, along with the error message
	        if (errors.hasErrors()) {
	            String result = errors.getAllErrors()
	                        .stream().map(x -> x.getDefaultMessage())
	                        .collect(Collectors.joining(""));

	            throw new Exception(result);
	        }

	        String[] jsonvector = param.split(":");
	        String dos = jsonvector[1];
	        dos = dos.replaceAll("\"", "");
	        dos = dos.replaceAll("[{}]", "");
	        
			String res=runBatFileService.ejecutarBatOrSSH("rest" + dos);
			if(!res.contains("exitosa")) {
				return ResponseEntity.badRequest().body("No se completó la ejecución del procedimiento");
			}

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok("Success");		
	}

	@PostMapping("/api/fondo/backup/intermedio")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	ResponseEntity<String> postBackupIntermedio(@Valid @RequestBody ProcedimientoDto param, Errors errors) {
		try {
			//If error, just return a 400 bad request, along with the error message
	        if (errors.hasErrors()) {
	            String result = errors.getAllErrors()
	                        .stream().map(x -> x.getDefaultMessage())
	                        .collect(Collectors.joining(""));

	            throw new Exception(result);
	        }
	        
	        String proc = param.getNombreproc();
	        
	        String parametro = param.getIdproc().toString();
	        
			String res = runBatFileService.ejecutarBatOrSSH("resp" + proc, parametro);
			if(!res.contains("exitosa")) {
				return ResponseEntity.badRequest().body("No se completó la ejecución del procedimiento");
			}
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok("Success");		
	}

	@PostMapping("/api/fondo/restore/intermedio")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	ResponseEntity<String> postRestoreIntermedio(@Valid @RequestBody ProcedimientoDto param, Errors errors) {
		try {
			//If error, just return a 400 bad request, along with the error message
	        if (errors.hasErrors()) {
	            String result = errors.getAllErrors()
	                        .stream().map(x -> x.getDefaultMessage())
	                        .collect(Collectors.joining(""));

	            throw new Exception(result);
	        }

	        String proc = param.getNombreproc();	        
	        String parametro = param.getIdproc().toString();
	        
			String res = runBatFileService.ejecutarBatOrSSH("rest" + proc, parametro);
			if(!res.contains("exitosa")) {
				return ResponseEntity.badRequest().body("No se completó la ejecución del procedimiento");
			}
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok("Success");		
	}

	@PostMapping("/procesarSolicitud/api/fondo/backup/intermedio")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	ResponseEntity<String> postBkupIntermedioSol(@Valid @RequestBody ProcedimientoDto param, Errors errors) {
		try {
			//If error, just return a 400 bad request, along with the error message
	        if (errors.hasErrors()) {
	            String result = errors.getAllErrors()
	                        .stream().map(x -> x.getDefaultMessage())
	                        .collect(Collectors.joining(""));

	            throw new Exception(result);
	        }
	        
	        String proc = param.getNombreproc();
	        
	        String parametro = param.getIdproc().toString();
	        
			String res = runBatFileService.ejecutarBatOrSSH("resp" + proc, parametro);
			if(!res.contains("exitosa")) {
				return ResponseEntity.badRequest().body("No se completó la ejecución del procedimiento");
			}
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok("Success");		
	}

	@PostMapping("/procesarSolicitud/api/fondo/restore/intermedio")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	ResponseEntity<String> postRestIntermedioSol(@Valid @RequestBody ProcedimientoDto param, Errors errors) {
		try {
			//If error, just return a 400 bad request, along with the error message
	        if (errors.hasErrors()) {
	            String result = errors.getAllErrors()
	                        .stream().map(x -> x.getDefaultMessage())
	                        .collect(Collectors.joining(""));

	            throw new Exception(result);
	        }

	        String proc = param.getNombreproc();	        
	        String parametro = param.getIdproc().toString();
	        
			String res = runBatFileService.ejecutarBatOrSSH("rest" + proc, parametro);
			if(!res.contains("exitosa")) {
				return ResponseEntity.badRequest().body("No se completó la ejecución del procedimiento");
			}
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok("Success");		
	}

}
