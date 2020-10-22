package lv.javaguru.chatbot.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

	@Column(name = "telegram_id", nullable = false, unique = true)
    private String telegramId;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "phone")
	private String phone;

	@Column(name = "email")
	private String email;

	@Column(name = "is_admin", nullable = false)
    private boolean admin = false;

	public String getTelegramId() {
		return telegramId;
	}

	public void setTelegramId(String telegramId) {
		this.telegramId = telegramId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		if (!super.equals(o))
			return false;
		User user = (User) o;
		return Objects.equals(telegramId, user.telegramId) &&
				Objects.equals(firstName, user.firstName) &&
				Objects.equals(lastName, user.lastName) &&
				Objects.equals(phone, user.phone) &&
				Objects.equals(email, user.email) &&
				Objects.equals(admin, user.admin);
	}

	@Override
	public int hashCode() {
		return Objects
				.hash(super.hashCode(), telegramId, firstName, lastName, phone, email, admin);
	}
}
