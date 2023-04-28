package com.epf.rentmanager.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@WebServlet(name = "VehiculeUpdateServlet", value = "/cars/update")
public class VehiculeUpdateServlet extends HttpServlet{
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
            request.setAttribute("vehicule", vehicleService.findById(Integer.parseInt(request.getParameter("id"))));
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        request.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/update.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(Integer.parseInt(request.getParameter("id")));
        vehicle.setConstructeur(request.getParameter("constructeur"));
        vehicle.setModele(request.getParameter("modele"));
        vehicle.setNbPlaces(Integer.parseInt(request.getParameter("nb_places")));

        try {
            vehicleService.update(vehicle);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        //doGet(request, response);
        response.sendRedirect("/rentmanager/cars");
    }
}
