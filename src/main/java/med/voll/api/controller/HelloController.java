package med.voll.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello") //indica a qual URL o controller vai responder
public class HelloController {

	@GetMapping //anotação que diz a qual método do protocolo HTTP esse método responde
	public String olaMundo() {
		return "Hello World Rebeca ";
	}
	
	
}
