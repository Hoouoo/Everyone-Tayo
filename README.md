# [공개 SW] 시각 장애인을 위한 버스 승하차 시스템 : EveryOneTayo

<p align="center"><img src="https://user-images.githubusercontent.com/56144682/132005848-0b1a1d64-b8a5-4aab-a55f-d9455894a414.png"></p>

## 개요

현재 시각장애인들은 교통수단에 많은 불편함을 겪고 있다.  
2019년에는 "시각 장애인도 버스를 탈 수 있게 해주세요." 라는 국민 청원도 올라왔다.  
이에 시각장애인에게 필요한 부분이 무엇인지 사전조사를 통해 시각장애인들이 사용하는 스마트폰에서 음성(TTS)를 사용한 버스 승하차 어플리케이션을 개발하였다.

어플리케이션은 버스 전용 애플리케이션과 사용자 전용 애플리케이션 두 개로 나뉜다.  
각 어플리케이션의 간략한 설명은 다음과 같다.

**버스 전용 애플리케이션**  
시각 장애인이 버스를 예약 및 하차를 할 때 알림을 받을 수 있어야한다.  
이에 "해당 버스에 사용자가 승/하차 예정이다."라는 알림을 전송하기 위해 버스 전용 애플리케이션을 개발하였다.

**사용자 전용 애플리케이션**  
서비스 이용 대상자를 기준으로 최대한 예약 과정을 단편화하여 
시작하기 버튼 클릭을 시장으로 예약까지 음성으로 단 번에 진행된다.  
예약을 성공하면 하차 버튼을 눌러 사용자의 승차부터 하차까지 도울 수 있는 애플리케이션이다.  

---
## 실행 화면
### 버스 전용 애플리케이션

#### 시작 화면
버스 API를 활용하여 API에 존재하는 모든 차량 정보에 대한 ID/PW를 자동으로 생성하여 버스 차량 번호에 해당하는 ID/PW로 로그인  
<p align="center"><img height=400 src=https://user-images.githubusercontent.com/35298140/132128260-edc0cd90-9002-4058-8a0a-a3fd0177e512.png></p>

#### 로그인 성공 후  
<p align="center"><img height=400 src=https://user-images.githubusercontent.com/35298140/132128302-fff4d99a-3eef-48f4-9ce7-178c653110a8.png></p>

> 로그인 화면에서 ID / PW를 입력하면 정상적으로 로그인이 가능하다.

#### 사용자가 해당 버스에 예약 시
사용자가 해당 버스 번호 및 차량 번호에 해당하는 정보로 예약에 성공했을 시 알림음과 함께 승차 버튼에 초록불 점등  
<p align="center"><img height=400 src=https://user-images.githubusercontent.com/35298140/132128488-e337d1ce-f945-4d2c-acb5-34a597a7a31d.png></p>

> 자체 개발한 네트워크 소켓을 이용한 '알림 서비스(pusha)'를 이용하여 해당 기능을 구현하였다.

#### 사용자가 해당 버스를 하차할 시
해당 사용자가 하차에 대한 알림을 전송할 시 하차에 빨간불 점등  
<p align="center"><img height=400 src=https://user-images.githubusercontent.com/35298140/132128526-0d14010c-d876-453a-9f78-6907ae244fe8.png></p>

> 승차 알림과 동일한 방법을 이용하여 해당 기능을 구현하였다.

---

### 사용자 전용 애플리케이션

#### 시작화면
사용자가 로그인을 하지 않고 바로 서비스를 이용할 수 있도록 시작하기 버튼 클릭과 동시에 예약 서비스 실행  
<p align="center"><img height=400 src=https://user-images.githubusercontent.com/35298140/132127801-1df75438-c31f-45c2-83cf-ebd585f42acf.png></p>

> 해당 애플리케이션을 사용하는 대상을 고려하여 애플리케이션을 동작함에 있어 필요한 정보를 최소화 하였다.

#### 버스 예약
서비스 이용 대상자를 고려하여 음성으로 예약을 진행하며, 예약 성공시 '버스 전용 애플리케이션'에 승차 알림을 전송한다.  
현재 사용자가 존재하는 위치를 기반으로 가장 가까운 버스 정류소에 필요한 버스 데이터 정보를 가져오며, 예약의 성공 여부는 음성으로 제공한다.  
<p align="center"><img height=400 src=https://user-images.githubusercontent.com/35298140/132127832-a775b8d1-0015-4d5d-8376-6cb505597507.png>
<img height=400 src=https://user-images.githubusercontent.com/35298140/132127878-ea66f6f6-245f-47cd-a27e-0ba64b45115c.png>
<img height=400 src=https://user-images.githubusercontent.com/35298140/132128097-9b801aa9-723d-49ea-b0d1-d060cc13b0ca.png></p>

