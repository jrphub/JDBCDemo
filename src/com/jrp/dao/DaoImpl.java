package com.jrp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.jrp.beans.Circle;

@Component
public class DaoImpl {
	
	@Autowired
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public DataSource getDataSource() {
		return dataSource;
	}

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public Circle getCircle(int circleId) {
		Connection con = null;
		Circle circle = null;
		try {
			//Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
			//con = DriverManager.getConnection("jdbc:derby://localhost:1527/db");
			
			//usage of Spring
			con = dataSource.getConnection();
			
			PreparedStatement pstmt = con
					.prepareStatement("select * from circle where id = ?");
			pstmt.setInt(1, circleId);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				circle = new Circle(circleId, rs.getString(2));
			}
			rs.close();
			pstmt.close();
			return circle;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return circle;
	}

	public int getCircleCount() {
		String sql = "select count(*) from circle";
		return getJdbcTemplate().queryForObject(sql, Integer.class);
	}
	
	public String getCircleName(int circleId) {
		String sql = "select name from circle where id =?";
		return getJdbcTemplate().queryForObject(sql, new Object[]{circleId}, String.class);
	}
	
	public Circle getCircleById(int circleId){
		String sql = "select * from  circle";
		return getJdbcTemplate().queryForObject(sql, new CircleMapper());
	}
	
	public List<Circle> getAllCircles() {
		String sql = "select * from circle";
		return getJdbcTemplate().query(sql, new CircleMapper());
	}
	
	private static final class CircleMapper implements RowMapper<Circle> {

		@Override
		public Circle mapRow(ResultSet res, int rowNum) throws SQLException {
			Circle circle = new Circle();
			circle.setId(res.getInt("ID"));
			circle.setName(res.getString("NAME"));
			return circle;
		}
	}
	
	public void InsertCircle(Circle circle) {
		String sql = "insert into Circle(id, name) values (?,?)";
		getJdbcTemplate().update(sql, new Object[]{circle.getId(), circle.getName()});
	}
	
	public void createTriangleTable() {
		String sql = "create table Triangle (id integer, name varchar(50))";
		getJdbcTemplate().execute(sql);
	}
	
}
