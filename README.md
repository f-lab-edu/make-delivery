# make-delivery 서버 구조도
![real](https://user-images.githubusercontent.com/34911552/102442304-772a4480-4067-11eb-839f-2d986933cde6.png)



## 프로젝트 목표
* 배달의 민족 같은 배달 앱 서비스를 구현해 내는 것이 목표입니다.
* 단순한 기능 구현뿐 아니라 대용량 트래픽 처리까지 고려한 기능을 구현하는 것이 목표입니다.
* 객체지향 원리와 여러 이론적 토대위에서 올바른 코드를 작성하는 것이 목표입니다.
* 문서화, 단위테스트는 높은 우선순위를 두어 작성했고 CI/CD를 통한 자동화 또한 구현하여 쉽게 협업이 가능한 프로젝트로 만들었습니다.

## 기술적 issue 해결 과정

* [#11] 성능 테스트 결과에 따라 비용을 고려하여 적절한 서버 구조 설계 과정  
https://tjdrnr05571.tistory.com/16

* [#10] Ngrinder를 이용해 성능테스트 : WAS, DB, Nginx등 서버에 병목이 있는지 확인하는 과정  
https://tjdrnr05571.tistory.com/17

* [#9] 같은 주문에 2명의 라이더가 동시에 배달하는 문제 해결 - Redis Transaction을 이용하여 데이터 atomic 보장  
https://tjdrnr05571.tistory.com/18

* [#8] Mysql Replication - Spring에서 AOP를 이용해 Master/Slave 이중화한 과정  
https://tjdrnr05571.tistory.com/14

* [#7] TransactionManager가 DataSource정하는 로직을 쿼리를 날릴 때로 늦추기 - LazyConnectionDataSourceProxy  
https://tjdrnr05571.tistory.com/15

* [#6] Jenkins를 이용하여 CI/CD 환경 구축하는 과정  
https://tjdrnr05571.tistory.com/13

* [#5] 성능을 위해 Redis keys 대신 scan 이용하기  
https://tjdrnr05571.tistory.com/11

* [#4] Spring에서 중복되는 로그인 체크 기능을 AOP로 분리하기  
https://tjdrnr05571.tistory.com/10

* [#3] 정확히 트랜잭션이 롤백 되었을 때 장바구니를 복원하기 - TransactionSynchronization afterCompletion (Rollback hook)
https://tjdrnr05571.tistory.com/9

* [#2] Redis에 한번에 많은 데이터 추가 시 네트워크 병목 개선하기 - Redis Pipeline 이용하기  
https://tjdrnr05571.tistory.com/7

* [#1] 서버가 여러대면 로그인 정보는 어디에 저장할까? - Sticky Session, Session Clustering, Redis Session Storage  
https://tjdrnr05571.tistory.com/3



## 라이더 배차 과정 구조도
![rider](https://user-images.githubusercontent.com/34911552/102442827-a55c5400-4068-11eb-93ab-705ae21e927e.png)

## DB ERD
![스크린샷 2020-10-12 22 05 51](https://user-images.githubusercontent.com/34911552/95750006-74ae1600-0cd7-11eb-8e10-2f16de2bbec4.png)

## Use Case
https://github.com/f-lab-edu/make-delivery/wiki/Use-Case

## Front
https://github.com/f-lab-edu/make-delivery/wiki/%ED%99%94%EB%A9%B4-%EC%84%A4%EA%B3%84
