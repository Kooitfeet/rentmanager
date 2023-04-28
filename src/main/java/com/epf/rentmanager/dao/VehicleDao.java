package com.epf.rentmanager.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;


@Repository

public class VehicleDao {

	private VehicleDao() {}
	
	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, nb_places, model) VALUES(?, ?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, nb_places, model FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, nb_places, model FROM Vehicle;";
	
	public long create(Vehicle vehicle) throws DaoException {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement stmt = conn.prepareStatement(CREATE_VEHICLE_QUERY,
					Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, vehicle.getConstructeur());
			stmt.setString(3, vehicle.getModele());
			stmt.setInt(2, vehicle.getNbPlaces());
			long key = ((PreparedStatement) stmt).executeUpdate();
			conn.close();
			return key;
		} catch (SQLException e) {
			throw new DaoException();
		}
	}

	public long delete(Vehicle vehicle) throws DaoException {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement stmt = conn.prepareStatement(DELETE_VEHICLE_QUERY,
					Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, (int) vehicle.getId());
			long key = ((PreparedStatement) stmt).executeUpdate();
			return key;
		} catch (SQLException e) {
			throw new DaoException();
		}
	}

	public Vehicle findById(long id) throws DaoException {

		Vehicle vehicle = new Vehicle();
		try{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stat = connection.prepareStatement(FIND_VEHICLE_QUERY);
			stat.setLong(1,id);
			ResultSet rs = stat.executeQuery();
			while(rs.next()){
				int nb_places = rs.getInt("nb_places");
				String constructeur = rs.getString("constructeur");
				String modele = rs.getString("model");

				vehicle = new Vehicle(id,nb_places,constructeur,modele);
			}
		} catch (SQLException e){
			throw new RuntimeException(e);
		}
		return vehicle;

	}

	public List<Vehicle> findAll() throws DaoException {

		List<Vehicle> vehicles = new ArrayList<Vehicle>();
		try{
			Connection connection = ConnectionManager.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(FIND_VEHICLES_QUERY);

			while(rs.next()){
				int id = rs.getInt("id");
				int nb_places = rs.getInt("nb_places");
				String constructeur = rs.getString("constructeur");
				String modele = rs.getString("model");

				vehicles.add(new Vehicle(id,nb_places,constructeur,modele));
			}
		}catch (SQLException e){
			e.printStackTrace();
			throw new DaoException();
		}
		return vehicles;
	}
}
