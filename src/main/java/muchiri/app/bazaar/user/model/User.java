package muchiri.app.bazaar.user.model;

import java.time.Instant;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public sealed class User permits Seller, Bidder {
    private long id;
    @NotBlank(message = "username is required")
    private String username;
    @NotBlank(message = "name is required")
    private String name;
    @Email(message = "email is invalid")
    private String email;
    @NotBlank(message = "password is required")
    private String password;
    @NotBlank(message = "phone is required")
    private String phone;
    private Role role;
    private boolean activated;
    private Instant createdAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", phone=" + phone
                + ", activated=" + activated + ", createdAt=" + createdAt + "]";
    }
}
