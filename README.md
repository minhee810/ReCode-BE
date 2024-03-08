# 👨🏻‍🏫 ReCode-BE 프로젝트 소개 
Recode 서비스는 온라인 스터디 매칭 웹 서비스로 Recode = Let’s coding + Record의 합성어로 같이 코딩하며 공부한 내용을 기록하자는 의미를 담았습니다.

본인이 공부하고 싶은 공부 주제(기술 스택)에 맞는 스터디 그룹에 가입 할 수 있게 도와주는 것이 주요 목적입니다.

## 기존 프로젝트와의 차별점
- 기존 온라인 스터디 매칭 서비스는 Hola(https://holaworld.io/)가 있습니다.
- 홀라와 Recode는 온라인 공간에서 상호 관심사(공부하고 싶은 주제)에 맞는 사람을 매칭해주는 기능은 동일합니다.
- 홀라에서는 스터디 중개 과정에서 타 플랫폼(카카오톡 오픈 채팅, 이메일 등등)을 이용하는데에 그치는 반면 Recode 서비스에서는 스터디 룸 내에서 채팅, 자료공유 게시판, 출석체크 기능을  제공합니다.

## 🕰️ 개발 기간 (약 2개월) 
- 2023.10.16 ~ 2023.12.20

## 👨‍💻 멤버 구성 
- 팀장_백승주 : 프로젝트 전체 PM, Frontend PM 
- 팀원_강민희 : 문서, 일정 정리, Fronted PM
- 팀원_김훈호 : DB PM
- 팀원_정성현 : (프로젝트 진행 중 취업) 
- 팀원_한다현 :  CI/CD PM, Backend PM
- 팀원_허 찬 : Backend PM

## 🌐 개발 환경 
- **OS**
    - ![Windows](https://img.shields.io/badge/Windows-0078D6?style=for-the-badge&logo=windows&logoColor=white), ![macOS](https://img.shields.io/badge/macOS-000000?style=for-the-badge&logo=apple&logoColor=white)
    - Ubuntu 22.04 : AWS EC2 인스턴스 OS
    - iOS, Android : 알람 기능
- **Server**
    - ![Tomcat](https://img.shields.io/badge/Tomcat-F8DC75?style=for-the-badge&logo=apache-tomcat&logoColor=black) : Spring Boot 기반 Backend API 서버
    - ![Nginx](https://img.shields.io/badge/Nginx-009639?style=for-the-badge&logo=nginx&logoColor=white) : React 기반 Frontend API 서버
    - ![Netty](https://img.shields.io/badge/Netty-415D46?style=for-the-badge&logo=Netty&logoColor=white) : Spring Boot Reactive Web 기반 채팅 API 서버
- **Framework** : ![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white), ![React](https://img.shields.io/badge/React-61DAFB?style=for-the-badge&logo=react&logoColor=white)
- **IDE**
    - ![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ_IDEA-000000?style=for-the-badge&logo=intellij-idea&logoColor=white) : Backend 개발
    - ![Visual Studio Code](https://img.shields.io/badge/Visual_Studio_Code-007ACC?style=for-the-badge&logo=visual-studio-code&logoColor=white) : Frontend 개발
    - ![HeidiSQL](https://img.shields.io/badge/HeidiSQL-9C27B0?style=for-the-badge&logo=HeidiSQL&logoColor=white) : 최종 배포판 DB 관리
    - ![h2 Database](https://img.shields.io/badge/h2_Database-00457C?style=for-the-badge&logo=h2&logoColor=white) : [Localhost](http://Localhost) 환경에서 DB 테스트
    - ![MongoDB Compass](https://img.shields.io/badge/MongoDB_Compass-47A248?style=for-the-badge&logo=mongodb&logoColor=white) : 채팅 데이터 확인
- **DB Tool**
    - ![MariaDB](https://img.shields.io/badge/MariaDB-003545?style=for-the-badge&logo=mariadb&logoColor=white) : 프로젝트 전체 DB
    - ![MongoDB](https://img.shields.io/badge/MongoDB-47A248?style=for-the-badge&logo=mongodb&logoColor=white) : 채팅 메세지 저장을 위한 DB
- **개발 언어**
    - ![JavaSE 11](https://img.shields.io/badge/Java_SE_11-007396?style=for-the-badge&logo=java&logoColor=white) : Backend 개발 언어
    - ![JavaSE 21](https://img.shields.io/badge/Java_SE_21-007396?style=for-the-badge&logo=java&logoColor=white) : 채팅 기능 개발 언어
- **부수적인 언어**
    - ![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black) : React 개발 언어
    - ![Python3](https://img.shields.io/badge/Python_3-3776AB?style=for-the-badge&logo=python&logoColor=white) : AWS Lambda 개발 언어

## 📝 Notion Page 
프로젝트 기록 Notion 보러가기 : ✏️ [Recode Notion page ](https://www.notion.so/Recode-Project-b7f5aae1842d434cb56b61cd655d7c8f)



## 📆 WBS (Work Breakdown Structure)
[Recode WBS](https://docs.google.com/spreadsheets/d/1g9SNr9Wtk2Ag40zXaVhdDxNf1PJvWBMJU3m0X9w3-DM/edit#gid=397150525)



## 📄 주요 기능 명세서
#### 📌 기능 명세서 [Notion_링크](https://www.notion.so/2b288647f6734f8ca7c67b0b95290c74?v=d3cb78329e324a92b004dfa917fe5933)
 <details>
  <summary> 이미지 </summary>
  <img width="1386" alt="image" src="https://github.com/minhee810/ReCode-BE/assets/100061907/e025ee37-a42a-4ef3-93f2-9a109f3b7096">
</details>


#### 📌 API 명세서 [Notion 링크](https://www.notion.so/API-ver-2-f76b1841beb2407c80dae8cd56b953f7)
 <details>
    <summary> 이미지 </summary>
    - 전체 목록 
    <img width="1106" alt="image" src="https://github.com/minhee810/ReCode-BE/assets/100061907/c403dd67-3b56-4a74-97a6-a107b3f18943">
    - Request / Response 응답 형식
    <img width="508" alt="image" src="https://github.com/minhee810/ReCode-BE/assets/100061907/c8f6f797-5ce7-47b9-924e-2c6fc6c3ec55">
    <img width="510" alt="image" src="https://github.com/minhee810/ReCode-BE/assets/100061907/2da554d9-6881-4c4c-ae97-22bab4ae4dce">
 </details>

#### 📌 ERD
<details>
    <summary> 이미지 </summary>
    <img width="1101" alt="image" src="https://github.com/minhee810/ReCode-BE/assets/100061907/fb74450a-71af-4c36-9d67-1d8aef20d2cb">
</details>

#### 📌 시스템 아키텍쳐 
<details> 
    <summary> 이미지 </summary>
    <img width="827" alt="image" src="https://github.com/minhee810/ReCode-BE/assets/100061907/99c6e4cb-8221-45de-aafc-b5c862735522">
</details>

## 주요 기능 설명

    1. 메인 페이지

    2. 스터디 그룹 생성

    3. 채팅

    4. 마이 페이지

    5. 스터디 룸
    

