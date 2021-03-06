package com.wfly.ticketcdi.controller;

import com.wfly.ticketcdi.boundary.NoSuchSeatException;
import com.wfly.ticketcdi.boundary.TheatreBox;
import com.wfly.ticketcdi.util.Logged;

import org.jboss.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
@Logged
public class TheatreBooker implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2774564534015825754L;

	
	@Inject
	private Logger logger;
	@Inject
	private TheatreBox theatreBox;
	@Inject
	private FacesContext facesContext;
	private int money;

	@PostConstruct
	public void createCustomer() {
		this.money = 100;
	}

	public void bookSeat(int seatId) throws NoSuchSeatException {
		logger.info("Booking	seat	" + seatId);
		int seatPrice = theatreBox.getSeatPrice(seatId);
		if (seatPrice > money) {
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Not enough Money!",
					"Registration unsuccessful");
			facesContext.addMessage(null, m);
			return;
		}
		theatreBox.buyTicket(seatId);
		FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Booked!", "Booking successful");
		facesContext.addMessage(null, m);
		logger.info("Seat booked.");
		money = money - seatPrice;
	}

	public int getMoney() {
		return money;
	}
}
