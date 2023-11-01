![header](https://capsule-render.vercel.app/api?type=waving&color=auto&height=200&section=header&text=Project%20MeongNyangBook&fontSize=70)

스프링 부트 반려동물 쇼핑/커뮤니티 프로젝트
----
[![Hits](https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fgithub.com%2FBSB99%2FProject-MeongNyangBook&count_bg=%23555B51&title_bg=%2367DA22&icon=spring.svg&icon_color=%23E7E7E7&title=view&edge_flat=false)](https://hits.seeyoufarm.com)


---

## 💫프로젝트 팀원

##### 리더 : 김슬기

* https://github.com/wisdomSG
* https://wisdomsg.tistory.com/
* 추가사항

##### 부리더 : 백승범

* https://github.com/BSB99?tab=repositories
* https://an-unreachable-world.tistory.com/
* 추가사항

##### 팀원 : 강정훈

* https://github.com/skah1061
* https://codeplace.tistory.com/
* 추가사항

## 🐶멍냥북

---

> ##### 스파르타 코딩클럽 내일배움캠프 스프링 6기
> ##### 개발기간 : 2023/08/16 ~ 2023/09/18

## 📇배포 주소

---
> 웹사이트 주소 : https://meongnyangbook.site/

![meongnyangbook](https://github.com/BSB99/Project-MeongNyangBook/assets/81159848/b5519bc3-51e5-4118-9837-1917cdf656de)

## 🙇‍♂️프로젝트 소개

---

#### MeongNyangBook

###### 저희 멍냥북은 유기동물의 분양을 돕는 ‘분양’과 나의 반려동물을 자랑하고 의견을 주고받을수있는 공간인 ‘커뮤니티’와 반려동물에게 필요한 모든 것을 사줄 수 있는 ‘쇼핑’으로 이루어져있습니다. 반려동물과 관련된 모든 것을 할 수 있는 서비스입니다.

## 🔥버전

---

### Requirements

###### For building and running the application you need

* SpringBoot 3.1.2
* Java 17
* Redis
* Jwt 0.11.5
* S3 2.2.6
* Elastic Search 5.1.2
* GPT API 1.0.4

## 🐈‍⬛STACK

---

### Installation

    $ git clone https://github.com/BSB99/Project-MeongNyangBook.git
    $ cd Project-MoeongNyangBook

##### Environment

<img src="https://img.shields.io/badge/gitHub-'181717'?style=for-the-badge&logo=gitHub&logoColor=white">    <img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">    <img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=Spring&logoColor=white">    <img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=SpringBoot&logoColor=white">    <img src="https://img.shields.io/badge/githubactions-2088FF?style=for-the-badge&logo=githubactions&logoColor=white">

##### Development

<img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black">    <img src="https://img.shields.io/badge/Java-61DAFB?style=for-the-badge&logo=Java&logoColor=black">    <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=Docker&logoColor=white">    <img src="https://img.shields.io/badge/elasticsearch-005571?style=for-the-badge&logo=elasticsearch&logoColor=white">    <img src="https://img.shields.io/badge/apachekafka-231F20?style=for-the-badge&logo=apachekafka&logoColor=white">    <img src="https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=Redis&logoColor=white">    <img src="https://img.shields.io/badge/awslambda-FF9900?style=for-the-badge&logo=awslambda&logoColor=white">

##### Data base

<img src="https://img.shields.io/badge/MySql-4479A1?style=for-the-badge&logo=MySql&logoColor=white">    <img src="https://img.shields.io/badge/amazonrds-527FFF?style=for-the-badge&logo=amazonrds&logoColor=white">

##### Communication

<img src="https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=Notion&logoColor=white">    <img src="https://img.shields.io/badge/Slack-4A154B?style=for-the-badge&logo=Slack&logoColor=white">    <img src="https://img.shields.io/badge/kakaotalk-FFCD00?style=for-the-badge&logo=kakaotalk&logoColor=white">

##### AWS

<img src="https://img.shields.io/badge/amazonaws-232F3E?style=for-the-badge&logo=amazonaws&logoColor=white">    <img src="https://img.shields.io/badge/amazons3-569A31?style=for-the-badge&logo=amazons3&logoColor=white">    <img src="https://img.shields.io/badge/amazonroute53-8C4FFF?style=for-the-badge&logo=amazonroute53&logoColor=white">    <img src="https://img.shields.io/badge/amazonec2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white">

## 📦Trouble Shooting

---

- **데이터베이스 구조의 최적화 : 추상화 클래스 Post 상속**
    - 기존 방식 및 문제점
        - 게시물 기능인 `Adoption_post`와`Community_post`의 초기 ERD 구성 시 각각 독립적인 데이터베이스 구조로 설계하였지만 공통적인 요소가 많이 발견되어 DB 성능이 떨어질 것을
          우려함
    - 해결 방안 :  `추상화 클래스 도입` 및 `연관관계 최적화`
        - 추상화 클래스 도입 : 공통된 column을 Post라는 추상화 클래스로 정의, `Adoption_post`와 `Community_post` 를 작성하여 중복성을 제거하고, 각 기능에 특화된
          속성만을 포함하도록 작성
        - 연관관계 최적화 :  댓글, 좋아요 등의 기능은 각각 `Adoption_post`와 `Community_post`와의 연관관계를 갖지 않고, 추상화된 `Post`와의 연관관계를 통일하여
          관리하였습니다. 이를 통해 연관관계의 복잡성을 줄이고, 중복 코드를 최소화함.


- **조회수 증가 버그 및 과도한 DB 접근 문제 해결**
    - 기존 방식 및 문제점
        - 사용자가 새로고침 할 때 마다 조회수가 중복해서 증가 → 정확하지 않은 조회수 표시
        - 조회시마다 DB에 직접 접근하여 DB의 부하 증가 → 불필요한 부하 발생으로 인한 성능 저하
    - 해결 방안 : `Redis` 적용
        - 유저별로 1시간에 한 번만 조회수가 증가하도록 Redis를 활용하여 로직 재구성
        - DB 접근 횟수 감소를 위해 조회수 데이터를 Redis에서 캐시로 관리
        - Redis의 휘발성 데이터 특성 보완을 위해 **@**Scheduled 어노테이션 활용하여 6시간마다 Redis의 조회수 데이터를 DB에 반영


- **조건 검색의 성능 최적화 : `Elasticsearch` vs `Spring Data JPA의 QueryDSL`**
    - 기존 방식 및 문제점
        - Spring Data JPA의 QueryDSL을 사용하여 조건 검색 기능 구현을 구현하였으나, 트래픽이 많아지면서 다양한 조건의 데이터를 검색하고 집계하는데 있어서 구조적인 한계가 존재
    - 해결 방안
        - Elasticsearch를 사용하면 수정과 삭제에서 내부적으로 많은 리소스를 소요하지만, 수정과 삭제가 빈번하지 않으며, 조건 검색이 많이 필요한 쇼핑의 상품 조건 검색 기능에
          Elasticsearch를 적용함
    - 적용 결과 : JMeter를 통한 성능 테스트
        - 1만 건 이상의 데이터를 넣어, 대략 7000건의 API요청을 보낸 결과,

      → `8.6초 → 0.7초`로 10배 가량의 성능 향상을 확인


- **HTTPS로 웹 소켓 보안 설정하기: Nginx를 사용한 안전한 WSS 연결 구현 방법**
    - 기존 방식 및 문제점
        - 웹 사이트를 HTTPS로 변경하면서 웹 소켓 연결을 안정적으로 설정하기 위해 wss 프로토콜로 변경했으나, 웹 소켓 연결이 실패하는 문제가 발생
        - HTTPS로 사이트를 보호하면서 웹 소켓을 사용할 때, 보안 정책 때문에 일반적인 WebSocket 프로토콜로는 연결이 안될 수 있음
    - 해결 방안
        - Nginx 서버 설정에서 웹 소켓 프로토콜을 지원하도록 설정 추가


- **이미지 리사이즈를 통한 성능 최적화**
    - 기존 방식 및 문제점
        - 웹 사이트에서 많은 이미지를 제공하기 때문에 전체 조회 시, 사용자 경험을 향상시키기 위해 이미지 사이즈를 줄일 필요성을 느낌
    - 해결 방안
        - AWS Lambda를 활용하여 S3 버킷의 트리거로 동작하는 Lambda 함수를 구현
        - Lambda 함수 : Serverless 환경에서 실행되어 이미지 리사이즈 작업을 수행
        - 리사이즈된 이미지는 원본 이미지와 다른 폴더에 저장되며, 필요한 경우 리사이즈된 이미지를 가져와 사용
    - 적용 결과 : nGrinder를 사용한 성능 테스트 (동접자수 200명, 3분 동안의 전체 조회)

      -> `4.5초 → 2.1초`로 2배 가량의 성능 향상을 확인

## 📦주요기능

---

- 회원 기능
    - 회원가입, 로그인, 로그아웃 ( Redis 사용 )
    - 카카오, 구글 로그인 ( OAuth 로그인 )
    - 휴대폰 인증 및 이메일 인증 기능(비밀번호 찾기)
    - 회원 탈퇴 및 영구 정지, 신고 기능
    - user 프로필 조회 및 수정 기능
- 알림 기능 ( Kafka, SSE를 결합)
- 커뮤니티, 분양
    - 게시물 CRUD, 댓글 CRUD, 좋아요 기능
    - AWS S3 사용하여 이미지 등록 기능 ( Lambda를 이용한 이미지 Resize )
- 실시간 채팅 기능 ( WebSocket, Stomp )
- Chat GPT API를 이용한 질문 기능
- 쇼핑 기능
    - 조건 검색 기능 ( ElasticSearch를 이용 )
    - 상품 CRUD ( 관리자만 CUD 가능 ), 장바구니 CRUD
    - 결제 기능(PG사 KG이니시스 연동)
- 관리자 기능 ( Back Office )
    - 신고리스트 확인 및 유저 영구정지
    - 공지사항 CRUD 및 상품 CRUD

## API

---

> #### Swagger URL : https://meongnyangbook.site/swagger-ui/index.html

## 🗺️ERD

---

![meongnyangbook](https://github.com/BSB99/Project-MeongNyangBook/assets/81159848/8be5a87c-9918-4a3b-85e8-052ae60988c3)

## 🧭서비스 아키텍처

---

![meongnyangbook](https://github.com/BSB99/Project-MeongNyangBook/assets/81159848/724b180b-13d0-4199-a692-304860718a89)

## BestProject

---

![meongnyangbook](https://github.com/BSB99/Project-MeongNyangBook/assets/81159848/039055f8-8c16-4f77-b785-05cfbb1d7584)
