package com.blog.server.blog.controller;

import com.blog.server.blog.domain.Comment;
import com.blog.server.blog.domain.Post;
import com.blog.server.blog.domain.User;
import com.blog.server.blog.dto.Response;
import com.blog.server.blog.excpetion.BlogException;
import com.blog.server.blog.excpetion.ErrorCode;
import com.blog.server.blog.repository.LikesRepository;
import com.blog.server.blog.repository.PostPagingRepository;
import com.blog.server.blog.repository.PostRepository;
import com.blog.server.blog.repository.UserRepository;
import com.blog.server.blog.service.PostService;
import com.blog.server.blog.validaton.Validator;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.blog.server.blog.excpetion.ErrorCode.POST_NOT_EXIST;
import static com.blog.server.blog.excpetion.ErrorCode.USER_NOT_EXIST;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@ResponseStatus(HttpStatus.ACCEPTED)
public class PostController {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikesRepository likesRepository;
    private final PostService postService;
    private final PostPagingRepository PostPagingRepository;

    @GetMapping("/api/posts")
//    public List<PostResponse> getAllPost(@AuthenticationPrincipal User user,
//                                         @RequestParam(required = false) Integer size,
//                                         @RequestParam(required = false) Integer page) { // ?????????
    public ResponseEntity<Map<String,Object>> getAllPost(@AuthenticationPrincipal User user,
                                                         @PageableDefault(sort = {"likeCount"}, direction = Sort.Direction.DESC) Pageable pageable) { // ?????????
        List<Post> targetPostList;
        boolean end = true;
        if (pageable != null) {
            Page<Post> pageList = PostPagingRepository.findAllBy(pageable);
            targetPostList = pageList.getContent();
            end = !pageList.hasNext();
        } else {
            targetPostList = postRepository.findAllByOrderByLikeCountDesc();
        }
        List<PostResponse> result = new ArrayList<>();
        processPost(user, targetPostList, result);
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("end", end);
        responseMap.put("posts", result);
        return new ResponseEntity<>(responseMap,HttpStatus.OK);
    }


    private void processPost(User user, List<Post> targetPostList, List<PostResponse> result) {
        for (Post p : targetPostList) {
            PostResponse pr = new PostResponse(p);
            if (user != null) {
                pr.setLikeByMe(likesRepository.existsLikesByPostAndUser(p, user));
            }
            pr.setNickname(p.getUser().getNickname());
            result.add(pr);
        }
    }

    @PostMapping(value = "/api/posts", consumes = {"multipart/form-data"})
    public Response.Simple addPosts(@Valid @ModelAttribute PostForm postForm, BindingResult bindingResult,
                                    @AuthenticationPrincipal User user) {
        Validator.validateLoginUser(user, ErrorCode.NEED_LOGIN);
        postService.addNewPost(postForm, user);
        return Response.Simple.builder().build();
    }


    // ????????? ??????

    @GetMapping("/api/posts/{postId}")
    public PostResponse getPost(@PathVariable Long postId, @AuthenticationPrincipal User user) {
        Validator.validateLoginUser(user, ErrorCode.NEED_LOGIN);
        postService.plusView(postId); // ?????? ??? View??? ?????? ???????????? ??????
        Post targetPost = postRepository.findById(postId).orElseThrow(() -> new BlogException(POST_NOT_EXIST));
        User targetUser = userRepository.findById(user.getId()).orElseThrow(() -> new BlogException(USER_NOT_EXIST));
        return new PostResponse(targetPost);
    }

    @DeleteMapping("/api/posts/{postId}")
    public Response.Simple deletePost(@PathVariable Long postId, @AuthenticationPrincipal User user) {
        Validator.validateLoginUser(user, ErrorCode.NEED_LOGIN);
        postRepository.findById(postId).orElseThrow(() -> new BlogException(POST_NOT_EXIST));
        postRepository.deleteById(postId);
        return Response.Simple.builder().build();
    }

    @PutMapping(value = "/api/posts/{postId}", consumes = {"multipart/form-data"})
    public Response.Simple fixPost(@PathVariable Long postId, @Valid @ModelAttribute PostForm requestDto,
                                   @AuthenticationPrincipal User user) {
        Validator.validateLoginUser(user, ErrorCode.NEED_LOGIN);
        postService.update(postId, requestDto, user);
        return Response.Simple.builder().build();
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @Data
    @AllArgsConstructor
    static class PostResponse {
        private Long id, likeCount, viewCount, template; // query ???????????? ^^;
        private String nickname, imageUrl, content, title, email;
        private LocalDateTime createdAt, modifiedAt;

        private List<CommentResponse> comment;
        ////????????????
        private boolean likeByMe = false;


        public PostResponse(Post post) {
            this.title = post.getTitle();
            this.id = post.getId();
            this.email = post.getUser().getEmail();
            this.content = post.getContent();
            this.imageUrl = post.getImageUrl();
            this.createdAt = post.getCreatedAt();
            this.modifiedAt = post.getModifiedAt();
            this.template = post.getTemplates();
            this.viewCount = post.getViewCount();
            this.comment = post.getCommentList().stream().map(CommentResponse::new).collect(Collectors.toList());
            this.likeCount = (long) post.getLikesList().size();
        }
    }

    @Data
    @AllArgsConstructor
    public static class CommentResponse {
        private Long id;
        private String content, email, nickname;
        private LocalDateTime createdAt, modifiedAt;

        public CommentResponse(Comment comment) {
            this.id = comment.getId();
            this.email = comment.getUser().getEmail();
            this.nickname = comment.getUser().getNickname();
            this.content = comment.getContent();
            this.createdAt = comment.getCreatedAt();
            this.modifiedAt = comment.getModifiedAt();
        }
    }

    @Validated
    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class PostForm {
        @NotBlank(message = "????????? ?????? ???????????????")
        private String title;
        @NotBlank(message = "????????? ?????? ???????????????")
        private String content;
        private MultipartFile image;
        private Long template = 1L;
    }
    @Data
    @AllArgsConstructor
    @Builder
    public static class PostResponseAll {
        private boolean end = false;
        private List<PostResponse> postResponseList = new ArrayList<>();
    }
}
