package com.wfly.ticketcdi.boundary;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ejb.*;
import javax.annotation.*;
import java.util.concurrent.TimeUnit;
import org.jboss.logging.*;
import com.wfly.ticketcdi.entity.Seat;


import java.util.*;

@Singleton
@Startup
@AccessTimeout(value = 5, unit = TimeUnit.MINUTES)
public class TheatreBox {
	@Inject
	private Event<Seat> seatEvent;

	@Lock(LockType.WRITE)
	public void buyTicket(int seatId) throws NoSuchSeatException {
		final Seat seat = getSeat(seatId);
		final Seat bookedSeat = seat.getBookedSeat();
		addSeat(bookedSeat);
		seatEvent.fire(bookedSeat);
	}
	
	private static final Logger logger = Logger.getLogger(TheatreBox.class);

	private Map<Integer, Seat> seats;

	@PostConstruct
	public void setupTheatre() {
		seats = new HashMap<>();
		int id = 0;
		for (int i = 0; i < 5; i++) {
			addSeat(new Seat(++id, "Stalls", 40));
			addSeat(new Seat(++id, "Circle", 20));
			addSeat(new Seat(++id, "Balcony", 10));
		}
		logger.info("Seat Map constructed.");
	}

	private void addSeat(Seat seat) {
		seats.put(seat.getId(), seat);
	}

	@Lock(LockType.READ)
	public Collection<Seat> getSeats() {
		return Collections.unmodifiableCollection(seats.values());
	}

	@Lock(LockType.READ)
	public int getSeatPrice(int seatId) throws NoSuchSeatException {
		return getSeat(seatId).getPrice();
	}

	@Lock(LockType.READ)
	private Seat getSeat(int seatId) throws NoSuchSeatException {
		final Seat seat = seats.get(seatId);
		if (seat == null) {
			throw new NoSuchSeatException("Seat" + seatId + "does not exist!");
		}
		return seat;
	}

}
