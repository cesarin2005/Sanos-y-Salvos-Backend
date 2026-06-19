package com.sanosysalvos.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AuthDtos {

    public static class RegisterRequest {
        @NotBlank(message = "El nombre es requerido")
        private String name;
        @NotBlank @Email(message = "Email inválido")
        private String email;
        @NotBlank @Size(min = 6, message = "Mínimo 6 caracteres")
        private String password;
        @NotBlank(message = "El teléfono es requerido")
        private String phone;
        private String address;
        private String city;

        public String getName() { return name; }
        public void setName(String v) { this.name = v; }
        public String getEmail() { return email; }
        public void setEmail(String v) { this.email = v; }
        public String getPassword() { return password; }
        public void setPassword(String v) { this.password = v; }
        public String getPhone() { return phone; }
        public void setPhone(String v) { this.phone = v; }
        public String getAddress() { return address; }
        public void setAddress(String v) { this.address = v; }
        public String getCity() { return city; }
        public void setCity(String v) { this.city = v; }
    }

    public static class LoginRequest {
        @NotBlank @Email
        private String email;
        @NotBlank
        private String password;

        public String getEmail() { return email; }
        public void setEmail(String v) { this.email = v; }
        public String getPassword() { return password; }
        public void setPassword(String v) { this.password = v; }
    }

    public static class AuthResponse {
        private String token;
        private String type = "Bearer";
        private Long userId;
        private String name;
        private String email;
        private String role;

        public String getToken() { return token; }
        public void setToken(String v) { this.token = v; }
        public String getType() { return type; }
        public void setType(String v) { this.type = v; }
        public Long getUserId() { return userId; }
        public void setUserId(Long v) { this.userId = v; }
        public String getName() { return name; }
        public void setName(String v) { this.name = v; }
        public String getEmail() { return email; }
        public void setEmail(String v) { this.email = v; }
        public String getRole() { return role; }
        public void setRole(String v) { this.role = v; }

        public static Builder builder() { return new Builder(); }
        public static class Builder {
            private final AuthResponse r = new AuthResponse();
            public Builder token(String v) { r.token = v; return this; }
            public Builder userId(Long v) { r.userId = v; return this; }
            public Builder name(String v) { r.name = v; return this; }
            public Builder email(String v) { r.email = v; return this; }
            public Builder role(String v) { r.role = v; return this; }
            public AuthResponse build() { return r; }
        }
    }
}