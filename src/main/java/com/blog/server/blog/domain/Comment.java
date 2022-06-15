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
    @JoinColumn(name = "post_id")
    private Post post;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    @NotBlank(message = "빈 칸을 입력하지 마세요")
    private String content;

    @Builder
    public Comment(Post post, User user, String content) {
        this.content = content;
        this.post = post;
        this.user = user;
    }

    public void update(CommentDto.UpdateComment commentDto) {
        this.content = commentDto.getContent();
    }
}
