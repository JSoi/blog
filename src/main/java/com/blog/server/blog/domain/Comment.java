package com.blog.server.blog.domain;

import com.blog.server.blog.dto.CommentDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "comment")
public class Comment extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "postId")
    private Post post;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Size(min = 1, max = 1000, message = "댓글 내용은 1자 이상, 1000자 이하여야 합니다")
    @Column(length = 1000)
    private String content;


    public Comment(Post post, User user, CommentDto commentDto) {
        this.content = commentDto.getContent();
        this.post = post;
        this.user = user;
    }

    public void update(CommentDto commentDto) {
        this.content = commentDto.getContent();
    }
}
