package com.sanosysalvos.user.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String phone;

    private String address;
    private String city;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.USER;

    private boolean active = true;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public enum Role { USER, ADMIN, VET, SHELTER }

    public User() {}

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public String getCity() { return city; }
    public Role getRole() { return role; }
    public boolean isActive() { return active; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setId(Long v) { this.id = v; }
    public void setName(String v) { this.name = v; }
    public void setEmail(String v) { this.email = v; }
    public void setPassword(String v) { this.password = v; }
    public void setPhone(String v) { this.phone = v; }
    public void setAddress(String v) { this.address = v; }
    public void setCity(String v) { this.city = v; }
    public void setRole(Role v) { this.role = v; }
    public void setActive(boolean v) { this.active = v; }
    public void setCreatedAt(LocalDateTime v) { this.createdAt = v; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String name, email, password, phone, address, city;
        private Role role = Role.USER;
        private boolean active = true;

        public Builder name(String v) { this.name = v; return this; }
        public Builder email(String v) { this.email = v; return this; }
        public Builder password(String v) { this.password = v; return this; }
        public Builder phone(String v) { this.phone = v; return this; }
        public Builder address(String v) { this.address = v; return this; }
        public Builder city(String v) { this.city = v; return this; }
        public Builder role(Role v) { this.role = v; return this; }
        public Builder active(boolean v) { this.active = v; return this; }

        public User build() {
            User u = new User();
            u.name = this.name; u.email = this.email;
            u.password = this.password; u.phone = this.phone;
            u.address = this.address; u.city = this.city;
            u.role = this.role; u.active = this.active;
            u.createdAt = LocalDateTime.now();
            return u;
        }
    }
}