package it.polito.tdp.ufo.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import it.polito.tdp.ufo.model.Arco;
import it.polito.tdp.ufo.model.Avvistamento;
import it.polito.tdp.ufo.model.Sighting;

public class SightingsDAO {
	
	public List<Sighting> getSightings() {
		String sql = "SELECT * FROM sighting" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Sighting> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				list.add(new Sighting(res.getInt("id"),
						res.getTimestamp("datetime").toLocalDateTime(),
						res.getString("city"), 
						res.getString("state"), 
						res.getString("country"),
						res.getString("shape"),
						res.getInt("duration"),
						res.getString("duration_hm"),
						res.getString("comments"),
						res.getDate("date_posted").toLocalDate(),
						res.getDouble("latitude"), 
						res.getDouble("longitude"))) ;
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}

	public List<Avvistamento> getAnno() {
	String sql="SELECT YEAR (datetime) as d, COUNT(DISTINCT id) as peso " + 
			"FROM sighting as s " + 
			"WHERE s.country='us' " + 
			"GROUP BY d " + 
			"ORDER  BY d ASC ";
	List<Avvistamento> result=new ArrayList<>();
	
	try {
		Connection conn = DBConnect.getConnection() ;

		PreparedStatement st = conn.prepareStatement(sql) ;
		
		
		ResultSet res = st.executeQuery() ;
		
		while(res.next()) {
			Avvistamento a=new Avvistamento(res.getInt("d"),res.getInt("peso"));
			result.add(a); 
		}
		
		conn.close();
		return result ;

	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return null ;
	}
	
	}

	public List<String> getStati(int anno) {
		
		
	String sql="SELECT DISTINCT state as d " + 
			"FROM sighting as s " + 
			"WHERE s.country='us' and YEAR (datetime)= ? " + 
			"GROUP BY d " + 
			"ORDER  BY d ASC ";
		
		List<String> result=new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, anno);
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				
				result.add(res.getString("d")); 
			}
			
			conn.close();
			return result ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}

	public List<Arco> getArco(int anno) {
	String sql="SELECT s1.state as s1, s2.state  as s2 " + 
			"FROM sighting as s1, sighting as s2 " + 
			"WHERE s1.country='us' and s2.country='us' and YEAR(s1.datetime)= ? and YEAR(s2.datetime)= ? and  s1.state>s2.state and s1.`datetime`>s2.datetime " + 
			"GROUP BY s1.state, s2.state ";

	List<Arco> result=new ArrayList<>();
	
	try {
		Connection conn = DBConnect.getConnection() ;

		PreparedStatement st = conn.prepareStatement(sql) ;
		st.setInt(1, anno);
		st.setInt(2, anno);
		ResultSet res = st.executeQuery() ;
		
		while(res.next()) {
			
			Arco a=new Arco(res.getString("s1"), res.getString("s2"));
			result.add(a); 
		}
		
		conn.close();
		return result ;

	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return null ;
	}
		
		
	}

}
