package kr.hhplus.be.server.domain.payment.service;

import kr.hhplus.be.server.domain.payment.dto.PaymentResult;
import kr.hhplus.be.server.domain.payment.dto.PaymentServiceRequest;
import kr.hhplus.be.server.domain.payment.entity.Payment;
import kr.hhplus.be.server.domain.payment.enums.PaymentStatusType;
import kr.hhplus.be.server.domain.payment.repository.PaymentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    @Test
    @DisplayName("결제를 성공적으로 생성한다.")
    void testCreatePayment_Success() {
        // Given
        PaymentServiceRequest request = new PaymentServiceRequest(
                1L,
                5000L,
                PaymentStatusType.PAYED
        );

        Payment mockPayment = new Payment(
                1L,
                request.getOrderId(),
                request.getPayAmt(),
                PaymentStatusType.PAYED
        );

        when(paymentRepository.save(any(Payment.class))).thenReturn(mockPayment);

        // When
        PaymentResult result = paymentService.createPayment(request);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getPaymentId()).isEqualTo(1L);
        assertThat(result.getOrderId()).isEqualTo(request.getOrderId());
        assertThat(result.getPayAmt()).isEqualTo(request.getPayAmt());
        assertThat(result.getPaymentStatus()).isEqualTo(request.getPaymentStatus());

        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

}