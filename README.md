# jpa-shop-web
Spring Boot + JPA 기반 상품 주문 웹사이트                 
강의 : [실전! 스프링 부트와 JPA 활용1 - 웹 애플리케이션 개발, 김영한 강사님](https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-JPA-%ED%99%9C%EC%9A%A9-1)            

## Project Features

- Member
  - 회원가입 `/members/new`
  - 회원목록 `/members`               

- Item
  - 상품등록 `/items/new`
  - 상품수정 `/items/{itemId}/edit`
  - 상품목록 `/items`               

- Order
  - 상품주문 `/order`
  - 주문내역 `/orders`


## Project Setting              
* Spring boot version : 2.6.6                   
* Dependencies
  - Spring Web
  - Thymeleaf
  - Lombok      
  - Spring Data JPA
  - Validation
  - H2 Database                         

## 프로젝트 완성 후 생각
- 무분별한 수정 및 추적의 어려움으로 인해 Entity 내 setter를 가급적 열어두지 않아야 한다.
- -> setter를 열지 않고 값 설정 / JPA의 Dirty Checking를 활용한 값의 업데이트를 어떻게 해야할까?
  - -> 값 설정 : protected constructor + Entity 내부에 객체 생성 메서드를 활용하는 방식 고려
  - -> 값 수정 : private setter + 비즈니스 로직 메서드를 활용하는 방식 고려
  - 해당 프로젝트에 위 방식을 적용해보았으나 부족한 부분이 많은 것 같다. 
  - 앞으로 공부 및 다양한 프로젝트를 만들어보며 알아보장
                    
