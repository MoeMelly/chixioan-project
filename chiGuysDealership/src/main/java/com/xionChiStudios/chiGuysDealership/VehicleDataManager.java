package com.xionChiStudios.chiGuysDealership;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VehicleDataManager {
    private static final Logger logger = Logger.getLogger(VehicleDataManager.class.getName());
    private static final String URL = "jdbc:mysql://localhost:3306/dealership?user=root&password=yearup";
    private static final BasicDataSource source = new BasicDataSource();

    public static Connection connection() throws SQLException {
        return DriverManager.getConnection(URL);
    }


    public static void displayAllVehicles() {
        String sql = "SELECT * FROM vehicle";
        try (Connection connection = VehicleDataManager.connection()) {
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String make = resultSet.getString("make");
                String model = resultSet.getString("model");
                int year = resultSet.getInt("year");
                String vin = resultSet.getString("vin");
                int mileage = resultSet.getInt("mileage");
                double price = resultSet.getDouble("price");
                VehicleColor color = VehicleColor.valueOf(resultSet.getString("color").toUpperCase());
                VehicleStatus status = VehicleStatus.valueOf(resultSet.getString("status").toUpperCase());
                Vehicle newVehicle = new Vehicle(id, make, model, year,  vin, mileage, color, price, status);
                System.out.println(newVehicle);
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, "ERROR DISPLAYING VEHICLE", e);
        }
    }

    public void insertVehicle(Vehicle vehicle) {
        String SQL = "INSERT INTO vehicle (id, make, model, year, vin, mileage, color, price, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,)";
        try (Connection connection = connection()) {
            PreparedStatement statement = connection().prepareStatement(SQL);
            statement.setInt(1, vehicle.id());
            statement.setString(2, vehicle.make());
            statement.setString(3, vehicle.model());
            statement.setInt(4, vehicle.year());
            statement.setString(5, vehicle.vin());
            statement.setInt(6, vehicle.mileage());
            statement.setString(7, String.valueOf(vehicle.color()));
            statement.setDouble(8, vehicle.price());
            statement.setString(9, String.valueOf(vehicle.status()));
            logger.info("✅Vehicle added!");

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "ERROR INSERTING VEHICLE", e);
        }
    }

    public void updateVehicle(Vehicle vehicle) {
        String SQL = "UPDATE vehicle SET make = ?, model = ?, year = ?, vin = ?, mileage = ?, color = ?, price = ?, status = ? WHERE id = ?";

        try (Connection conn = connection()) {
            PreparedStatement statement = connection().prepareStatement(SQL);
            statement.setInt(1, vehicle.id());
            statement.setString(2, vehicle.make());
            statement.setString(3, vehicle.model());
            statement.setInt(4, vehicle.year());
            statement.setString(5, vehicle.vin());
            statement.setInt(6, vehicle.mileage());
            statement.setString(7, String.valueOf(vehicle.color()));
            statement.setDouble(8, vehicle.price());
            statement.setString(9, String.valueOf(vehicle.status()));

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("✅Vehicle Updated!");
            } else {
                logger.warning("⚠️ No vehicle found with ID " + vehicle.id());
            }


        } catch (SQLException e) {
            logger.log(Level.SEVERE, "ERROR Updating QUERY");
        }
    }

    public void deleteVehicle(int id) {
        String SQl = "DELETE FROM vehicle WHERE id = ?";

        try (Connection connection = connection()) {
            PreparedStatement statement = connection().prepareStatement(SQl);
            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("✅Vehicle Has Been Removed From Inventory!");
            } else {
                logger.warning("⚠️ No Vehicle Matching: " + id + " " + "Found");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void getVehicleById(int id) {
        String sql = "SELECT * FROM vehicle WHERE id = ?";
        try (Connection connection = connection()) {
            PreparedStatement statement = connection().prepareStatement(sql);

            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();

            if (set.next()) {
                String make = set.getString("make");
                String model = set.getString("model");
                String color = set.getString("color");

                logger.info(Vehicle.class.toString());
            } else {
                logger.warning("No Vehicle Found with ID: " + id);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error Finding Match For Vehicle with: " + id, e);
        }
    }
}


