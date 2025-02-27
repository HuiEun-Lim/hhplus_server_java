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