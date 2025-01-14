### **커밋 링크**

CHORE: 환경 설정 변경 : 75796cb
FEAT: SwaggerConfig 생성 : 19ad903
FEAT: 사용자 repository 생성 + 단위테스트 : d949e19
FEAT: 사용자 검증 유틸 생성 + 단위테스트 : cd8f94f
FEAT: 사용자 service 생성 + 단위테스트 : 869836c
FEAT: 포인트 repository 생성 + 단위테스트 : 1b34649
FEAT: 포인트 검증 utils 생성 + 테스트 : c58f89f
FEAT: 포인트 service 생성 + 테스트 : 075ec86
FEAT: 포인트 facade 생성 + 단위테스트 : 125b3b7
FEAT: 포인트 controller 생성 + 단위테스트 : f4a9f77
FEAT: 쿠폰 도메인/repository 생성 + 단위테스트 : 07470e8
FEAT: 쿠폰 service 생성 + 단위테스트 : 6d4aadc
FEAT: 쿠폰 facade 생성 + 단위테스트 : b4d3b1b
FEAT: 쿠폰 controller 수정 + 단위테스트 : d3d1800
FEAT: 상품 repository/entity 생성 및 테스트 : e3d6d4e
REFACTOR: 포인트 리팩토링 : 09e1fa6
FEAT: 사용자 쿠폰 Response 수정 : 814a79f
FEAT: 상품 service 생성 및 테스트 : 676abe7
FEAT: 쿠폰 service 테스트 수정 : 0fd663a
FEAT: 주문 비즈니스 로직 구현 및 단위 테스트 : b023805
FEAT: 결제 비즈니스 로직 구현 및 단위 테스트 : 8252fa9
CHORE: QueryDsl 세팅 : a3d10b6
FEAT: 상위 상품 비즈니스 로직 구현/단위테스트 : e47d81c
FEAT: 상품 facade 구현/단위테스트 : bca3d8b
FEAT: controller 수정 : b6ef8dd
CHORE: SwaggerConfig 수정 : 1baf960
DOCS: ReadMe 작성 : 1baf960
CHORE: JpaConfig 수정 : f348549

---
### **리뷰 포인트(질문)**

- 파라미터에 대한 검증을 `controller`나 `facade`에서 하려고 했는데 그러면 코드 중복이 많은 거 같아서 `utills`을 만들어서 따로 제어하고 `service`에서 공통적으로 관리하게 했습니다.
    (파라미터 검증 로직을 `service`와 `facade`를 너무 많이 오가서 어느 위치에 있든 이식성이 좋도록 뺐습니다 사실...ㅎ)
    파라미터에 대한 검증을 어디서 진행하는 것이 좋고 로직마다 반복되는 검증은 어떻게 제어하는 게 좋을까요?
- 커밋 단위를 좀 더 작게해야할 것 같아서 세분화 했는데 이번에 오히려 너무 많이 해버렸어요... 커밋의 단위는 어느정도가 적절한지 궁금합니다.
- 하고 싶은 거, 해야 하는 거가 많았는데 시간 부족으로 다 완성하진 못했습니다...ㅠㅠ



---
### **이번주 KPT 회고**

### Keep
<!-- 유지해야 할 좋은 점 -->
- 동기들+학매들과의 활발한 소통
- 다 못할 걸 알아도 최대한 해보겠다는 마인드! 최선을 다 했으니 괜찮아!! 아마도...

### Problem
<!--개선이 필요한 점-->
- 변명이지만 회사 일이 바빠지면서 가뜩이나 부족했던 시간과 체력이 더 부족해지면서 2일의 전사가 되었다... 시간 분배 더 잘할 것!!ㅠㅠ
- 설계와 구조에 대해 너무 많은 시간을 뻿겼던 건 아닐까 싶다... 모르면 바로 질문하고 다음으로 넘어갈 것!!
- 다음 주차를 위해 남은 과제 마저 하기!!

### Try
<!-- 새롭게 시도할 점 -->
- queryDSL 얼렁뚱땅 도전했는데 제대로 공부해보기!!
