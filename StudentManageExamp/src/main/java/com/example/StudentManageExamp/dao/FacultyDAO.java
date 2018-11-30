package com.example.StudentManageExamp.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.StudentManageExamp.entity.Faculty;



@Repository
@Transactional(rollbackFor = Exception.class)
public class FacultyDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public List<Faculty> findAll(){
        String hql = "from Faculty";
        Session session = this.sessionFactory.getCurrentSession();
        @SuppressWarnings("unchecked")
		Query<Faculty> query = session.createQuery(hql);
        List<Faculty> list = query.list();
        return list;
    }

	public Faculty findOne(Integer id) {
		Session session = this.sessionFactory.getCurrentSession();
		Faculty faculty = session.get(Faculty.class, id);
		return faculty;
	}

}
