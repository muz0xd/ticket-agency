package com.wfly.ticket.boundary;

public class NotEnoughMoneyException extends Exception {

	private static final long serialVersionUID = 1L;

	public NotEnoughMoneyException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NotEnoughMoneyException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

	public NotEnoughMoneyException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public NotEnoughMoneyException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public NotEnoughMoneyException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

}
