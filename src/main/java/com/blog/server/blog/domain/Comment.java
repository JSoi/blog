package com.blog.server.blog.domain;

import com.blog.server.blog.dto.CommentDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "comment")
public class Comment extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String content;

    @Builder
    public Comment(Post post, User user, String content) {
        this.content = content;
        this.post = post;
        this.user = user;
    }

    public void update(CommentDto commentDto) {
        this.content = commentDto.getContent();
    }
}
