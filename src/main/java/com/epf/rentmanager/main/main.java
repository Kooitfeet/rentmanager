package com.epf.rentmanager.main;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class main {
    public static void main(String args[]){

        List<Vehicle> vehicules = new ArrayList<>();

        System.out.println(vehicules);
    }
}
