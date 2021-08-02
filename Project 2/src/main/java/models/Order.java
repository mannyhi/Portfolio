package models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;

public class Order {
	private Integer id;
	private String customerId;
	private String status;
	private String items;
	private BigDecimal total; 
	private Timestamp timePlaced;
	private Timestamp timeCompleted;
	private String employeeId;
	private String notes;
	
	// Constructor with all values included as args
	public Order(int id, String customerId, String status, String items, BigDecimal total, Timestamp timePlaced,
			Timestamp timeCompleted, String employeeId, String notes) {
		this.id = id;
		this.customerId = customerId;
		this.status = status;
		this.items = items;
		this.total = total.setScale(2, RoundingMode.HALF_UP);
		this.timePlaced = timePlaced;
		this.timeCompleted = timeCompleted;
		this.employeeId = employeeId;
		this.notes = notes;
	}
	
	// Constructor with the values that we'll use when a user places a new order
	public Order(String customerId, String items, BigDecimal total, Timestamp timePlaced) {
		this.id = null;
		this.customerId = customerId;
		this.status = "order placed";
		this.items = items;
		this.total = total.setScale(2, RoundingMode.HALF_UP);
		this.timePlaced = timePlaced;
		this.timeCompleted = null;
		this.employeeId = null;
		this.notes = null;
	}
	
	// noargs Constructor
	public Order() {
		super();
	}
	
	@Override
	public String toString() {
		return "Order [id=" + id + ", customerId=" + customerId + ", status=" + status + ", items=" + items + ", total=$"
				+ total + ", timePlaced=" + timePlaced + ", timeCompleted=" + timeCompleted + ", employeeId="
				+ employeeId + ", notes=" + notes + "]";
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total.setScale(2, RoundingMode.HALF_UP);
	}

	public Timestamp getTimePlaced() {
		return timePlaced;
	}

	public void setTimePlaced(Timestamp timePlaced) {
		this.timePlaced = timePlaced;
	}

	public Timestamp getTimeCompleted() {
		return timeCompleted;
	}

	public void setTimeCompleted(Timestamp timeCompleted) {
		this.timeCompleted = timeCompleted;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	
	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

}
