package com.wfly.ticketcdi.controller;

import com.wfly.ticketcdi.entity.Seat;
import java.io.Serializable;
import javax.enterprise.event.Observes;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class BookingRecord implements Serializable {

	private static final long serialVersionUID = -6316795315793817571L;
	
	private int bookedCount = 0;

	public int getBookedCount() {
		return bookedCount;
	}

	public void bookEvent(@Observes Seat bookedSeat) {
		bookedCount++;
	}
}
