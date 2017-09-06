package com.wfly.ticketjpa.control;

import com.wfly.ticketjpa.entity.Seat;

import javax.ejb.Stateless;


@Stateless
public class SeatDao extends AbstractDao<Seat> {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8954446169937937654L;

	public SeatDao() {
        super(Seat.class);
    }
}
