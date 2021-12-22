package com.city.user.entity;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static com.city.user.util.Constants.DB_SCHEMA;

@Entity
@Table(name = "contact", schema = DB_SCHEMA)
public class UserContactEntity implements Serializable {
    private static final long serialVersionUID = 1933922682791931931L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "contact_id")
    private UserEntity contact;

    @Column(name = "name")
    private String name;

    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn;

    @Version
    private Short version;

    public UserEntity getContact() {
        return contact;
    }

    public void setContact(UserEntity contact) {
        this.contact = contact;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Short getVersion() {
        return version;
    }

    public void setVersion(Short version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserContactEntity)) return false;
        UserContactEntity that = (UserContactEntity) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }


}
