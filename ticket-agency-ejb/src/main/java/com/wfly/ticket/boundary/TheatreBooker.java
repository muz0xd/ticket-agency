package com.wfly.ticket.boundary;

import com.wfly.ticket.control.*;

import javax.ejb.*;
import java.util.concurrent.TimeUnit;
import org.jboss.logging.*;
import javax.annotation.*;

@Stateful
@Remote(TheatreBookerRemote.class)
@AccessTimeout(value = 5, unit = TimeUnit.MINUTES)
public class TheatreBooker implements TheatreBookerRemote {
	private static final Logger logger = Logger.getLogger(TheatreBooker.class);
	@EJB
	private TheatreBox theatreBox;
	private int money;

	@PostConstruct
	public void createCustomer() {
		this.money = 100;
	}

	@Override
	public int getAccountBalance() {
		return money;
	}

	@Override
	public String bookSeat(int seatId) throws SeatBookedException, NotEnoughMoneyException, NoSuchSeatException {
		final int seatPrice = theatreBox.getSeatPrice(seatId);
		if (seatPrice > money) {
			throw new NotEnoughMoneyException("You don’t have enough money to buy this" + seatId + "seat!");
		}
		theatreBox.buyTicket(seatId);
		money = money - seatPrice;
		logger.infov("Seat {0}	booked.", seatId);
		return "Seat booked.";
	}
}