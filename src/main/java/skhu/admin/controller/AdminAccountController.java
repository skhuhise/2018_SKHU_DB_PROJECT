package skhu.admin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import skhu.dto.Admin;
import skhu.dto.Department;
import skhu.mapper.AdminMapper;
import skhu.mapper.DepartmentMapper;
import skhu.vo.Page;

@Controller
@RequestMapping("admin/menu/account")
public class AdminAccountController {
	@Autowired AdminMapper adminMapper;
	@Autowired DepartmentMapper departmentMapper;

	@RequestMapping(value="adminList", method=RequestMethod.GET)
	public String adminList(Model model, Admin condition,
			@RequestParam(value="searchText", required=false) String searchText,
			@RequestParam(value="searchType", required=false) String searchType,
			HttpServletRequest request, @RequestParam(value="pg", required=false) String pg) {

		if(searchText == null)
			searchText = "";

		if(searchType == null)
			searchType = "0";

		Page page = new Page();
		int total = adminMapper.count(condition, searchType, searchText);
		int currentPage = 1;

		if(pg != null)
			currentPage = Integer.parseInt(pg);

		List<Department> departments = departmentMapper.findAll();
		List<Admin> admins = adminMapper.findAllWithDepartment((currentPage - 1) * 15, 15, condition, searchType, "%" + searchText + "%");
		ArrayList<Page> pages = page.paging(total, 15, currentPage, request.getQueryString());

		model.addAttribute("condition", condition);
		model.addAttribute("departments", departments);
		model.addAttribute("admins", admins);
		model.addAttribute("searchText", searchText);
		model.addAttribute("searchType", searchType);
		model.addAttribute("pages", pages);

		return "admin/menu/account/adminList";
	}

	@RequestMapping(value="adminEdit", method=RequestMethod.GET)
	public String adminEdit(Model model, @RequestParam("id") int id) {
		Admin admin = adminMapper.findById(id);
		List<Department> departments = departmentMapper.findAll();

		model.addAttribute("admin", admin);
		model.addAttribute("departments", departments);

		return "admin/menu/account/adminEdit";
	}

	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public String edit(Model model, Admin admin) {

		adminMapper.update(admin);

		return "redirect:adminList";
	}

	@RequestMapping("delete")
	public String delete(Model model, @RequestParam("id") int id) {
		adminMapper.delete(id);
		return "redirect:adminList";
	}

	@RequestMapping(value="acntchange", method=RequestMethod.GET)
	public String acntchange(HttpSession session, Model model) {
		List<Department> departments = departmentMapper.findAll();

		model.addAttribute("admin", session.getAttribute("adminInfo"));
		model.addAttribute("departments", departments);

		return "admin/menu/account/acntchange";
	}

	@RequestMapping(value="acntupdate", method=RequestMethod.POST)
	public String anctupdate(HttpSession session, Model model, Admin account) {
		adminMapper.update(account);
		account.setPassword(null);
		session.removeAttribute("adminInfo");
		session.setAttribute("adminInfo", account);
		session.setMaxInactiveInterval(5400);

		return "redirect:../main";
	}

	@RequestMapping(value="pwdconfirm", method=RequestMethod.GET)
	public String pwdConfirm(Model model) {
		Admin confirm = new Admin();

		model.addAttribute("confirm", confirm);
		return "admin/menu/account/pwdconfirm";
	}

	@RequestMapping(value="pwdchange", method=RequestMethod.POST)
	public String pwdchange(HttpSession session, Model model, Admin confirm) {
		int id = ((Admin)session.getAttribute("adminInfo")).getId();
		Admin account = adminMapper.findById(id);
		if(confirm != null && account != null && confirm.getPassword().equals(account.getPassword())) {
			model.addAttribute("account", account);

			return "admin/menu/account/pwdchange";
		}

		String message = "비밀번호가 일치하지 않습니다!";
	    String location = "pwdconfirm";

	    model.addAttribute("message", message);
	    model.addAttribute("location", location);

	    return "user/error/error";
	}

	@RequestMapping(value="pwdupdate", method=RequestMethod.POST)
	public String pwdupdate(HttpSession session, Model model, Admin account, @RequestParam("passwordConfirm") String passwordConfirm) {
		if(passwordConfirm != null && !passwordConfirm.equals("") && account.getPassword().equals(passwordConfirm)) {
			adminMapper.update(account);
			return "redirect:../main";
		}

		String message = "일치하지 않습니다!";
	    String location = "pwdconfirm";

	    model.addAttribute("message", message);
	    model.addAttribute("location", location);

	    return "user/error/error";
	}

	@RequestMapping(value="addadmin", method=RequestMethod.GET)
	public String addadmin(Model model) {
		Admin admin = new Admin();
		List<Department> departments = departmentMapper.findAll();

		model.addAttribute("admin", admin);
		model.addAttribute("departments", departments);

		return "admin/menu/account/addadmin";
	}

	@RequestMapping(value="insert", method=RequestMethod.POST)
	public String insert(Model model, Admin admin, @RequestParam("passwordConfirm") String passwordConfirm) {
		if((admin.getEmail().length() != 0 && !admin.getEmail().equals("")) &&
		(admin.getLoginId().length() != 0 && !admin.getLoginId().equals("")) &&
		(admin.getName().length() != 0 && !admin.getName().equals("")) &&
		(admin.getPassword().length() != 0 && !admin.getPassword().equals("")) &&
		admin.getPassword().equals(passwordConfirm)) {
			adminMapper.insert(admin);

			return "redirect:adminList";
		}

		String message = "생성에 실패하였습니다!";
	    String location = "../main";

	    model.addAttribute("message", message);
	    model.addAttribute("location", location);

	    return "user/error/error";
	}
}
