package com.example.StudentManageExamp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.StudentManageExamp.dao.StudentDAO;
import com.example.StudentManageExamp.entity.Student;

@Service
@Transactional
public class StudentService {
	@Autowired StudentDAO studentDAO;
	public List<Student> findAll(){
		return studentDAO.findAll();
	}

	public Student findOne(Integer id) {
		return studentDAO.findOne(id);
	}

	public void delete(Integer id) {
		studentDAO.deleteStudent(id);
	}

	public void update(Student student) {
		studentDAO.updateStudent(student);
	}

	public void delete(Student student) {
		studentDAO.updateStudent(student);
	}

	public void insert(Student student) {
		studentDAO.insertStudent(student);
	}

	public PagedListHolder<?> findByName(String name){
		if(name != null)
			name = "%" + name + "%";
		return studentDAO.findByName(name);
	}
}
