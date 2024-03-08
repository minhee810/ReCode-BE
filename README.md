### 👨🏻‍🏫 ReCode-BE 프로젝트 소개 
Recode 서비스는 온라인 스터디 매칭 웹 서비스로 Recode = Let’s coding + Record의 합성어로 같이 코딩하며 공부한 내용을 기록하자는 의미를 담았습니다.

본인이 공부하고 싶은 공부 주제(기술 스택)에 맞는 스터디 그룹에 가입 할 수 있게 도와주는 것이 주요 목적입니다.

#### 기존 프로젝트와의 차별점
- 기존 온라인 스터디 매칭 서비스는 Hola(https://holaworld.io/)가 있습니다.
- 홀라와 Recode는 온라인 공간에서 상호 관심사(공부하고 싶은 주제)에 맞는 사람을 매칭해주는 기능은 동일합니다.
- 홀라에서는 스터디 중개 과정에서 타 플랫폼(카카오톡 오픈 채팅, 이메일 등등)을 이용하는데에 그치는 반면 Recode 서비스에서는 스터디 룸 내에서 채팅, 자료공유 게시판, 출석체크 기능을  제공합니다.

#### 🕰️ 개발 기간 (약 2개월) 
- 2023.10.16 ~ 2023.12.20

#### 👨‍💻 멤버 구성 
- 팀장_백승주 : 프로젝트 전체 PM, Frontend PM 
- 팀원_강민희 : 문서, 일정 정리, Fronted PM
- 팀원_김훈호 : DB PM
- 팀원_정성현 : (프로젝트 진행 중 취업) 
- 팀원_한다현 :  CI/CD PM, Backend PM
- 팀원_허 찬 : Backend PM

#### 🌐 개발 환경 
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


#### WBS
![Untitled](https://github.com/HeoJungBaekKang/ReCode-BE/assets/137294567/daaeabc3-a014-4442-b377-67c8d5378b0d)

#### 주요 기능 명세서
    1. System Architecture
        ![Untitled (1)](https://github.com/HeoJungBaekKang/ReCode-BE/assets/137294567/42e3d902-fd81-4dd1-8332-a5982fa052be)

    2. ERD
        ![RECODE_ERD](https://github.com/HeoJungBaekKang/ReCode-BE/assets/137294567/dd8133ac-d7a1-4ce5-983e-7933a9ac3ab6)

    3. Data Flow Chart
        

#### 주요 기능 설명
    1. 메인 페이지

    2. 스터디 그룹 생성

    3. 채팅

    4. 마이 페이지

    5. 스터디 룸
    
