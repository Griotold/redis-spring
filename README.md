# 실습 1.
1. 내 블로그 글 별 조회수를 Redis로 확인하고 싶다.
    1. 블로그 URL의 PATH는 `/articles/{id}` 형식이다.
    2. 로그인 여부와 상관없이 새로고침 될때마다 조회수가 하나 증가한다.
    3. 이를 관리하기 위해 적당한 데이터 타입을 선정하고,
    4. 사용자가 임의의 페이지에 접속할 때 실행될 명령을 작성해보자.

---

- String - INCE(++), DECR(--)
- INCR aricles:{id}

```INCR articles:1```

- 오늘의 조회수를 따로 관리하고 싶다면?

```INCR articles:1:today```

```RENAME articles:1:today articles:20XX-XX-XX```

2. 블로그에 로그인한 사람들의 조회수와 가장 많은 조회수를 기록한 글을 Redis로 확인하고 싶다.
    1. 블로그 URL의 PATH는 `/articles/{id}` 형식이다.
    2. 로그인 한 사람들의 계정은 영문으로만 이뤄져 있다.
    3. 이를 관리하기 위해 적당한 데이터 타입을 선정하고,
    4. 사용자가 임의의 페이지에 접속할 때 실행될 명령을 작성해보자.
    5. 만약 상황에 따라 다른 명령이 실행되어야 한다면, 주석으로 추가해보자.

```FLUSHDB```

- Set

```SADD articles:1 alex```

```SADD articles:1 brad```

```SADD articles:1 chad```

```SCARD articles:1```

- SADD의 결과에 따라 명령어를 실행하거나 말거나
- 0은 스킵
- 1일 경우? Sorted Set에 넣어주자
- ZINCRBY key score value 

```ZINCRBY articles:ranks 1 articles:1```

```ZINCRBY articles:ranks 1 articles:2```

```ZRANGE articles:ranks 0 -1```

- ZREVRANCE는 Deprecated 예정

```ZREVRANGE articles:ranks 0 1```

```ZRANGE articles:ranks 0 0 REV```

# 실습 2. 새로운 Spring Boot 프로젝트를 만들어 다음을 진행해보자.

1. 주문 ID, 판매 물품, 갯수, 총액, 결제 여부에 대한 데이터를 지정하기 위한 `ItemOrder` 클래스를 `RedisHash`로 만들고,
    1. 주문 ID - String
    2. 판매 물품 - String
    3. 갯수 - Integer
    4. 총액 - Long
    5. 주문 상태 - String
2. 주문에 대한 CRUD를 진행하는 기능을 만들어보자.
    1. `ItemOrder`의 속성값들을 ID를 제외하고 클라이언트에서 전달해준다.
    2. 성공하면 저장된 `ItemOrder`를 사용자에게 응답해준다.

---