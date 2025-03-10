package kr.hhplus.be.server.infrastructure.db.payment.jpa;

import kr.hhplus.be.server.domain.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentJpaRepository extends JpaRepository<Payment, Long> {
    Payment save(Payment payment);

    Payment findByPaymentId(Long paymentId);

    int countByOrderId(Long orderId);
}
