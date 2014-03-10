package com.jrp.jdbcMain;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jrp.beans.Circle;
import com.jrp.dao.DaoImpl;

public class Client {
	private static ApplicationContext context = null;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		context = new ClassPathXmlApplicationContext("spring.xml");
		DaoImpl dao = (DaoImpl) context.getBean("daoImpl");
		
		Circle circle = dao.getCircle(1);
		System.out.println(circle.getName());
		System.out.println(dao.getCircleCount());
		System.out.println(dao.getCircleName(1));
		//System.out.println(dao.getCircleById(1).getName());
		
		dao.InsertCircle(new Circle(4, "Fourth circle"));
		System.out.println(dao.getAllCircles().size());
		//dao.createTriangleTable();
	}

}
