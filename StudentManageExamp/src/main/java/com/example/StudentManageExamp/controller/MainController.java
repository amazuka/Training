package com.example.StudentManageExamp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.StudentManageExamp.dao.FacultyDAO;
import com.example.StudentManageExamp.entity.Faculty;
import com.example.StudentManageExamp.entity.Student;
import com.example.StudentManageExamp.service.StudentService;


@Controller
public class MainController {

	@Autowired
	private StudentService studentService;
	@Autowired
	private FacultyDAO facultyDAO;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String homePage() {
		return "redirect:/student";
	}

	@RequestMapping(value = "/student", method = RequestMethod.GET)
	public String showStudents(ModelMap model,RedirectAttributes redirect, HttpServletRequest request) {
		if(model.get("success") != null)
			redirect.addFlashAttribute("success",model.get("success").toString());
		PagedListHolder<?> pages = studentService.findByName(null);
		addPaginator(model, 1, pages);
		return "list";
	}

	@RequestMapping(value = "/student/search")
	public String filterName(@RequestParam("name") String name, @RequestParam("current") int currentPage,
			ModelMap model) {
		PagedListHolder<?> pages = studentService.findByName(name);
		addPaginator(model, currentPage, pages);

		return "list";
	}

	@RequestMapping(value = "student/{id}/edit", method = RequestMethod.GET)
	public String edit(@PathVariable Integer id, ModelMap model) {
		model.addAttribute("faculties", facultyDAO.findAll());
		model.addAttribute("student", studentService.findOne(id));
		return "form";
	}

	@RequestMapping(value = "student/{id}/delete", method = RequestMethod.GET)
	public String delete(@PathVariable Integer id, HttpServletRequest request) {
		studentService.delete(id);
		request.getSession().setAttribute("studentlist", new PagedListHolder<>(studentService.findAll()));
		return "redirect:/student";
	}

	@RequestMapping(value = "/studentForm", params = "update", method = RequestMethod.POST)
	public String update(Student student, @RequestParam("faculty_id") Integer id,
			HttpServletRequest request) {
		Faculty faculty = facultyDAO.findOne(id);
		student.setFaculty(faculty);
		studentService.update(student);
		return "redirect:/student";
	}

	@RequestMapping(value = "/studentForm", params = "insert", method = RequestMethod.POST)
	public String insert(Student student, HttpServletRequest request) {
		studentService.insert(student);
		request.getSession().setAttribute("studentlist", new PagedListHolder<>(studentService.findAll()));
		return "redirect:/student";
	}

	private void addPaginator(ModelMap model, int currentPage, PagedListHolder<?> pages) {
		int goToPage = currentPage - 1;
		int totalPageCount = pages.getPageCount();

		if (goToPage <= pages.getPageCount() && goToPage >= 0) {
			pages.setPage(goToPage);
		}
		currentPage = pages.getPage() +1;
		model.addAttribute("currentIndex", currentPage);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("students", pages);
	}

}
