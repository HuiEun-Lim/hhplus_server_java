-- User
INSERT INTO user (user_id, name, created_at, modified_at)
VALUES
    (101, "임희은", NOW(), NOW())
    , (102, "김경덕", NOW(), NOW())
    , (103, "이호민", NOW(), NOW())
    , (104, "최재영", NOW(), NOW())
    ;

-- Coupon
INSERT INTO coupon (coupon_id, coupon_name, discount_type, discount_amount, max_discount_Amount, max_issuance_count, expiry_date, created_at, modified_at)
VALUES
    (10001, "10% 할인 쿠폰 (최대 1만원)", "RATE", 10, 10000, 5, DATE_ADD(NOW(), INTERVAL 7 DAY), NOW(), NOW())
    , (10002, "2000원 할인 쿠폰", "AMOUNT", 2000, 2000, 20, DATE_ADD(NOW(), INTERVAL 10 DAY), NOW(), NOW())
    , (10003, "유효기간 만료 쿠폰", "AMOUNT", 1000, 1000, 1, DATE_SUB(NOW(), INTERVAL 1 DAY), NOW(), NOW())
    ;