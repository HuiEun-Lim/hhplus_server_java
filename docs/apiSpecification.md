# API 명세서

## 📌 잔액 충전 API

### 개요

**URL**: POST `/point`

**설명**: 사용자 번호와 충전 결제 금액을 받아 포인트를 충전.

---

### 요청 조건

1. **사용자 아이디**: 사용자 정보에 없는 경우 충전 불가.
2. **충전 금액**:
    - 0 이하의 금액은 충전 불가.
    - 10원 단위로만 충전 가능.
3. **포인트 처리**: 기존 결제 금액에 충전할 결제 금액을 합함.

---

### RequestBody

```json
{
  "userId": "1",
  "chargeAmount": 1000
}
```

| 필드명 | 타입 | 필수 여부 | 설명 |
| --- | --- | --- | --- |
| `userId` | integer | Y | 사용자 아이디 |
| `chargeAmount` | integer | Y | 충전할 금액 (10원 단위) |

---

### 응답 예시

### 성공

```json
{
  "message": "충전 성공",
  "data": {
    "userId": "1",
    "amount": 15000
  }
}
```

### 에러

- 사용자 아이디 확인 불가

```json
{
  "error": "InvalidUser",
  "message": "존재하지 않는 사용자 아이디입니다."
}
```

- 충전 금액 0 이하

```json
{
   "error": "InvalidAmount",
   "message": "충전 금액은 0보다 커야 합니다."
}
```

- 충전 금액이 10원 단위가 아님

```json
{
   "error": "InvalidAmount",
   "message": "충전 금액은 10원 단위로만 가능합니다."
}
```


## 📌 잔액 조회 API

### 개요

**URL**: POST `/point`

**설명**: 사용자 번호를 입력 받아 사용자의 충전된 결제 금액을 조회.

---

### 요청 조건

1. **사용자 아이디**: 사용자 정보에 없는 경우 조회 불가.
2. **결제 금액 처리**:
    - 사용자의 충전된 결제 금액을 반환.

---

### Request Parameters

| 필드명 | 타입 | 필수 여부 | 설명 |
| --- | --- | --- | --- |
| `userId` | integer | Y | 사용자 아이디 |

---

### 응답 예시

### 성공

```json
{
  "message": "조회 성공",
  "data": {
    "userId": "1",
    "amount": 15000
  }
}
```

### 에러

- 사용자 아이디 확인 불가

```json
{
  "error": "InvalidUser",
  "message": "존재하지 않는 사용자 아이디입니다."
}
```

## 📌 상품 목록 조회 API

### 개요

**URL**: GET `/goods`

**설명**: 모든 상품의 목록을 조회하며, 각 상품의 번호, 이름, 가격, 잔여 수량을 반환.

---

### 요청 조건

- 별도의 매개변수를 요구하지 않음.

---

### 응답 예시

### 성공

```json
{
  "message": "조회 성공",
  "data": [
    {
      "goodsId": 1,
      "name": "상품 A",
      "price": 10000,
      "stock": 50
    },
    {
      "goodsId": 2,
      "name": "상품 B",
      "price": 15000,
      "stock": 20
    },
    {
      "goodsId": 3,
      "name": "상품 C",
      "price": 20000,
      "stock": 0
    }
  ]
}
```

### 에러

- 상품 데이터가 없는 경우

```json
{
  "error": "NoGoods",
  "message": "조회 가능한 상품이 없습니다."
}
```

### 데이터 설명

| 필드명 | 타입 | 설명 |
| --- | --- | --- |
| `goodsId` | integer | 상품 번호 |
| `name` | string | 상품 이름 |
| `price` | integer | 상품 가격 (단위: 원) |
| `stock` | integer | 상품 잔여 수량 |

## 📌 상위 상품 조회 API

### 개요

**URL**: GET `/goods/best`

**설명**: 최근 3일간 가장 많이 팔린 상위 5개의 상품 정보를 조회.

---

### 요청 조건

- 별도의 매개변수를 요구하지 않음.

---

### 응답 예시

### 성공

```json
{
  "message": "조회 성공",
  "data": [
    {
      "goodsId": 1,
      "name": "상품 A",
      "price": 10000,
      "stock": 50
    },
    {
      "goodsId": 2,
      "name": "상품 B",
      "price": 20000,
      "stock": 20
    },
    {
      "goodsId": 3,
      "name": "상품 C",
      "price": 15000,
      "stock": 0
    },
    {
      "goodsId": 4,
      "name": "상품 D",
      "price": 30000,
      "stock": 11
    },
    {
      "goodsId": 5,
      "name": "상품 E",
      "price": 25000,
      "stock": 6
    }
  ]
}
```

### 데이터 설명

