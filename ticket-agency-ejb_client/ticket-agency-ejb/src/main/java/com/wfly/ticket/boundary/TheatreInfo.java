package com.wfly.ticket.boundary;

import javax.ejb.*;
import java.util.*;
import com.wfly.ticket.control.*;

@Stateless
@Remote(TheatreInfoRemote.class)
public class TheatreInfo implements TheatreInfoRemote {
	@EJB
	private TheatreBox box;

	@Override
	public String printSeatList() {
		final Collection<Seat> seats = box.getSeats();
		final StringBuilder sb = new StringBuilder();
		for (Seat seat : seats) {
			sb.append(seat.toString());
			sb.append(System.lineSeparator());
		}
		return sb.toString();
	}
}