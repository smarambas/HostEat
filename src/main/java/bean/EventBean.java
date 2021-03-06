package bean;

public class EventBean {

	private String eventOwner;
	private int maxGuestsNumber;
	private int guestsNumber;
	private String dateTime;
	private String guestStatus;
	private String payStatus;
	private double bill;
	private String actualGuests;
	private String regionString;
	private String provinceString;
	private String cityString;
	private String addressString;
	private int guestRated;
	private int hostRated;
	
	public String getEventOwner() {
		return eventOwner;
	}
	
	public void setEventOwner(String eventOwner) {
		this.eventOwner = eventOwner;
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
	
	public String getDateTime() {
		return dateTime;
	}
	
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	
	public String getGuestStatus() {
		return guestStatus;
	}
	
	public void setGuestStatus(String guestStatus) {
		this.guestStatus = guestStatus;
	}
	
	public String getPayStatus() {
		return payStatus;
	}
	
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	
	public double getBill() {
		return bill;
	}
	
	public void setBill(double bill) {
		this.bill = bill;
	}
	
	public String getActualGuests() {
		return actualGuests;
	}
	
	public void setActualGuests(String actualGuests) {
		this.actualGuests = actualGuests;
	}

	public String getRegionString() {
		return regionString;
	}

	public void setRegionString(String regionString) {
		this.regionString = regionString;
	}

	public String getProvinceString() {
		return provinceString;
	}

	public void setProvinceString(String provinceString) {
		this.provinceString = provinceString;
	}

	public String getCityString() {
		return cityString;
	}

	public void setCityString(String cityString) {
		this.cityString = cityString;
	}

	public String getAddressString() {
		return addressString;
	}

	public void setAddressString(String addressString) {
		this.addressString = addressString;
	}

	public int getGuestRated() {
		return guestRated;
	}

	public void setGuestRated(int guestRated) {
		this.guestRated = guestRated;
	}

	public int getHostRated() {
		return hostRated;
	}

	public void setHostRated(int hostRated) {
		this.hostRated = hostRated;
	}
	
}
