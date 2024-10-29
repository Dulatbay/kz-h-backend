package com.example.kzh.entities;

import com.example.kzh.entities.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "kzh_user")
public class User extends AbstractEntity<Long> implements UserDetails {

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private DetailUser detailUser;

    @OneToMany(mappedBy = "user",
            fetch = FetchType.LAZY)
    private Set<Question> questions;

    @OneToMany(mappedBy = "user",
            fetch = FetchType.LAZY)
    private Set<RatingQuiz> ratingQuizzes;

    @OneToMany(mappedBy = "user",
            fetch = FetchType.LAZY)
    private Set<Quiz> quizzes;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<PassedQuiz> passedQuizzes;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<PassedQuestion> passedQuestions;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Notes> notes;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Variant> variants;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
