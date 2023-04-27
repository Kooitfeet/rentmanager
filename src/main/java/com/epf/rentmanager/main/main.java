package com.epf.rentmanager.main;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;

import java.time.LocalDate;

public class main {
    public static void main(String args[]){
        Vehicle vehicle = new Vehicle();
        vehicle.setNbPlaces(5);

        System.out.println(vehicle.getNbPlaces());
    }
}
