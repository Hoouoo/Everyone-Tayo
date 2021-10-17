# 시각 장애인을 위한 버스 승하차 시스템 : EveryOneTayo

<p align="center"><img height=300 width=600 img src="https://user-images.githubusercontent.com/56144682/132005848-0b1a1d64-b8a5-4aab-a55f-d9455894a414.png"></p>

## 개요

현재 시각장애인들은 교통수단에 많은 불편함을 겪고 있다.  
2019년에는 "시각 장애인도 버스를 탈 수 있게 해주세요." 라는 국민 청원도 올라왔다.  
이에 시각장애인에게 필요한 부분이 무엇인지 사전조사를 통해 시각장애인들이 사용하는 스마트폰에서 음성(TTS)를 사용한 버스 승하차 어플리케이션을 개발하였다.  

어플리케이션은 버스 전용 애플리케이션과 사용자 전용 애플리케이션 두 개로 나뉜다.  
각 어플리케이션의 간략한 설명은 다음과 같다.  

+ **버스 전용 App**  
시각 장애인이 버스를 승차 예약 및 하차를 할 때 알림을 받을 수 있어야한다.  
이에 "해당 버스에 사용자가 승/하차 예정이다."라는 알림을 전송하기 위해 버스 전용 애플리케이션을 개발하였다.  
 
+ **사용자 전용 App**  
서비스 이용 대상자를 기준으로 예약 과정을 최대한 간소화하여 시작하기 버튼 클릭을 시장으로 예약까지 음성으로 단 번에 진행된다.  
예약을 성공하면 하차 버튼을 눌러 사용자의 승차부터 하차까지 도울 수 있는 애플리케이션이다.  

+ **계정 관리자용 Web Server**  
버스 전용 애플리케이션의 버스 관련 계정 정보를 관리하기 위한 웹 서버를 구축하였다..  
해당 웹 서버에서 버스 계정을 추가, 삭제, 조회 가능하며 사용자 애플리케이션에서 예약된 예약 정보도 조회가능하다.  

---  
  
## 청와대 청원내용과 해결

<p align='center'><img width=800 src=https://user-images.githubusercontent.com/35298140/137614998-cc48d32b-b6a3-4602-ba59-66d740317996.png></p>

### 청원 내용 1 : 버스 정류장 정보를 알 수 없습니다.

<p align='center'><img width=1000 src=https://user-images.githubusercontent.com/35298140/137615126-1f9956ae-1c24-4d8d-840e-274d7d30a9e4.png></p>

### 해결 1

<p align='center'><img height=400 src=https://user-images.githubusercontent.com/35298140/137615235-446c2a6b-0c1a-408a-8f9d-fd90219d1946.png></p>

'현재위치확인' 버튼을 누르면 현재위치에서 가장 가까운 정류소이름과 그 정류소와 사용자의 거리가 음성으로 출력됩니다. 버스정류소에 도착후 자신이 올바른 위치에 서있는지 확인할 수 있습니다. 

### 청원 내용 2:  버스 번호를 파악할 수 없어 탑승이 어렵습니다.  

<p align='center'><img width=1000 src=https://user-images.githubusercontent.com/35298140/137615431-5e0c2246-b36e-4639-9cd0-848f9919618c.png></p>  

### 해결2  

<p align='center'><img height=750 src=https://user-images.githubusercontent.com/35298140/137616688-2ea93b13-69c6-43c7-b378-d65316763758.png></p>  

사용자는 앱을 사용해서 버스번호로 가장 빨리 도착하는 버스를 예약할 수 있다. 해당 버스에는 시각장애인이 위치한 버스 정류소의 정보가 서버로 부터 도착하여 등록된다. 버스가 예약된 정류소에 접근하면(gps정보로 알 수 있음) '버스앱 단말기'에 연결된 스피커로 해당 버스번호를 음성으로 출력한다. 시각장애인은 버스입구쪽 스피커로 자신이 타야할 버스가 어디에 있는지 알 수 있고 입구를 쉽게 찾을 수 있다.  

### 청원 내용 3:    

<p align='center'><img width=1000 src=https://user-images.githubusercontent.com/35298140/137616850-04723edd-10f1-46cc-accf-2ccea74af2cd.png></p>

### 해결3  

<p align='center'><img height=400 src=https://user-images.githubusercontent.com/35298140/137617054-0c019d25-e8bb-4556-8928-01d021b82bb1.png></p>

사용자 앱에서 버스를 예약하면 하차버튼을 누를 수 있다. 하차버튼을 누르면 버스앱에  서버를 통해 하차정보가 전달된다. 버스기사는 전달된 하차정보를 인식하고 버스를 정차한다.  

---  

## 실행하기 앞서

### 서비스 아키텍처
<p align ="center"><img height=600 src= https://user-images.githubusercontent.com/56144682/132202886-f1a9f99d-3492-4a02-a772-1d405375e76c.jpg></p>


**모바일**
- OS : Android OS
- 버스 전용 Application
- 사용자 전용 Application

**웹 서버**
- 애플리케이션 관리를 위한 관리자 전용 웹 페이지
- 애플리케이션과 통신을 위해 구축

