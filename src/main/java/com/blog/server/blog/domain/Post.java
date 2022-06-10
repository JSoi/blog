package com.blog.server.blog.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Post extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="post_id")
    private Long id;

    @Column
    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER)
    private List<Likes> likesList;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String image_url;

    @Column(nullable = false)
    private int view_count;

}
