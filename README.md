



# Blog-Server

### 목차

[요구 사항](# 요구사항)

[ERD](# ERD)

[REST API](# REST API)

[필수과제](# 필수과제)

[인증/인가](#인증, 인가)

[My Issue](# My Issue)

[앞으로 공부할 것](#앞으로-공부할-것)



## 요구사항

1. 회원 가입 페이지
   - 닉네임은 `최소 3자 이상, 알파벳 대소문자(a~z, A~Z), 숫자(0~9)`로 구성하기
   - 비밀번호는 `최소 4자 이상이며, 닉네임과 같은 값이 포함된 경우 회원가입에 실패`로 만들기
   - 비밀번호 확인은 비밀번호와 정확하게 일치하기
2. 로그인 페이지
   - 로그인 버튼을 누른 경우 닉네임과 비밀번호가 데이터베이스에 등록됐는지 확인한 뒤, 하나라도 맞지 않는 정보가 있다면 "닉네임 또는 패스워드를 확인해주세요"라는 메시지를 띄울 수 있게 예외 처리한다.
3. 로그인 검사
   - 로그인 하지 않은 사용자도, 게시글 목록 조회는 가능하도록 하기
   - 로그인하지 않은 사용자가 좋아요 버튼을 눌렀을 경우, "로그인이 필요합니다." 라는 메시지를 띄울 수 있게 예외 처리한다.
   - 로그인 한 사용자가 로그인 페이지 또는 회원가입 페이지에 접속한 경우 "이미 로그인이 되어있습니다."라는 메세지로 예외 처리하기
   - <u>**인증 인가를 어떤 개념(Token/Session)을 채택 했는지, 그 이유에 대해서 설명**</u>하기
4. CORS 해결하기
   - **<u>CORS란 무엇이며, 어떤 상황에서 일어나는지 / 어떻게 해결하는지 알아보고, 프로젝트에 적용하기</u>**
5. 좋아요 순 정렬(정렬하기는 꼭 해봐야 하는 건데 과제에 없다)
   - 정렬 기준 중 하나를 선택해주세요!
     - 생성일 순
     - 좋아요 순✔
     - view 순


## ERD

[노션 링크](https://teamsparta.notion.site/4-c4ddea873ddd41ad94bd215f2743598c)

![dd](https://user-images.githubusercontent.com/17975647/173787724-fa53b884-848d-469f-a4de-aef047f6c57b.png)







## REST API

- [노션 링크](https://teamsparta.notion.site/4-c4ddea873ddd41ad94bd215f2743598c)
![aa](https://user-images.githubusercontent.com/17975647/174419197-ce66f015-e4e5-4fdf-8853-c0d2336b54bd.png)

## 필수과제

- **프레임워크와 라이브러리의 차이점** 👉 제어 흐름에 대한 주도성이 누구에게/어디에 있는가에 따라 다르다
  - 프레임워크
    - 뼈대나 기반 구조를 뜻하고, 제어의 역전 개념이 적용된 대표적인 기술
    - 특정 개념들의 추상화를 제공하는 여러 클래스나 컴포넌트로 구성 (컴포넌트들은 재사용 가능)
  - 라이브러리
    - 라이브러리는 단순 활용 가능한 도구들의 집합
    - 개발자가 만든 클래스에서 호출하여 사용
    - 클래스들의 나열로 필요한 클래스를 불러서 사용하는 방식
- 코드를 구현할 때 예외 처리를 위해 무엇을 했나요?
  - 예외 상황이 발생할 수 있는 경우의 수에 대해 생각해 보고, 구현 방식을 생각했다.
    - 예외 상황은 서버에서도 많이 발생하지만, 외부에서 들어오는 값에서 발생할 수 있는 예외를 생각했다.
    - 예를 들어, 회원가입하려는 사람이 공백을 입력하거나, 아이디에 특수 문자가 들어와 처리가 어려워지는 경우를 처리해 주었다.
  - 저번 과제와 마찬가지로 프로젝트 전역에서 발생할 수 있는 에러를 잡는 RestControllerAdvice를 사용하였다.

- Restful이란?
  - “Representational State Transfer” 의 약자
  - 분산 시스템 설계를 위한 제약 조건의 집합
  - HTTP URI(Uniform Resource Identifier)를 통해 자원(Resource)을 명시하고, HTTP Method(POST, GET, PUT, DELETE)를 통해 해당 자원에 대한 CRUD Operation을 적용하는 것을 의미한다.
  - **자원의 표현에 의한 상태 전달**
- 왜 Restful하게 짜야하나요?
  - 최근의 서버 프로그램은 모바일 디바이스에서도 통신을 할 수 있어야 한다.
    이러한 멀티 플랫폼에 대한 지원을 위해 서비스 자원에 대한 아키텍처를 세우고 이용하는 방법을 모색한 결과, REST에 관심을 가지게 되었다.
- Restful의 장/단점
  - 장점 : 
    - HTTP 프로토콜의 인프라를 그대로 사용하므로 REST API 사용을 위한 별도의 인프라를 구출할 필요가 없다.
    - HTTP 표준 프로토콜에 따르는 모든 플랫폼에서 사용이 가능하다.
    - REST API 메시지가 의도하는 바를 명확하게 나타내므로 의도하는 바를 쉽게 파악할 수 있다.
  - 단점
    - 표준이 존재하지 않는다
    - 사용할 수 있는 메서드가 4가지이기 때문에 형태가 제한적이다
- Restful의 대안은?
  - GraphQL
    - GraphQL은 애플리케이션 프로그래밍 인터페이스(API)를 위한 쿼리 언어이다
    -  GraphQL에서는 API서버에서 엄격하게 정의된 endpoint 들에 요청하는 대신, 한번의 요청으로 정확히 가져오고 싶은 데이터를 가져올 수 있게 도와주는 쿼리를 보낼 수 있다.
    - GraphQL에서는 URL을 Resource를 특정 짓는 것에 사용하지 않고 GraphQL Schema가 Resource를 특정 짓는다.
    - HTTP Method로 어떤 작업을 진행하게 되는지 구분하지 않고, Query, Mutation이라는 타입을 사용해 구분한다.
- Restful하게 짜기 위해 무엇을 고려했나요?
  - 각각의 엔드포인트가 자원을 대표하고 행위를 나타낼 수 있도록 설계했다.
  - 현재 ```/api``` 를 빼야 할지 고민중이다
- Entity 설계를 위해 무엇을 하였나요? 연관관계에 근거하여 설명해주세요.
  - 위에 첨부한 ERD가 설계 결과이며,
    - User-Like = 1:N 👉 유저는 다양한 게시물에 Like를 표시할 수 있다
    - User-Post = 1:N 👉 유저는 많은 게시물을 작성 가능하다 
    - Post-Like = 1:N  👉 하나의 포스트에는 여러개의 좋아요가 달릴 수 있다.
    - Post-Comment = 1:N 👉 포스트에는 여러 댓글이 달릴 수 있다.
    - User-Comment : 1:N 👉 유저는 여러개의 댓글을 작성할 수 있다.
  - 여기서 Like는 마치 User와 Post사이에 끼어 생긴 부산물 같은 느낌이라 Post-User를 M:N으로 표시할 수 있을 것 같다
  - JPA에서는 M:N을 다대일&일대다로 접근하는 것 같다.



## 인증, 인가


#### 인증

- 유저가 누구인지 확인하는 절차이다 - 어떤 개체(사용자 또는 장치)의 신원을 확인하는 과정

- 예) 회원가입하고 로그인 하는 것.



#### 인가

- 유저에 대한 권한을 허락하는 것.

- 어떤 개체가 어떤 리소스에 접근할 수 있는지 또는 어떤 동작을 수행할 수 있는지를 검증하는 것

  

이 중 서버의 부담이 적고 인가가 수월한 JWT를 선택하였다.



#### JWT의 장점 

- 검증하기 위한 DB가 따로 필요하지 않다.
- 생성된 토큰을 추적할 필요가 없다
- 트래픽에 대한 부담이 낮음
- 데이터를 사인하고, 유저에게 보내고 데이터를 돌려받을 때 유효성을 검증한다




## CORS

**(Cross-Origin Resource Sharing,CORS)** 란 다른 출처의 자원을 공유할 수 있도록 설정하는 권한 체제이다

따라서 **CORS**를 설정해주지 않거나 제대로 설정하지 않은 경우, 원하는대로 리소스를 공유하지 못하게 된다.

Spring Boot에서 CORS 설정하기

1. Configuration 적용하기

2. Annotation Controller에 붙이기 ```@CrossOrigin(origins = "*", allowedHeaders = "*")```

에서 좀 더 간단한 2번을 선택하여 구현하였다. 아직 검증해 보지 않아서 작동하지 않으면 1번의 방법으로 수정할 예정이다. -> 아직까지는 잘 작동한다!!



## My Issue



###  DTO가 너무 많아진다

구현을 진행할수록 너무 많은 DTO가 생기고, 유사한 이름들이 많아지면서 헷갈리기 시작했다.

검색을 해보니 DTO 클래스 내에 static으로 선언해서 static member class로 쓰는 방식이 있었고, 이를 이번 과제에 적용해 보았다.

**[코드 - DTO]**

```java
public class CommentDto {
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter @Setter
    @Builder
    public static class NewComment {
        Long user_id;
        Long post_id;
        String content;
    }
```
**[코드 - Service에서의 static member class 적용]**

```java
@Transactional
public void addComment(Long id, CommentDto.NewComment newCommentDto) { // CommentDto.NewComment를 쓴다!
```



Inner class를 적용하기 위해서는 static을 꼭 붙여 주어야 한다.



**만약 <u>일반 내부 클래스</u>를 쓰게 되면?**

👎 1) 자신을 호출한 클래스에 대한 참조를 하게 되고 , 시간/공간적으로 성능이 낮아진다

👎 2) GC가 수거하지 못해 메모리 누수가 생긴다.



### 엔티티 대신 DTO로 Respose 보내기

저번 주에는 Response를 반환할 때 엔티티를 그대로 외부에 노출했다(Post, Comment 등...)

이번에는 조회 시에 필요로 하는 필드만 뽑아서 DTO를 만들어 보았다.

**[코드]**

```java
@Data
@AllArgsConstructor
public static class CommentResponse {
    private Long comment_id;
    private String content;
    private LocalDateTime createdAt, modifiedAt;

    public CommentResponse(Comment comment) {
        this.comment_id = comment.getId();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();

    }
}
```

**[결과]**

![image](https://user-images.githubusercontent.com/17975647/174424349-ddbb3233-3d8d-401d-9906-3b3ad4279873.png)

더 깔끔해졌다!

원하는 데이터 형식으로 뽑아내기에 적합한 방법이라는 생각이 든다.

만약 프론트에서 원하는 Response 형태가 있다면 그에 맞춰서 보내 줄 수 있어서 좋았다



### DTO에서의 Setter 사용 지양

DTO든 어디든 Setter는 되도록 사용하지 않는게 좋다고 한다

이를 대신해 Builder 패턴을 권장하고 있다.



### 예외 처리 및 유효성 검사

우선 예외 처리와 유효성 검사에 대해 개념을 짧게 정리해 보자면

- **유효성 검사** : JSON👉DTO 로 Data를 객체에 옮겨 담으면서 길이나 형식을 체크하는 것

- **예외 처리** : 사용자의 실수, 서버의 오류 등으로 인한 요청이 실패한 경우에 에러를 처리

예외 처리와 유효성 검사는 함께 따라가는 개념인 것 같은데, 



#### 유효성 검사

Hibernate bean validator를 사용해서 유효성 검사를 진행했다.

검사는 **DTO**에서 이루어지며, **엔티티**에도 적용할 수 있다.

처음에는 User의 Input값이 들어 있는 DTO에서만 유효성 검사를 진행하려 했으나,

아래 링크를 보고 둘 다 진행하게 되었다😅

<https://stackoverflow.com/questions/42280355/spring-rest-api-validation-should-be-in-dto-or-in-entity>

**[DTO에서의 유효성 검사]**

```java
@Validated
public class CommentDto {
    @NotBlank(message = "댓글에 내용을 입력하세요")
    @Size(max = 1000, message = "내용을 1000자 이하로 입력하세요")
    String content;
}
```

**[Entity에서의 유효성 검사]**

```java
@Table(name = "comment")
@Validated
public class Comment extends TimeStamped {
    @Size(min = 1, max = 1000, message = "댓글 내용은 1자 이상, 1000자 이하여야 합니다")
    @Column(length = 1000)
    private String content;
    ..
}
```

아직 의문인 건 Valid를 이렇게 남용해도 되는 것인지, 좀 더 좋은 방법이 없을까 하는 것이다.



**[로그인, 페이지 접근 관련 유효성 검사]**

```java
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public static void validateLoginUser(User user, ErrorCode code) {
    if (user == null) {
        throw new BlogException(code);
    }
}

@ResponseStatus(HttpStatus.LOCKED)
public static void alreadyLoggedIn(User user, ErrorCode code) {
    if (user != null) {
        throw new BlogException(code);
    }
}
```





### 예외

저번 과제와 마찬가지로 RestControllerAdvice를 사용해 전반적인 에러를 관리했다.

이번 프로젝트에서 발생하는 Exception은 아래와 같다.

1. **DTO 검증시 발생하는** 
   1. MethodArgumentNotValidException : Entity, DTO의 검증시 발생
   2. ConstraintViolationException : FormData의 DTO 검증시 발생
   3. MaxUploadSizeExceededException : 이미지 업로드시 제한 용량 오버시 발생

2. **직접 커스텀한**
   1. BlogException  : 프로젝트 전역에서 중복 아이디, 잘못 된 로그인, 없는 포스트 번호 등 규칙을 할 때 발생



**1-1~3(MethodArgumentNotValid, ContstraintViolation, MaxUploadSizeExceeded) 의 경우**

```java
@ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<ErrorResponse> validException(
        MethodArgumentNotValidException ex) {
    String responseStr = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
    return argumentErrors(responseStr); // 2
}

@ExceptionHandler(ConstraintViolationException.class)
public ResponseEntity<ErrorResponse> validExceptionNotControlled(ConstraintViolationException cve) {
    return argumentErrors(cve.getConstraintViolations().iterator().next().getMessage());
}

@ExceptionHandler(MaxUploadSizeExceededException.class)
public ResponseEntity<ErrorResponse> handleMaxSizeException(MaxUploadSizeExceededException exc) {
    return argumentErrors(ErrorCode.IMAGE_SIZE_EXCEEDED.getDetail());
}
```



**2-1 (BlogException)**

```java
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = BlogException.class)
    protected ResponseEntity<ErrorResponse> handleCustomException(BlogException e) {
        log.error("handleCustomException throw CustomException : {}", e.getErrorCode());
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }
}
```



더 많은 예외가 있을 것 같은데 시간이 촉박해서 커버하지 못했다!

시간이 되면 다음 과제를 진행하면서 더 구현해보고 싶다😃



### JPQL..

콘솔창에 올라가는 SQL문들을 보고 있노라면 쿼리가 많아 신경이 쓰인다

```java
@Modifying
@Query("update Post p set p.view_count = p.view_count + 1 where p.id = :id")
void updateView(Long id);

@Modifying
@Query("update Post p set p.likeCount = p.likeCount + :value where p.id = :id")
void updateLikeCount(Long id, Long value);
```

그래서 쿼리의 양이 더 늘어나는 것을 피하고 싶어 JPQL로 Repository에 간단하게 구현해 보았다.

이후에 Fetchjoin같은 경우에도 쿼리를 작성해서 사용하는 것 같은데, 더 공부해 놓아야 될 것 같다.



### 어려운 Security

시큐리티 관련 지식이 전무해서 코드에서도 그 점이 많이 드러났던 것 같다

```java
@GetMapping("/user")
public UserInfo userInfo(@AuthenticationPrincipal User user) {
    Validator.validateLoginUser(user, ErrorCode.NEED_LOGIN);
    User findUser = userRepository.findById(user.getId()).orElseThrow(()
            -> new BlogException(ErrorCode.USER_NOT_EXIST));
    return new UserInfo(findUser);
}
```

지금은 수정된 상태지만, 이렇게 User를 중복 검사하는 실수도 잦았다.

이런 실수를 줄이려면 시큐리티 관련 기초를 더 쌓아야 될 것 같다




### 서버시간 동기화 문제

application.properties 에 다음 내용 추가

```
spring.datasource.url={내DB}?serverTimezone=Asia/Seoul
```

```java
@PostConstruct
public void started() {
   // timezone UTC 셋팅
   TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
}
```

이것때문에 서버를 지우고 새로 만들면서 한참을... 헤맸기 때문에 써 넣어본다😅



## 앞으로 공부할 것

연관관계가 늘어나면서 쿼리문이 늘어나고 심지어는 N+1 문제가 일어나기도 한다.



이를 해결하기 위해서는 

- Fetch Join
- Lazy Loading


등이 필요하다. 

Fetch Join을 할 때 User같이 ToMany가 여러 개 인 경우에는 어떻게 해야 할지 고민이 많았는데, 아래의 조건을 사용하면 될 것 같다.

>Fetch Join의 조건은 다음과 같습니다.
>
>- **ToOne은 몇개든** 사용 가능합니다
>- **ToMany는 1개만** 가능합니다.
>- 보통 해결책은 하나만 fetch join 합니다.

해당 조건을 기반으로 이번 기회에 적용해 보려고 했는데, 댓글까지 추가적으로 구현하는 바람에 연관 관계가 조금 더 복잡해져 시간이 더 걸릴 것 같다.

그래서 DTO로 쿼리를 날리는 방법을 해 보고 있었는데 진행이 어렵다. 이런 경우 QueryDSL을 쓰기도 한다는데..허허 😅 더 공부해야겠다. 갈길이 멀다!