---

### 개발 환경 (서버)

**개발 환경**
- IDE : IntelliJ IDEA ultimate
- Java 11
- MariaDB 10.5.12
- Docker
- PuSha

**Dependency**
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

---
 
### 개발 환경 (App)

**개발 환경**
- IDE : Android Studio
- Kotlin 1.5.30
- PuSha  
  
**Dependency**
```gradle
dependencies {

    implementation 'androidx.appcompat:appcompat:1.2.0'
    implementation 'com.google.android.material:material:1.3.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.0.4'
    implementation 'org.jetbrains:annotations:15.0'
    implementation 'androidx.work:work-runtime:2.5.0'
    testImplementation 'junit:junit:4.+'
    androidTestImplementation 'androidx.test.ext:junit:1.1.2'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.3.0'

    //lombok
    compileOnly 'org.projectlombok:lombok:1.18.12'
    annotationProcessor 'org.projectlombok:lombok:1.18.12'

    //retrofit2
    implementation 'com.google.code.gson:gson:2.8.7'
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
}
```


## 시작하기

### 공통 
1. 해당 코드 내려받기(HTTPS)  
``` 
git clone "https://github.com/Hoouoo/Everyone-Tayo.git"
```

2. 데이터 베이스 구축을 위한 MariaDB를 도커로 환경 구축
``` 
cd Everone-Tayo-db
(sudo) docker-compose up -d
```
> docker docs : https://docs.docker.com/get-started/

3. 실행하기  
3.1. `cd run` 디렉토리로 이동  
3.2-1. Server의 경우 :  
`EveryOne-Tayo-Server.jar` 실행  
3.2-2. Application의 경우 :  
(Android Os 기기에서 이용 가능) `Bus-EveryOne-Tayo.apk` 또는 `User-EveryOne-Tayo.apk` 실행  
  
4. 버스 전용 App에 로그인을 진행한다.
4.1. 로그인 계정은 웹 서버에 로그인하여 버스 계정 목록을 통해 조회 가능하다. 
> URL `127.0.0.1:8080/read_bus_driver` 

5. 사용자 전용 App의 안내에 따라 버스 탑승 신청을 하면된다.

**부가 설명**  
GCP, NCP, AWS와 같은 클라우드 서버를 이용하는 경우 다음 포트를 할당해주어야 한다.
- Pusha `9090`, `9091` 포트 할당
- 웹 `8080` 포트 할당

> 서버 IP 변경 시 TayoConfig.Class 파일 수정
```gradle
package team.sw.everyonetayo.configuration

class TayoConfig {

    companion object{
        val BASE_URL:String = [접속할 서버 주소:포트]
        val IP: String = [서버 IP 주소]
        val PORT: Int = [포트]
    }
}
```

### 버스 전용 App 환경설정

#### 로그인 계정 추가하는 방법 (localhost 기준)

도시코드(citycode) 목록표
> https://zesty-airbus-18b.notion.site/8d72635ab494438cae03d46f51c2edae
 
**Step 1. 버스 정류소 db에 저장하기**  
전국의 버스 정류소를 가져오기 위해 `localhost:8080/toJsonBusStoppAll`을 입력하여 전국의 버스 정류소를 db에 저장한다.  
> 특정 지역의 버스 데이터만 가져오고 싶으면 `/toJsonBusStop/[citycode]`에서 `citycode` 값을 변경  
   
**Step 2. `Route`를 db에 저장하기**  
URL : `127.0.0.1:8080/toJsonBusRoute/[citycode]` 에서 citycode 값에 해당하는 버스 노선 데이터 저장  

**Step 3. `Bus uuid`를 DB에 저장하기**  
- `127.0.0.1:8080/toJsonBusUuid/[citycode]`에서 citycode 값에 해당하는 차량 데이터 저장  

**Step 4. 버스 애플리케이션 계정 자동 생성**
- `127.0.0.1:8080/bus_account_auto_save`를 통해 db에 autoincrement된 값 저장  

---
## 실행 화면
### 버스 전용 애플리케이션

#### 시작 화면
버스 API를 활용하여 API에 존재하는 모든 차량 정보에 대한 ID/PW를 자동으로 생성하여 버스 차량 번호에 해당하는 ID/PW로 로그인 가능하다.
<p align="center"><img height=400 src=https://user-images.githubusercontent.com/35298140/132128260-edc0cd90-9002-4058-8a0a-a3fd0177e512.png></p>

#### 로그인 성공 후  
<p align="center"><img height=400 src=https://user-images.githubusercontent.com/35298140/132128302-fff4d99a-3eef-48f4-9ce7-178c653110a8.png></p>

> 로그인 화면에서 ID / PW를 입력하면 정상적으로 로그인이 가능하다.

#### 사용자가 해당 버스에 예약 시
사용자가 해당 버스 번호 및 차량 번호에 해당하는 정보로 예약에 성공했을 시 알림음과 함께 승차 버튼에 초록불 점등된다.
<p align="center"><img height=400 src=https://user-images.githubusercontent.com/35298140/132128488-e337d1ce-f945-4d2c-acb5-34a597a7a31d.png></p>

