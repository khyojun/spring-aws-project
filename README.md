# 진행하면서 생겼던 오류

### 로컬, 서버에서 웹 페이지 실행 시 사용자명 오류
- {userName} : 윈도우의 환경변수
- {user} : ec2에서 ec2-user라는 값만 나오게 됨.


## 해결책

- userName, user라는 이름으로 안 만들고 다른 이름으로 해서 사용자 이름을 표시하는게 좋다.

### putty 실행시 일어났었던 오류

- jar 파일 실행시 ec2에서 프리티어로 제공하는 RAM은 1GB라서 가상메모리라는 개념을 사용했어야 했다.

생긴 오류는 다음과 같이 생겼다.

![](데몬오류.png)

??!

### 해결책

이 부족한 부분을 디스크의 일부를 대신 사용하도록 설정해줌 으로써 해결하는 방법이다. 가상메모리를 사용하는 것이다.

가상 메모리 = 물리 메모리 + 하드디스크의 swap영역 활용

- sudo dd if=/dev/zero of=/mnt/swapfile bs=1M count=2048
- sudo mkswap /mnt/swapfile
- sudo swapon /mnt/swapfile

위 세 개의 명령어를 순서대로 입력하면 스왑 메모리가 생성된다. 이렇게 해주면 스왑 메모리가 2GB 잡혀서 메모리 부족으로 빌드가 멈추는 현상은 사라지지만, 디스크는 RAM 보다 훨씬 속도가 느리기 때문에 서비스에 퍼포먼스 문제가 발생할 수 있다고 한다. 그래서 이 방법은 임시방편으로 쓰고 사양을 올려야 한다.

이것을 통해서 cs가 왜 중요한지 다시금 깨닫게 되었다.



## 배포시 S3 랑 Github Action 사용

- Travis CI 를 사용하려고 했는데 결제 방식이 조금 달라져서 바로 진행이 안되서 github Action으로 진행
- 계속 빌드하다가 Could not load credentials from any providers 문제 발생
  - 위 문제 발생시 확인해야 할 것!
    - permission의 권한을 줬는지 : https://github.com/aws-actions/configure-aws-credentials/issues/271
  - aws key 에 대한 설정을 repository secret에서 잘 했는지 꼭 확인하기!



### 출처(아래 링크를 참고하면서 진행)
- https://github.com/jojoldu/freelec-springboot2-webservice/issues/806



### 출처
- https://progdev.tistory.com/26