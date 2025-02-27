# 이커머스 부하 테스트 계획서

## 1. 테스트 대상 선정
- **대상 API** : 이커머스 주문 생성
    - **Endpoint** : `/orders` (POST)
    - **주요 로직** :
      - 사용자 정보 조회
      - 제품 정보 조회 및 재고 감소
      - 쿠폰 사용 처리 (사용할 쿠폰 존재 시)
      - 주문 생성 및 주문 상품 등록
      - 주문 완료 후 외부 데이터 플랫폼 이벤트 발행
      - 
## 2. 테스트 대상 선정 이유
### 1. 이커머스 시스템에서 가장 중요한 트랜잭션 중심 기능
- 주문 생성은 결제, 재고 관리, 배송 처리와 직접 연결되며, 실시간 트랜잭션이 가장 집중되는 API이다. 
- 따라서 동시 요청이 몰릴 때 안정적으로 처리되지 않으면 결제 실패, 주문 누락, 재고 오류 등의 심각한 문제가 발생할 수 있다.

### 2. 특가 상품 및 프로모션 시 높은 트래픽 유입 가능성
- 한정 수량 특가, 쇼핑몰 할인 행사, 블랙프라이데이 같은 이벤트에서는 단시간 내 수만 건의 주문이 생성될 수 있다.
- 특정 상품이 빠르게 품절될 경우, 주문 요청이 한꺼번에 몰려 DB 락, 트랜잭션 충돌 등의 문제가 발생할 가능성이 크다.

## 3. 테스트 시나리오
### 5000명 동시 주문 생성
-목표: 5000명의 사용자가 5분 동안 주문 생성 시, 시스템이 정상적으로 처리하는지 확인
- 설정:
  - 5000개의 VU(가상 사용자)가 동시에 실행되며 각각 1개의 주문 요청을 수행
  - 사용자 ID는 고정값(예: 101)을 사용
  - 상품 ID(productId: 100001), 수량(quantity: 1)을 고정하여 테스트
  - 응답 시간과 성공률을 측정

## 4. 테스트 환경
| 구성 요소     | 설정 값                       |
|:----------|:---------------------------|
| API 서버    | Spring Boot, JPA, HikariCP |
| DB        | MySQL (Docker)             |
| 부하 테스트 도구 | k6                         |
| 네트워크      | Docker Compose 환경          |
|모니터링 도구|Grafana, Prometheus|

## 5. 테스트 스크립트 `stress.js`
```javascript
import http from 'k6/http';
import { sleep, check } from 'k6';

export let options = {
    stages: [
        { duration: '5m', target: 5000 }
    ],
};

const BASE_URL = 'http://host.docker.internal:8080';

const HEADERS = {
    'Content-Type': 'application/json'
};

export default function () {

    let userId = 101;
    let orderItems = [
        {
            productId: 100001,
            quantity: 1
        }
    ];

    let payload = JSON.stringify({
        userId,
        orderItems,
        issuedCouponId: null
    });

    let response = http.post(`${BASE_URL}/orders`, payload, { headers: HEADERS });

    console.log(`VU: ${userId}, Response Body: ${response.body}`);

    // 응답 상태 코드 체크 (성공 여부 확인)
    check(response, {
        "is status 200": (r) => r.status === 200
    });
    sleep(1);
}
```

## 6. 결론
이 부하 테스트는 5000명의 동시 사용자가 주문을 생성할 때 시스템이 정상적으로 동작하는지 평가하는 것이 목표이다.
테스트 결과를 바탕으로 HikariCP 커넥션 풀 크기 조정, MySQL 튜닝, API 서버 확장 등의 최적화 작업을 수행할 계획이다.