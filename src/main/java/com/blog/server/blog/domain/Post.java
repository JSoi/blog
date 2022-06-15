package com.blog.server.blog.domain;

import com.blog.server.blog.dto.PostDto;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "post")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Post extends TimeStamped {

    @JsonIgnore
    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @Column
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Likes> likesList = new ArrayList<>();

    @JsonIgnore
    @Column
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    @Column(nullable = false, length = 25)
    private String title;

    @Column(nullable = false)
    @Lob
   private String content;

    @Column(nullable = false)
    private String image_url;

    @Column(name = "view_count")
    private Long viewCount = 0L;

    @Column(name = "like_count")
    private Long likeCount = 0L;

    @Column(length = 10)
    // Left, Right, Center
    private String templates = "Center";

    @Builder
    public Post(User user, String title, String content, String image_url) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.image_url = image_url;

    }

    public void update(PostDto.UpdatePost updatePost) {
        this.title = updatePost.getTitle();
        this.content = updatePost.getContent();
        this.image_url = updatePost.getImage_url();
    }

}
