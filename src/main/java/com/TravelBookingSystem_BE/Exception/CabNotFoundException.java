package com.TravelBookingSystem_BE.Exception;

public class CabNotFoundException extends RuntimeException{

   public CabNotFoundException(Long id)
   {
       super("Could not found the car with id: " +id);
   }

}