| 필드명 | 타입 | 설명 |
| --- | --- | --- |
| `goodsId` | integer | 상품 번호 |
| `name` | string | 상품 이름 |
| `price` | integer | 상품 가격 (단위: 원) |
| `stock` | integer | 상품 잔여 수량 |

## 📌 쿠폰 발급 API

### 개요

**URL**: POST`/coupons`

**설명**: 사용자 번호와 쿠폰 번호를 입력 받아 사용자에게 쿠폰을 발급.

---

### 요청 조건

1. **사용자 번호**: 사용자 정보에 없는 번호의 경우 발급 불가.
2. **쿠폰 번호**: 쿠폰 정보에 없는 번호의 경우 발급 불가.
3. **중복 발급 방지**: 이미 발급한 쿠폰은 재발급 불가.
4. **최대 발급 수량**: 쿠폰의 최대 발급 수량을 초과하여 발급 불가.

---

### RequestBody

```json
{
  "userId": "1",
  "couponId": "1001"
}
```

| 필드명 | 타입 | 필수 여부 | 설명 |
| --- | --- | --- | --- |
| `userId` | integer | Y | 사용자 번호 |
| `couponId` | integer | Y | 쿠폰 번호 |

---

### 응답 예시

### 성공

```json
{
  "message": "쿠폰 발급 성공",
  "data": {
    "userId": "1",
    "couponId": "1001"
  }
}
```

### 에러

- 사용자 번호 확인 불가

```json
{
  "error": "InvalidUser",
  "message": "존재하지 않는 사용자 번호입니다."
}
```

- 쿠폰 번호 확인 불가

```json
{
  "error": "InvalidCoupon",
  "message": "존재하지 않는 쿠폰 번호입니다."
}
```

- 쿠폰 중복 발급

```json
{
  "error": "DuplicateCoupon",
  "message": "이미 발급된 쿠폰입니다."
}
```

- 쿠폰 최대 발급 수량 초과

```json
{
  "error": "CouponLimitExceeded",
  "message": "쿠폰 최대 발급 수량을 초과했습니다."
}
```

---

### 데이터 설명

| 필드명 | 타입 | 설명 |
| --- | --- | --- |
| `userId` | integer | 사용자 번호 |
| `couponId` | integer | 쿠폰 번호 |

## 📌 보유 쿠폰 목록 조회 API

### 개요

**URL**: GET `/coupons`

**설명**: 사용자 번호를 입력 받아 현재 사용 가능한 쿠폰 목록을 조회.

---

### 요청 조건

1. **사용자 번호**: 사용자 정보에 없는 번호의 경우 조회할 수 없음.
2. **쿠폰 필터링**:
    - 이미 사용한 쿠폰은 제외.
    - 사용 기간이 유효하지 않은 쿠폰은 제외.

---

### Request Parameters

| 필드명 | 타입 | 필수 여부 | 설명 |
| --- | --- | --- | --- |
| `userId` | integer | Y | 사용자 번호 |

---

### 응답 예시

### 성공

```json
{
  "message": "조회 성공",
  "data": [
    {
      "couponId": 1001,
      "name": "10% 할인 쿠폰",
      "discountType": "rate",
      "discount": "10%",
      "maxAMt": 10000,
      "expiryDate": "2025-12-31"
    },
    {
      "couponId": 1002,
      "name": "5,000원 할인 쿠폰",
      "discountType": "amount",
      "discount": "5000원",
      "maxAMt": 5000,
      "expiryDate": "2025-12-25"
    }
  ]
}
```

### 에러

- 사용자 번호 확인 불가

```json
{
  "error": "InvalidUser",
  "message": "존재하지 않는 사용자 번호입니다."
}
```

---

### 데이터 설명

| 필드명 | 타입 | 설명 |
| --- | --- | --- |
| `couponId` | integer | 쿠폰 번호 |
| `name` | string | 쿠폰 이름 |
| `discountType` | varchar | 쿠폰 정액/정률 유형 |
| `discount` | integer | 쿠폰 할인율 또는 금액 |
| `maxAmt` | integer | 최대 할인금액 |
| `expiryDate` | string | 쿠폰 만료일 (YYYY-MM-DD) |

## 📌 주문 API

### 개요

**URL**: POST `/order`

**설명**: 사용자 번호, 주문 상품 목록, 쿠폰 번호를 입력 받아  주문 내역을 저장.

---

### 요청 조건

1. **사용자 번호**: 사용자 정보에 없는 번호일 경우 주문할 수 없음.
2. **주문 상품 목록**:
    - 상품 번호가 유효하지 않은 경우 주문 불가.
    - 상품의 재고가 주문 수량보다 적을 경우 주문 불가.
3. **쿠폰 번호** (선택):
    - 쿠폰 정보에 없는 경우 사용 불가.
    - 이미 사용된 쿠폰일 경우 사용 불가.
    - 사용 기한이 유효하지 않은 경우 사용 불가.
