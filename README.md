# 요구사항

기존 요구사항
* 모든 PUBLIC 메서드의 호출과 응답 정보를 로그로 출력
* 애플리케이션의 흐름을 변경하면 안됨
    * 로그를 남긴다고 해서 비즈니스 로직의 동작에 영향을 주면 안됨
* 메서드 호출에 걸린 시간
* 정상 흐름과 예외 흐름 구분
    * 예외 발생시 예외 정보가 남아야 함
* 메서드 호출의 깊이 표현
* HTTP 요청을 구분
    * HTTP 요청 단위로 특정 ID를 남겨서 어떤 HTTP 요청에서 시작된 것인지 명확하게 구분이 가능해야
      함
    * 트랜잭션 ID (DB 트랜잭션X), 여기서는 하나의 HTTP 요청이 시작해서 끝날 때 까지를 하나의
      트랜잭션이라 함

```shell
정상 요청
[796bccd9] OrderController.request()
[796bccd9] |-->OrderService.orderItem()
[796bccd9] |   |-->OrderRepository.save()
[796bccd9] |   |<--OrderRepository.save() time=1004ms
[796bccd9] |<--OrderService.orderItem() time=1014ms
[796bccd9] OrderController.request() time=1016ms
```

```shell
예외 발생
[b7119f27] OrderController.request()
[b7119f27] |-->OrderService.orderItem()
[b7119f27] |   |-->OrderRepository.save()
[b7119f27] |   |<X-OrderRepository.save() time=0ms ex=java.lang.IllegalStateException: 예외 발생!
[b7119f27] |<X-OrderService.orderItem() time=10ms ex=java.lang.IllegalStateException: 예외 발생!
[b7119f27] OrderController.request() time=11ms ex=java.lang.IllegalStateException: 예외 발생!
```

이 요구사항을 만족하기 위해서 기존 코드를 많이 수정해야 한다.  
코드 수정을 최소화 하기 위해 템플릿 메서드 패턴과 콜백 패턴도 사용했지만, 결과적으로 로그를 남기고 싶은 클래스가 수백개라면 수백개의 클래스를 모두 고쳐야한다.  
로그를 남길 때 기존 원본 코드를 변경해야 한다는 사실 그 자체가 개발자에게는 가장 큰 문제로 남는다.  
기존 요구사항에 다음 요구사항이 추가되었다.  

요구사항 추가
* 원본 코드를 전혀 수정하지 않고, 로그 추적기를 적용해라.
* 특정 메서드는 로그를 출력하지 않는 기능
    * 보안상 일부는 로그를 출력하면 안된다.
* 다음과 같은 다양한 케이스에 적용할 수 있어야 한다.
    * v1 - 인터페이스가 있는 구현 클래스에 적용
    * v2 - 인터페이스가 없는 구체 클래스에 적용
    * v3 - 컴포넌트 스캔 대상에 기능 적용
