package com.wfly.ticket.client;

import java.util.Properties;
import java.util.logging.*;
import javax.naming.*;
import com.wfly.ticket.boundary.NotEnoughMoneyException;
import com.wfly.ticket.boundary.TheatreBookerRemote;
import com.wfly.ticket.boundary.TheatreInfoRemote;
import com.wfly.ticket.control.NoSuchSeatException;
import com.wfly.ticket.control.SeatBookedException;
import com.wfly.ticket.utils.IOUtils;

public class TicketAgencyClient {
	private static final Logger logger = Logger.getLogger(TicketAgencyClient.class.getName());

	public static void main(String[] args) throws Exception {
		Logger.getLogger("org.jboss").setLevel(Level.SEVERE);
		Logger.getLogger("org.xnio").setLevel(Level.SEVERE);
		new TicketAgencyClient().run();
	}

	private final Context context;
	private TheatreInfoRemote theatreInfo;
	private TheatreBookerRemote theatreBooker;

	public TicketAgencyClient() throws NamingException {
		final Properties jndiProperties = new Properties();
		jndiProperties.setProperty(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		this.context = new InitialContext(jndiProperties);
	}

	private enum Command {
		BOOK, LIST, MONEY, QUIT, INVALID;
		public static Command parseCommand(String stringCommand) {
			try {
				return valueOf(stringCommand.trim().toUpperCase());
			} catch (IllegalArgumentException iae) {
				return INVALID;
			}
		}
	}

	private void run() throws NamingException {
		this.theatreInfo = lookupTheatreInfoEJB();
		this.theatreBooker = lookupTheatreBookerEJB();
		showWelcomeMessage();
		while (true) {
			final String stringCommand = IOUtils.readLine("> ");
			final Command command = Command.parseCommand(stringCommand);
			switch (command) {
			case BOOK:
				handleBook();
				break;
			case LIST:
				handleList();
				break;
			case MONEY:
				handleMoney();
				break;
			case QUIT:
				handleQuit();
				break;
			default:
				logger.warning("Unknown	command	" + stringCommand);
			}
		}
	}

	private void handleBook() {
		int seatId;
		try {
			seatId = IOUtils.readInt("Enter	SeatId:	");
		} catch (NumberFormatException e1) {
			logger.warning("Wrong	SeatId	format!");
			return;
		}
		try {
			final String retVal = theatreBooker.bookSeat(seatId);
			System.out.println(retVal);
		} catch (SeatBookedException | NotEnoughMoneyException | NoSuchSeatException e) {
			logger.warning(e.getMessage());
			return;
		}
	}

	private void handleList() {
		logger.info(theatreInfo.printSeatList());
	}

	private void handleMoney() {
		final int accountBalance = theatreBooker.getAccountBalance();
		logger.info("You have: " + accountBalance + "money left.");
	}

	private void handleQuit() {
		logger.info("Bye");
		System.exit(0);
	}

	private TheatreInfoRemote lookupTheatreInfoEJB() throws NamingException {
		return (TheatreInfoRemote) context.lookup(
				"ejb:/ticket-agency-ejb//TheatreInfo!com.wfly.ticket.boundary.TheatreInfoRemote");
	}

	private TheatreBookerRemote lookupTheatreBookerEJB() throws NamingException {
		return (TheatreBookerRemote) context.lookup(
				"ejb:/ticket-agency-ejb//TheatreBooker!com.wfly.ticket.boundary.TheatreBookerRemote?stateful");
	}

	private void showWelcomeMessage() {
		System.out.println("Theatre	booking	system");
		System.out.println("=====================================");
		System.out.println("Commands: book, list, money, quit");
	}
}
