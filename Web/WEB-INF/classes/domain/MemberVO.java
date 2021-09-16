package domain;

public class MemberVO {
	private String id;
	private String passwd;
	private String passwdCheck;
	private String name;
	private String email;

	public String getID() {
		return this.id;
	}

	public void setID(String id) {
		this.id = id;
	}

	public String getPasswd() {
		return this.passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getPasswdCheck() {
		return this.passwdCheck;
	}

	public void setPasswdCheck(String passwdCheck) {
		this.passwdCheck = passwdCheck;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}