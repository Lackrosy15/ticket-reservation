package com.example.seat;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/seats")
public class SeatServlet extends HttpServlet {
    private final SeatDao seatDao = new SeatDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Seat> seats = seatDao.getAllSeats();
        req.setAttribute("seats", seats);
        req.getRequestDispatcher("/seats.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int seatId = Integer.parseInt(req.getParameter("seatId"));
        String passportNumber = req.getParameter("passportNumber");
        try {
            seatDao.reserveSeat(seatId, passportNumber);
            resp.sendRedirect("seats");
        } catch (SeatAlreadyReservedException e) {
            req.setAttribute("errorMessage", e.getMessage());
            doGet(req, resp);
        }
    }
} 