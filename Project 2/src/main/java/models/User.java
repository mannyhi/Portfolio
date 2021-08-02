package models;

public class User {
	private String id;
	private String role;
	private String name;
	private String password;
	
	public User(String id, String role, String name, String password) {
		this.id = id;
		this.role = role;
		this.name = name;
		this.password = password;
	}
	public User() {
		super();
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", role=" + role + ", name=" + name + ", password=" + password
				+ "]";
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
}
