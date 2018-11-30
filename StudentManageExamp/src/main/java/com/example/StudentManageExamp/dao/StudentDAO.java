package com.example.StudentManageExamp.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.StudentManageExamp.entity.Student;


@Repository
@Transactional(rollbackFor = Exception.class)
public class StudentDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public List<Student> findAll(){
        String hql = "from Student";
        Session session = this.sessionFactory.getCurrentSession();
        @SuppressWarnings("unchecked")
		Query<Student> query = session.createQuery(hql);
        List<Student> list = query.list();
        return list;
    }

	public Student findOne(Integer id) {
		Session session = this.sessionFactory.getCurrentSession();
		Student student = session.get(Student.class, id);
		return student;
	}

	public void deleteStudent(Integer id) {
		Session session = this.sessionFactory.getCurrentSession();
		Student student = session.get(Student.class, id);
		session.delete(student);
	}

	public void updateStudent(Student student) {
		Session session = this.sessionFactory.getCurrentSession();
		session.merge(student);
	}

	public void insertStudent(Student student) {
		Session session = this.sessionFactory.getCurrentSession();
		session.save(student);
	}

	public List<Student> filter(String name) {
		Session session = this.sessionFactory.getCurrentSession();
		String sql =
				"From Student " +
				"Where name LIKE :search";
		@SuppressWarnings("unchecked")
		Query<Student> query = session.createQuery(sql);
		query.setParameter("search", "%"+ name +"%");
		List<Student> list = query.list();
		return list;
	}

	public PagedListHolder<?> findByName(String name){
		Session session = this.sessionFactory.getCurrentSession();
		String hql =
				"FROM Student s " +
				"WHERE (:name is NULL OR s.name LIKE :name) ORDER BY s.id";
		@SuppressWarnings("unchecked")
		Query<Student> query = session.createQuery(hql);
		query.setParameter("name", name);
		PagedListHolder<?> pages = new PagedListHolder<>(query.list());
		return pages;
	}
}
