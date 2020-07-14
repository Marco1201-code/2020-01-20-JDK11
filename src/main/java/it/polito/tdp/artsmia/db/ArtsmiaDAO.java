package it.polito.tdp.artsmia.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.artsmia.model.Adiacenze;
import it.polito.tdp.artsmia.model.ArtObject;
import it.polito.tdp.artsmia.model.Artista;
import it.polito.tdp.artsmia.model.Exhibition;

public class ArtsmiaDAO {

	public List<ArtObject> listObjects() {
		
		String sql = "SELECT * from objects";
		List<ArtObject> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				ArtObject artObj = new ArtObject(res.getInt("object_id"), res.getString("classification"), res.getString("continent"), 
						res.getString("country"), res.getInt("curator_approved"), res.getString("dated"), res.getString("department"), 
						res.getString("medium"), res.getString("nationality"), res.getString("object_name"), res.getInt("restricted"), 
						res.getString("rights_type"), res.getString("role"), res.getString("room"), res.getString("style"), res.getString("title"));
				
				result.add(artObj);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Exhibition> listExhibitions() {
		
		String sql = "SELECT * from exhibitions";
		List<Exhibition> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Exhibition exObj = new Exhibition(res.getInt("exhibition_id"), res.getString("exhibition_department"), res.getString("exhibition_title"), 
						res.getInt("begin"), res.getInt("end"));
				
				result.add(exObj);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
public List<String> listRole() {
		
		String sql = "SELECT DISTINCT role \n" + 
				"FROM authorship";
		List<String> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				
				result.add(res.getString("role"));
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	

public List<Artista> listVertex(String role) {
		
		String sql = "SELECT DISTINCT a.artist_id , NAME " + 
				"FROM artists AS a, authorship AS au  "+ 
				"WHERE a.artist_id = au.artist_id AND au.role =?";
		List<Artista> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1,role);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				
				result.add(new Artista(res.getInt("a.artist_id"),res.getString("NAME")));
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	
}


public List<Artista> listVertex() {
	
	String sql = "SELECT DISTINCT artist_id , NAME " + 
			"FROM artists ";
	List<Artista> result = new ArrayList<>();
	Connection conn = DBConnect.getConnection();

	try {
		PreparedStatement st = conn.prepareStatement(sql);
		
		ResultSet res = st.executeQuery();
		while (res.next()) {

			
			result.add(new Artista(res.getInt("artist_id"),res.getString("NAME")));
		}
		conn.close();
		return result;
		
	} catch (SQLException e) {
		e.printStackTrace();
		return null;
	}

}

public List<Adiacenze> listEdge(String role,Map<Integer,Artista> map) {
	
	String sql = "SELECT a1.artist_id,a2.artist_id , COUNT(*) AS TOT\n" + 
			"FROM authorship AS a1  , authorship AS a2 ,exhibition_objects eo1 , exhibition_objects eo2\n" + 
			"WHERE a1.artist_id>a2.artist_id \n" + 
			"AND a1.role =? AND a2.role =?\n" + 
			"AND eo1.exhibition_id = eo2.exhibition_id\n" + 
			"AND eo1.object_id != eo2.object_id\n" + 
			"AND  eo1.object_id = a1.object_id\n" + 
			"AND eo2.object_id = a2.object_id\n" + 
			"GROUP BY a1.artist_id,a2.artist_id ";
	List<Adiacenze> result = new ArrayList<>();
	Connection conn = DBConnect.getConnection();

	try {
		PreparedStatement st = conn.prepareStatement(sql);
		st.setString(1,role);
		st.setString(2,role);
		ResultSet res = st.executeQuery();
		while (res.next()) {

			Artista a1 = map.get(res.getInt("a1.artist_id"));
			Artista a2 = map.get(res.getInt("a2.artist_id"));
			Integer peso = res.getInt("TOT");
			System.out.println(a1+"\n"+a2);
			result.add(new Adiacenze(a1,a2,peso));
			
		}
		conn.close();
		return result;
		
	} catch (SQLException e) {
		e.printStackTrace();
		return null;
	}

}




}
