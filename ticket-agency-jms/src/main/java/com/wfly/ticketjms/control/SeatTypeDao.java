package com.wfly.ticketjms.control;

import com.wfly.ticketjms.entity.SeatType;

import javax.ejb.Stateless;


@Stateless
public class SeatTypeDao extends AbstractDao<SeatType> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3352055245349822392L;

	public SeatTypeDao() {
        super(SeatType.class);
    }
}
