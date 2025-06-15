package com.xionChiStudios.chiGuysDealership;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

@SpringBootApplication
public class ChiGuysDealershipApplication {
	private static final Logger logger = Logger.getLogger(ChiGuysDealershipApplication.class.getName());

	public static void main(String[] args) {SpringApplication.run(ChiGuysDealershipApplication.class, args);




	}



















   public static void displayVehicles() {
		VehicleDataManager.displayAllVehicles();
   }

}


