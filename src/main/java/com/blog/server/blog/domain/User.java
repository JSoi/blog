package com.blog.server.blog.domain;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
public class User extends TimeStamped implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 1, max = 10, message = "이름은 1자 이상 10자 이하입니다")
    @NotBlank(message = "이름을 입력하세요")
    @Column(nullable = false, length = 10)
    private String name;

    @Size(min = 3, max = 20, message = "별명은 3자 이상 20자 이하입니다")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "별명을 영어와 숫자로만 구성해주세요")
    @NotBlank(message = "별명을 입력하세요")
    @Column(nullable = false, length = 20)
    private String nickname;

    @Email(message = "올바른 이메일 형식을 입력해 주세요")
    @NotBlank(message = "아이디에 값을 입력하세요")
    @Size(max = 50, message = "아이디는 50자 이하로 입력하세요")
    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @JsonIgnore
    @NotBlank(message = "비밀번호에 값을 입력하세요")
    @Column(nullable = false)
    private String password;

    @Lob
    @Size(max = 1000, message = "자기소개를 1000자 이하로 작성해주세요")
    @Column(length = 1000)
    private String introduce;

    @Column(length = 300)
    private String profileImageUrl;

    @JsonIgnore
    @Column
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    @JsonIgnore
    @Column
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Likes> likesList = new ArrayList<>();

    @JsonIgnore
    @Column
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> postList = new ArrayList<>();


    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
