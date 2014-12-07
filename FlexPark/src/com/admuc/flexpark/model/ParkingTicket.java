package com.admuc.flexpark.model;

import java.util.Date;

public class ParkingTicket {

   private User user;
   private ParkingSpace parkingSpace;
   private Date startTime;
   private Date endTime;
   private Date reminder;
   private Double parkingFee;

   public ParkingTicket(User user, ParkingSpace parkingSpace, Date startTime, Date endTime, Date reminder, Double parkingFee) {
      this.user = user;
      this.parkingSpace = parkingSpace;
      this.startTime = startTime;
      this.endTime = endTime;
      this.reminder = reminder;
      this.parkingFee = parkingFee;
   }

}
