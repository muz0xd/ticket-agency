package com.wfly.ticketcdi.control;

import com.wfly.ticketcdi.boundary.NoSuchSeatException;
import com.wfly.ticketcdi.boundary.TheatreBox;
import com.wfly.ticketcdi.entity.Seat;

import org.jboss.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.inject.Inject;
import java.util.Collection;
import java.util.Optional;

@Stateless
public class AutomaticSellerService {
	@Inject
	private Logger logger;
	@Inject
	private TheatreBox theatreBox;
	@Resource
	private TimerService timerService;

	@Schedule(hour = "*", minute = "*", second = "*/30", persistent = false)
	public void automaticCustomer() throws NoSuchSeatException {
		final Optional<Seat> seatOptional = findFreeSeat();
		if (!seatOptional.isPresent()) {
			cancelTimers();
			logger.info("Scheduler	gone!");
			return; // No more seats
		}
		final Seat seat = seatOptional.get();
		theatreBox.buyTicket(seat.getId());
		logger.info("Somebody just booked seat number" + seat.getId());
	}

	private Optional<Seat> findFreeSeat() {
		final Collection<Seat> list = theatreBox.getSeats();
		return list.stream().filter(seat -> !seat.isBooked()).findFirst();
	}

	private void cancelTimers() {
		for (Timer timer : timerService.getTimers()) {
			timer.cancel();
		}
	}
}
