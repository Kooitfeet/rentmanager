package com.epf.rentmanager.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.stereotype.Repository;

@Repository

public class ReservationDao {

	private ClientDao clientDao;
	private VehicleDao vehicleDao;

	private ReservationDao(ClientDao clientDao, VehicleDao vehicleDao) {
		this.vehicleDao = vehicleDao;
		this.clientDao = clientDao;
	}

	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String UPDATE_RESERVATION_QUERY = "UPDATE Reservation SET vehicle_id = ?, client_id = ?, debut = ?, fin = ?  WHERE id = ?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
	private static final String FIND_RESERVATION_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation WHERE id=?;";

	public long create(Reservation reservation) throws DaoException {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement stmt = conn.prepareStatement(CREATE_RESERVATION_QUERY,
					Statement.RETURN_GENERATED_KEYS);
			stmt.setLong(1, reservation.getIdClient());
			stmt.setLong(2, reservation.getIdVehicle());
			stmt.setDate(3, Date.valueOf(reservation.getDateDebut()));
			stmt.setDate(4, Date.valueOf(reservation.getDateFin()));
			long key = ((PreparedStatement) stmt).executeUpdate();
			conn.close();
			return key;
		} catch (SQLException e) {
			throw new DaoException();
		}
	}

	public long delete(Reservation reservation) throws DaoException {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement stmt = conn.prepareStatement(DELETE_RESERVATION_QUERY,
					Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, (int) reservation.getId());
			long key = ((PreparedStatement) stmt).executeUpdate();
			return key;
		} catch (SQLException e) {
			throw new DaoException();
		}
	}

	public List<Reservation> findResaByClientId(long clientId) throws DaoException {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement stmt = conn.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY);
			stmt.setLong(1, clientId);
			ResultSet rs = stmt.executeQuery();

			ArrayList<Reservation> resaList = new ArrayList<Reservation>();
			while (rs.next()) {
				Reservation reservation = new Reservation();
				reservation.setId(rs.getInt("id"));
				reservation.setClient(clientDao.findById(clientId));
				reservation.setVehicle(vehicleDao.findById(rs.getInt("vehicle_id")));
				reservation.setDateFin(rs.getDate("fin").toLocalDate());
				reservation.setDateDebut(rs.getDate("debut").toLocalDate());

				resaList.add(reservation);
			}
			conn.close();
			return resaList;
		} catch (SQLException e) {
			throw new DaoException();
		}
	}

	public List<Reservation> findResaByVehiculeId(long VehiculeId) throws DaoException {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement stmt = conn.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY);
			stmt.setLong(1, VehiculeId);
			ResultSet rs = stmt.executeQuery();

			ArrayList<Reservation> resaList = new ArrayList<Reservation>();
			while (rs.next()) {
				Reservation reservation = new Reservation();
				reservation.setId(rs.getInt("id"));
				reservation.setClient(clientDao.findById(rs.getInt("client_id")));
				reservation.setVehicle(vehicleDao.findById(VehiculeId));
				reservation.setDateFin(rs.getDate("fin").toLocalDate());
				reservation.setDateDebut(rs.getDate("debut").toLocalDate());

				resaList.add(reservation);
			}
			conn.close();
			return resaList;
		} catch (SQLException e) {
			throw new DaoException();
		}
	}

	public List<Reservation> findAll() throws DaoException {

		List<Reservation> reservations = new ArrayList<Reservation>();
		try {
			Connection connection = ConnectionManager.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(FIND_RESERVATIONS_QUERY);

			while (rs.next()) {
				int id = rs.getInt("id");
				int client_id = rs.getInt("client_id");
				Client client = clientDao.findById(client_id);
				int vehicle_id = rs.getInt("vehicle_id");
				Vehicle vehicle = vehicleDao.findById(vehicle_id);
				LocalDate debut = rs.getDate("debut").toLocalDate();
				LocalDate fin = rs.getDate("fin").toLocalDate();

				reservations.add(new Reservation(id, client, vehicle, debut, fin));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
		return reservations;
	}

	public Reservation findById(long id) throws DaoException {

		Reservation reservation = new Reservation();
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stat = connection.prepareStatement(FIND_RESERVATION_QUERY);
			stat.setLong(1, id);
			ResultSet rs = stat.executeQuery();
			while (rs.next()) {
				Client client = clientDao.findById(rs.getInt("client_id"));
				Vehicle vehicle = vehicleDao.findById(rs.getInt("vehicle_id"));
				LocalDate debut = rs.getDate("debut").toLocalDate();
				LocalDate fin = rs.getDate("fin").toLocalDate();


				reservation = new Reservation(id, client, vehicle, debut, fin);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return reservation;
	}
	public long update(Reservation reservation) throws DaoException {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement stmt = conn.prepareStatement(UPDATE_RESERVATION_QUERY, Statement.RETURN_GENERATED_KEYS);
			stmt.setLong(1, reservation.getVehicle().getId());
			stmt.setLong(2, reservation.getClient().getId());
			stmt.setDate(3, Date.valueOf(reservation.getDateDebut()));
			stmt.setDate(4, Date.valueOf(reservation.getDateFin()));
			stmt.setLong(5, reservation.getId());
			long key = stmt.executeUpdate();
			conn.close();
			return key;
		} catch (SQLException e) {
			throw new DaoException();
		}
	}
}