> 모든 서비스는 TTS(Text To Speach)를 이용하여 키패드의 입력이 아닌, 음성으로도 입력이 가능하도록 구현하였다.

#### 버스 하차
예약 성공시 하단에 생성되는 하차 버튼을 눌러 '버스 전용 애플리케이션'에 하차 알림 전송  
<p align="center"><img height=400 src=https://user-images.githubusercontent.com/35298140/132128132-d76a82a2-537f-4bed-88d0-568ed0c1867c.png></p>

> 자료 조사를 통해 시각 장애인은 '버스 하차 벨' 또한 찾기 어려운 것을 알 수 있었다.
> 이에 해당 애플리케이션으로 버스 승차 뿐 아니라, 하차까지 도와 드릴 수 있도록 구현하였다.

---

### 웹 서버 (관리자용) 페이지

#### 관리자 계정 로그인 화면
<p align="center"><img height=400 src=https://user-images.githubusercontent.com/56144682/132193221-78a8aa28-8956-4ad4-82e4-76dd95c12e39.png></p>

> 부여된 관리자 계정으로 로그인할 수 있다.
<!--초기 값 ID : `admin` PW : `admin`-->


## 실행하기 앞서

### 서비스 아키텍처
<p align ="center"> <img height=600 src= "https://user-images.githubusercontent.com/56144682/132189511-c0271d8a-e834-4947-b7f6-e84d231147cc.jpg"></p>

**모바일**
- OS : Android OS
- 버스 전용 애플리케이션
- 사용자 전용 애플리케이션

**웹 서버**
- 애플리케이션 관리를 위한 관리자 전용 웹 페이지
- 애플리케이션과 통신을 위해 구축

---

### 개발 환경 

**서버**
- IDE : intelliJ IDEA ultimate
- Java 11
- MariaDB 10.5.12
- Docker

**서버 Dependency**
```gradle
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'org.springframework.boot:spring-boot-starter-validation'
    implementation 'org.springframework.boot:spring-boot-starter-security'
    implementation 'org.thymeleaf.extras:thymeleaf-extras-springsecurity5'
    compileOnly 'org.projectlombok:lombok'
    developmentOnly 'org.springframework.boot:spring-boot-devtools'
    runtimeOnly 'org.mariadb.jdbc:mariadb-java-client'
    annotationProcessor 'org.projectlombok:lombok'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    compileOnly 'org.webjars:bootstrap:5.0.1'
    compileOnly 'org.webjars:jquery:1.11.1'
    implementation 'org.json:json:20210307'
    implementation 'io.jsonwebtoken:jjwt-api:0.10.7'
    runtimeOnly 'io.jsonwebtoken:jjwt-impl:0.10.7'
    runtimeOnly 'io.jsonwebtoken:jjwt-jackson:0.10.7'
}
```


## 시작하기

1. 해당 코드 내려받기(HTTPS)  
``` 
git clone "https://github.com/golagola2020/hango-server.git"
```

2. 데이터 베이스 구축을 위한 MariaDB를 도커로 환경 구축
``` 
cd Everone-Tayo-db
(sudo) docker-compose up -d.yml
```

> docker docs : https://docs.docker.com/get-started/

3. run폴더의 각가 실행파일 실행
- Server의 경우 :  
`EveryOne-Tayo-Server.jar` 실행
- Application의 경우 :  
(Android Os 기기에서 이용 가능) `Bus-EveryOne-Tayo.apk` 또는 `User-EveryOne-Tayo.apk` 실행
---

## 팀 구성원

**이상훈** 
Leader 
> 동의대학교 컴퓨터소프트웨어공학과 : 재학

**정규업**
Front-end / Designer
> 동의대학교 컴퓨터소프트웨어공학과 : 재학

**박성호**
Full-stack
> 동의대학교 컴퓨터소프트웨어공학과 : 재학

**손 민**
Full-stack
> 동의대학교 컴퓨터소프트웨어공학과 : 재학
