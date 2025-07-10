package com.example.seat;

import java.sql.*;
import java.util.*;

public class SeatDao {
    private String url;
    private String user;
    private String password;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Postgres driver не подключен");
        }
    }

    public SeatDao() {
        String host = System.getenv().getOrDefault("DB_HOST", "localhost");
        String port = System.getenv().getOrDefault("DB_PORT", "5432");
        String db = System.getenv().getOrDefault("DB_NAME", "yourdb");
        this.user = System.getenv().getOrDefault("DB_USER", "youruser");
        this.password = System.getenv().getOrDefault("DB_PASSWORD", "yourpassword");
        this.url = "jdbc:postgresql://" + host + ":" + port + "/" + db;
    }

    public List<Seat> getAllSeats() {
        List<Seat> seats = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, status, passport_number FROM seats ORDER BY id")) {
            while (rs.next()) {
                seats.add(new Seat(
                        rs.getInt("id"),
                        rs.getString("status"),
                        rs.getString("passport_number")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seats;
    }

    public void reserveSeat(int seatId, String passportNumber) {
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(
                     "UPDATE seats SET status='reserved', passport_number=? WHERE id=? AND (passport_number IS NULL or passport_number='')")) {
            ps.setString(1, passportNumber);
            ps.setInt(2, seatId);
            if (ps.executeUpdate() == 0) {
                throw new SeatAlreadyReservedException("Место уже забронировано другим пользователем. Выберите другое!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
} 