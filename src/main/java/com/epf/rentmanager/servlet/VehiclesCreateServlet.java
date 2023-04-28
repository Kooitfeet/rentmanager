package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet(name = "VehicleCreateServlet", value = "/cars/create")
public class VehiclesCreateServlet extends HttpServlet {

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
            request.setAttribute("users", clientService.findAll());
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        request.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/create.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Vehicle vehicle = new Vehicle();
        vehicle.setNbPlaces(Integer.parseInt(request.getParameter("nb_places")));
        vehicle.setConstructeur(request.getParameter("constructeur"));
        vehicle.setModele(request.getParameter("modele"));
        try {
            vehicleService.create(vehicle);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        response.sendRedirect("/rentmanager/cars");
    }
}
