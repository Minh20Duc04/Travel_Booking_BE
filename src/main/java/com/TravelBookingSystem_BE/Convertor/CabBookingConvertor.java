package com.TravelBookingSystem_BE.Convertor;

import com.TravelBookingSystem_BE.Dto.CabBookingDto;
import com.TravelBookingSystem_BE.Model.Cab;
import com.TravelBookingSystem_BE.Model.CabBooking;
import org.springframework.stereotype.Component;

@Component
public class CabBookingConvertor {

    public CabBooking CabBookingDtotoCabBooking(CabBookingDto cabBookingDto, Cab cab) {
        return CabBooking.builder()
                .custName(cabBookingDto.getCustName())
                .custGender(cabBookingDto.getCustGender())
                .custEmail(cabBookingDto.getCustEmail())
                .custPhone(cabBookingDto.getCustPhone())
                .custAddress(cabBookingDto.getCustAddress())
                .pickUpLocation(cabBookingDto.getPickUpLocation())
                .dropLocation(cabBookingDto.getDropLocation())
                .pickUpDate(cabBookingDto.getPickUpDate())
                .dropDate(cabBookingDto.getDropDate())
                .totalCost(cabBookingDto.getTotalCost())
                .cab(cab)
                .build();
    }

    public CabBookingDto CabBookingtoCabBookingDto(CabBooking cabBooking) {
        return new CabBookingDto(
                cabBooking.getCab().getCabId(),
                cabBooking.getCustName(),
                cabBooking.getDropDate(),
                cabBooking.getCustGender(),
                cabBooking.getCustEmail(),
                cabBooking.getCustPhone(),
                cabBooking.getCustAddress(),
                cabBooking.getPickUpDate(),
                cabBooking.getPickUpLocation(),
                cabBooking.getDropLocation(),
                cabBooking.getTotalCost()
        );
    }
}
