package model;

public class Event {

	private String date;
	private String time;
	private int guestsNumber;
		
	public Event(String d, String t, int gnum) {
		this.date = d;
		this.time = t;
		this.guestsNumber = gnum;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getGuestsNumber() {
		return guestsNumber;
	}

	public void setGuestsNumber(int guestsNumber) {
		this.guestsNumber = guestsNumber;
	}
	
}
