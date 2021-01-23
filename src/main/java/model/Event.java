package model;

import java.util.GregorianCalendar;

public class Event {

	private User owner;
	private int maxGuestsNumber;
	private int guestsNumber;
	private GregorianCalendar dateTime;
	private GuestStatus guestStatus;
	private PaymentStatus payStatus;
	private double bill;
	private boolean payRequired;
	private String region;
	private String province;
	private String city;
	private String address;
		
	public Event(User owner, GregorianCalendar dateTime, int maxGuests, double bill) {
		this.owner = owner;
		this.dateTime = dateTime;
		this.bill = bill;
		this.maxGuestsNumber = maxGuests;
		
		if(bill > 0) {
			this.payRequired = true;
		}
		else {
			this.payRequired = false;
			this.payStatus = PaymentStatus.NOSET;
		}
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public int getMaxGuestsNumber() {
		return maxGuestsNumber;
	}

	public void setMaxGuestsNumber(int maxGuestsNumber) {
		this.maxGuestsNumber = maxGuestsNumber;
	}

	public int getGuestsNumber() {
		return guestsNumber;
	}

	public void setGuestsNumber(int guestsNumber) {
		this.guestsNumber = guestsNumber;
	}

	public GregorianCalendar getDateTime() {
		return dateTime;
	}

	public void setDateTime(GregorianCalendar dateTime) {
		this.dateTime = dateTime;
	}
	
	public int emptySpots() {
		return this.maxGuestsNumber - this.guestsNumber;
	}

	public GuestStatus getGuestStatus() {
		return guestStatus;
	}

	public void setGuestStatus(GuestStatus guestStatus) {
		this.guestStatus = guestStatus;
	}

	public PaymentStatus getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(PaymentStatus payStatus) {
		this.payStatus = payStatus;
	}

	public double getBill() {
		return bill;
	}

	public void setBill(double bill) {
		this.bill = bill;
	}

	public boolean isPayRequired() {
		return payRequired;
	}

	public void setPayRequired(boolean payRequired) {
		this.payRequired = payRequired;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
}
