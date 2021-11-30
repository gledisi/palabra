package com.city.user.entity;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static com.city.user.util.Constants.DB_SCHEMA;

@Entity
@Table(name = "user_contact", schema = DB_SCHEMA)
public class UserContactEntity implements Serializable {
    private static final long serialVersionUID = 1933922682791931931L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "contact_id")
    private List<UserEntity> contacts;

    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn;

    @Version
    private Short version;

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

    public List<UserEntity> getContacts() {
        return contacts;
    }

    public void setContacts(List<UserEntity> contacts) {
        this.contacts = contacts;
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
