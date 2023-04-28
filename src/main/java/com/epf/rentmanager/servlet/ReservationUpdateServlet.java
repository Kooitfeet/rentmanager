package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet(name = "VehiculeUpdateServlet", value = "/rents/update")
public class ReservationUpdateServlet extends HttpServlet{
        @Autowired
        private ReservationService reservationService;

        @Autowired
        private VehicleService vehicleService;
        @Autowired
        private ClientService clientService;

        @Override
        public void init() throws ServletException {
            super.init();
            SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
            System.out.println("My servlet has been initialized");
        }

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            try {
                request.setAttribute("reservation", reservationService.findById(Integer.parseInt(request.getParameter("id"))));
                List<Vehicle> vehicles = vehicleService.findAll();
                List<Client> clients = clientService.findAll();
                request.setAttribute("vehicules", vehicles);
                request.setAttribute("clients", clients);
            } catch (ServiceException e) {
                e.printStackTrace();
            }
            request.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/update.jsp").forward(request, response);
        }

        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            Reservation reservation = new Reservation();
            reservation.setId(Integer.parseInt(request.getParameter("id")));
            try {
                reservation.setVehicle(vehicleService.findById(Long.parseLong(request.getParameter("vehicule"))));
                reservation.setClient(clientService.findById(Long.parseLong(request.getParameter("client"))));
            } catch (ServiceException e) {
                throw new RuntimeException(e);
            }
            reservation.setDateDebut(LocalDate.parse(request.getParameter("debut")));
            reservation.setDateFin(LocalDate.parse(request.getParameter("fin")));

            try {
                reservationService.update(reservation);
            } catch (ServiceException e) {
                e.printStackTrace();
            }
            response.sendRedirect("/rentmanager/rents");
        }
}
