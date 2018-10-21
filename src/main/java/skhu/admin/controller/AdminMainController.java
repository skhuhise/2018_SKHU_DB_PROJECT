package skhu.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("admin/menu")
public class AdminMainController {

	@RequestMapping(value="main", method=RequestMethod.GET)
	public String login() {
		return "admin/menu/main";
	}
}
