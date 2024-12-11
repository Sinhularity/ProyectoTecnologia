package uv.tcs.dognload.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "apellido_paterno_usuario", length = 12, nullable = false)
    private String paternal_surname;

    @Column(name = "apellido_materno_usuario", length = 12, nullable = false)
    private String martenal_surname;

    @Column(name = "nombre_usuario", length = 20, nullable = false)
    private String name;

    @Column(name = "correo_electronico_usuario", length = 50, nullable = false, unique = true)
    private String email;

    @Column(name = "telefono_usuario", length = 10, nullable = false, unique = true)
    private String phone_number;

    public User() {
    }

    public User(String paternal_surname, String martenal_surname, String name, String email, String phone_number) {
        this.paternal_surname = paternal_surname;
        this.martenal_surname = martenal_surname;
        this.name = name;
        this.email = email;
        this.phone_number = phone_number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaternal_surname() {
        return paternal_surname;
    }

    public void setPaternal_surname(String paternal_surname) {
        this.paternal_surname = paternal_surname;
    }

    public String getMartenal_surname() {
        return martenal_surname;
    }

    public void setMartenal_surname(String martenal_surname) {
        this.martenal_surname = martenal_surname;
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

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