4. **판매가 계산**:
    - 상품 가격의 합에서 쿠폰 할인 적용.
    - 쿠폰 할인 유형(정액/정률)과 최대 할인가를 고려.
    - 쿠폰이 없으면 상품 가격 합계로 판매가 계산.
5. **처리 후 작업**:
    - 상품의 재고 수량을 차감.
    - 주문 내역을 저장.
    - 주문 상품 정보를 저장.
    - 쿠폰 상태를 사용 완료로 변경.

---

### RequestBody

```json
{
  "userId": 1,
  "orderItems": [
    {
      "productId": 1001,
      "quantity": 2
    },
    {
      "productId": 1002,
      "quantity": 1
    }
  ],
  "couponId": 2001
}
```

| 필드명 | 타입 | 필수 여부 | 설명 |
| --- | --- | --- | --- |
| `userId` | integer | Y | 사용자 번호 |
| `orderItems` | array | Y | 주문 상품 목록 |
| `orderItems[].productId` | integer | Y | 상품 번호 |
| `orderItems[].quantity` | integer | Y | 주문 수량 |
| `couponId` | integer | N | 쿠폰 번호 (선택) |

### 응답 예시

### 성공

```json
{
  "message": "주문 성공",
  "data": {
    "orderId": 12345,
    "totalPrice": 27000,
    "discountedPrice": 23000
  }
}
```

### 에러

- 사용자 번호 확인 불가

```json
{
  "error": "InvalidUser",
  "message": "존재하지 않는 사용자 번호입니다."
}
```

- 상품 번호 확인 불가

```json
{
  "error": "InvalidGoods",
  "message": "존재하지 않는 상품 번호입니다."
}
```

- 상품 재고 부족

```json
{
  "error": "InsufficientStock",
  "message": "상품 재고가 부족합니다."
}
```

- 쿠폰 번호 확인 불가

```json
{
  "error": "InvalidCoupon",
  "message": "존재하지 않는 쿠폰 번호입니다."
}
```

- 쿠폰 사용 불가 (이미 사용된 경우)

```json
{
  "error": "CouponAlreadyUsed",
  "message": "이미 사용된 쿠폰입니다."
}
```

- 쿠폰 사용 불가 (기한 만료)

```json
{
  "error": "CouponExpired",
  "message": "쿠폰 사용 기한이 만료되었습니다."
}
```

---

### 데이터 설명

| 필드명 | 타입 | 설명 |
| --- | --- | --- |
| `orderId` | integer | 주문 번호 |
| `totalPrice` | integer | 상품 가격 합계 |
| `discountedPrice` | integer | 할인 적용 후 최종 판매가 |

## 📌 결제 API

### 개요

**URL**: POST `/order/pay`

**설명**: 사용자 번호와 주문 번호를 입력 받아 결제를 처리하며, 충전 잔액에서 상품 판매가를 차감하고 주문 상태를 결제 완료로 변경.

---

### 요청 조건

1. **사용자 번호**:
    - 사용자 정보에 없는 번호일 경우 결제를 처리 불가.
2. **잔액 확인**:
    - 사용자의 충전 결제 잔액이 상품 판매가보다 적을 경우 결제를 처리 불가.
3. **결제 처리**:
    - 사용자의 충전 결제 잔액에서 상품 판매가를 차감.
    - 주문 정보에서 상태값을 "결제 완료"로 변경.

---

### RequestBody

```json
{
  "userId": 1,
  "orderId": 12345
}
```

| 필드명 | 타입 | 필수 여부 | 설명 |
| --- | --- | --- | --- |
| `userId` | integer | Y | 사용자 번호 |
| `orderId` | integer | Y | 주문 번호 |

### 응답 예시

### 성공

```json
{
  "message": "결제 성공",
  "data": {
    "orderId": 12345,
    "userId": 1,
    "totalPrice": 50000,
    "pointAmount": 20000,
    "status": "결제 완료"
  }
}
```

### 에러

- 사용자 번호 확인 불가

```json
{
  "error": "InvalidUser",
  "message": "존재하지 않는 사용자 번호입니다."
}
```

- 충전 잔액 부족

```json
{
  "error": "InsufficientBalance",
  "message": "충전 잔액이 부족하여 결제할 수 없습니다."
}
```

- 주문 번호 확인 불가

```json
{
  "error": "InvalidOrder",
  "message": "존재하지 않는 주문 번호입니다."
}
```

---

### 데이터 설명
| 필드명 | 타입 | 설명 |
| --- | --- | --- |
| `orderId` | integer | 주문 번호 |
| `userId` | integer | 사용자 번호 |
| `totalPrice` | integer | 결제된 상품의 총 판매가 |
| `pointAmount` | integer | 결제 후 충전 잔액 |
| `status` | string | 주문 상태 ("결제 완료") |