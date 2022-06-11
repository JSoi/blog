# Blog

1. 회원 가입 페이지
   - 회원 가입을 구현할 때 인증 인가 방식은 자유롭게 선택해주시면 됩니다.
   - 닉네임은 `최소 3자 이상, 알파벳 대소문자(a~z, A~Z), 숫자(0~9)`로 구성하기
   - 비밀번호는 `최소 4자 이상이며, 닉네임과 같은 값이 포함된 경우 회원가입에 실패`로 만들기
   - 비밀번호 확인은 비밀번호와 정확하게 일치하기
2. 로그인 페이지
   - 로그인 버튼을 누른 경우 닉네임과 비밀번호가 데이터베이스에 등록됐는지 확인한 뒤, 하나라도 맞지 않는 정보가 있다면 "닉네임 또는 패스워드를 확인해주세요"라는 메세지를 프론트엔드에서 띄워줄 수 있도록 예외처리 하기
3. 로그인 검사
   - 로그인 하지 않은 사용자도, 게시글 목록 조회는 가능하도록 하기
   - 로그인하지 않은 사용자가 좋아요 버튼을 눌렀을 경우, "로그인이 필요합니다." 라는 메세지를 프론트엔드에서 띄워줄 수 있도록 예외처리 하기
   - 로그인 한 사용자가 로그인 페이지 또는 회원가입 페이지에 접속한 경우 "이미 로그인이 되어있습니다."라는 메세지로 예외처리하기
   - 인증 인가를 어떤 개념(Token/Session)을 채택 했는지, 그 이유에 대해서 설명하기
4. CORS 해결하기
   - CORS란 무엇이며, 어떤 상황에서 일어나는지 / 어떻게 해결하는지 알아보고, 프로젝트에 적용하기
5. 좋아요 순 정렬(정렬하기는 꼭 해봐야 하는 건데 과제에 없다)
   - 정렬 기준 중 하나를 선택해주세요!
     - 생성일 순
     - 좋아요 순
     - view 순