package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
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

@WebServlet(name = "ReservationDetailsServlet", urlPatterns = "/rents/details")
public class ReservationDetailsServlet extends HttpServlet {
        @Autowired
        private ClientService clientService;

        @Autowired
        private ReservationService reservationService;

        @Autowired
        private VehicleService vehicleService;

        @Override
        public void init() throws ServletException {
            super.init();
            SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
            System.out.println("My servlet has been initialized");
        }

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            try {
                long id = Long.parseLong(request.getParameter("id"));
                request.setAttribute("reservation", reservationService.findById(id));
            } catch (ServiceException e) {
                e.printStackTrace();
            }
            request.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/details.jsp").forward(request, response);
        }

        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            doGet(request, response);
        }

}
