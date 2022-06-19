package com.blog.server.blog.domain;

import com.blog.server.blog.dto.PostDto;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "post")
@Validated
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Post extends TimeStamped {

    @JsonIgnore
    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @JsonIgnore
    @Column
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Likes> likesList = new ArrayList<>();

    @JsonIgnore
    @Column
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    @Column(nullable = false, length = 25)
    @NotBlank(message = "제목을 입력하세요")
    @Size(max = 25, message = "제목은 최대 25자입니다")
    private String title;

    @Column(nullable = false)
    @NotBlank(message = "내용을 입력하세요")
    @Size(max = 3000, message = "내용은 3000자 이하로 작성해주세요")
    @Lob
    private String content;

    @Column(length = 300)
    private String imageUrl;

    private Long viewCount = 0L;

    private Long likeCount = 0L;

    // Left(1), Right(2), Center(3)
    private Long templates = 1L;

    @Builder
    public Post(User user, String title, String content, String imageUrl) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;

    }

    public void update(PostForm updatePost, String imageUrl) {
        this.title = updatePost.getTitle();
        this.content = updatePost.getContent();
        this.templates = updatePost.getTemplate();
        this.imageUrl = imageUrl;
    }

    @Builder
    public Post(PostForm postForm, String imageUrl, User user) {
        this.title = postForm.getTitle();
        this.content = postForm.getContent();
        this.templates = postForm.getTemplate();
        this.imageUrl = imageUrl;
        this.user = user;
    }
}
