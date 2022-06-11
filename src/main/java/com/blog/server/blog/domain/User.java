package com.blog.server.blog.domain;


import com.blog.server.blog.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class User extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @Column(nullable = false, length = 10)
    private String name;

    @Column(nullable = false, length = 20)
    private String nickname;

    @Column(nullable = false, unique = true, length = 20)
    private String email;

    @Column(nullable = false, length = 20)
    private String password;

    @Column
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    @Column
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Likes> likesList = new ArrayList<>();

    @Column
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> postList = new ArrayList<>();

    @Column
    private String introduce;

    @Builder
    public User(String name, String nickname, String email, String password, List<Comment> commentList, List<Likes> likesList, List<Post> postList, String introduce) {
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.commentList = commentList;
        this.likesList = likesList;
        this.postList = postList;
        this.introduce = introduce;
    }

    public User(UserDto.Register registerDto) {
        User.builder().name(registerDto.getName())
                .email(registerDto.getEmail())
                .introduce(registerDto.getIntroduce())
                .password(registerDto.getPassword())
                .nickname(registerDto.getNickname()).build();
    }


}
