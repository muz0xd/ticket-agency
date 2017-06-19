package com.wfly.ticket.boundary;

import com.wfly.ticket.control.*; 

public interface TheatreBookerRemote {

	String bookSeat(int seatId) throws SeatBookedException, NotEnoughMoneyException, NoSuchSeatException;

	int getAccountBalance();

}