> 자체 개발한 네트워크 소켓을 이용한 '알림 서비스(pusha)'를 이용하여 해당 기능을 구현하였다.
>
> 자료 조사를 통해 시각 장애인은 버스 탑승 시 ''버스 위치를 알기 어렵다''는 것을 알 수 있었고, 
>
> 이러한 점을 도와드릴 수 있도록 알림을 울리는 형식으로 구현하였다.

#### 사용자가 해당 버스를 하차할 시
해당 사용자가 하차에 대한 알림을 전송할 시 하차에 빨간불 점등된다.
<p align="center"><img height=400 src=https://user-images.githubusercontent.com/35298140/132128526-0d14010c-d876-453a-9f78-6907ae244fe8.png></p>

> 승차 알림과 동일한 방법을 이용하여 해당 기능을 구현하였다.

---

### 사용자 전용 애플리케이션

#### 시작화면
사용자가 로그인을 하지 않고 바로 서비스를 이용할 수 있도록 구성하였으며, 사용자는 예약하기 버튼과 현재 위치 확인 버튼을 클릭할 수 있다.

예약하기는 버스 예약이 가능하고, 현재 위치 확인 버튼으로는 가장 가까운 버스 정류장의 이름과 거리를 알 수 있도록한다.

<p align="center"><img height=400 src=https://user-images.githubusercontent.com/74065019/137616595-4f79fa47-03fb-4dfd-be6e-7a9a379fecda.png></p>

> 해당 애플리케이션을 사용하는 대상을 고려하여 애플리케이션을 동작함에 있어 필요한 정보를 최소화 하였다.

#### 버스 예약
서비스 이용 대상자를 고려하여 음성으로 예약을 진행하며,  
예약 성공시 '버스 전용 애플리케이션'에 승차 알림을 전송한다.  
현재 사용자가 존재하는 위치를 기반으로 가장 가까운 버스 정류소에 필요한 버스 데이터 정보를 가져오며, 예약의 성공 여부는 음성으로 제공한다.  

<p align="center"><img height=400 src=https://user-images.githubusercontent.com/74065019/137616563-ba84e8a8-0f80-4464-8d76-0eca603cf432.png>
<img height=400 src=https://user-images.githubusercontent.com/74065019/137616624-5b5b1b37-3dff-4272-8957-b1c577b2fbfd.png>
<img height=400 src=https://user-images.githubusercontent.com/74065019/137616841-42935e24-78e9-42cb-8c0a-87a5cc6ef528.png>
<img height=400 src=https://user-images.githubusercontent.com/74065019/137616800-88f369af-ffad-4fb5-b7b2-284f60c9b214.png></p>


> 모든 서비스는 TTS(Text To Speach)를 이용하여 키패드의 입력 뿐만 아니라, 음성으로도 입력이 가능하도록 구현하였다.

#### 버스 하차
예약 성공시 생성되는 하차 버튼을 눌러 '버스 전용 애플리케이션'에 하차 알림 전송할 수 있다.
<p align="center"><img height=400 src=https://user-images.githubusercontent.com/74065019/137616714-565638cb-953c-461e-9ffe-61d89f580f9f.png>
    <img height=400 src=https://user-images.githubusercontent.com/74065019/137616744-2eefe2cf-37bb-40ca-80cd-7fc329e3c579.png>
<img height=400 src=https://user-images.githubusercontent.com/74065019/137616778-43e58607-82df-4ee0-875d-451ebfd6c8b1.png></p>

> 자료 조사를 통해 시각 장애인은 '버스 하차 벨' 또한 찾기 어려운 것을 알 수 있었다.  
> 이에 해당 애플리케이션으로 버스 승차 뿐 아니라, 하차까지 도와 드릴 수 있도록 구현하였다.


---

### 웹 서버 (관리자용) 페이지

#### 관리자 계정 로그인 화면
<p align="center"><img height=400 src=https://user-images.githubusercontent.com/56144682/132193221-78a8aa28-8956-4ad4-82e4-76dd95c12e39.png></p>

> 부여된 관리자 계정으로 로그인할 수 있다.
<!--초기 값 ID : `admin` PW : `admin`-->

#### 관리자 계정 버스 예약 현황 조회
<p align="center"><img height=600 src=https://user-images.githubusercontent.com/56144682/132198364-27d08cda-01cb-44d6-bbe9-3444224309da.png></p>

> 관리자 버스의 정보 및 예약 시간을 확인할 수 있다.

#### 버스 기사(버스 애플리케이션) 관리 탭

버스 기사계정을 추가, 삭제, 조회 할 수 있다.
<p align ="center"><img height=400 src= "https://user-images.githubusercontent.com/56144682/132202429-2fd4679a-2701-44ac-accc-c485c2d9eff3.png"></p>

---
**Application Download**
> 
---
## 팀 구성원
<p align ="center"><img height=400 src= https://user-images.githubusercontent.com/56144682/132203353-a8ee5ace-a06e-4294-a4f2-a8a7e9835110.png></p>

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
