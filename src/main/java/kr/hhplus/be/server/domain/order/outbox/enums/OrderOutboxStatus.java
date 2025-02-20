package kr.hhplus.be.server.domain.order.outbox.enums;

/**
 * 아웃박스 상태 종류
 * - SAVE : 저장
 * - SUCCESS : 성공
 * - FAIL : 실패
 */
public enum OrderOutboxStatus {
    SAVE, SUCCESS, FAIL
}
