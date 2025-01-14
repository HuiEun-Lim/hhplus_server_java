package kr.hhplus.be.server.interfaces.web.payment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.application.payment.dto.response.PaymentFacadeResponse;
import kr.hhplus.be.server.application.payment.facade.PaymentFacade;
import kr.hhplus.be.server.domain.payment.enums.PaymentStatusType;
import kr.hhplus.be.server.interfaces.web.payment.dto.request.PaymentRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@WebMvcTest(PaymentController.class)
@ExtendWith(MockitoExtension.class)
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PaymentFacade paymentFacade;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("결제를 성공적으로 생성한다.")
    void payOrder() throws Exception {
        // given
        PaymentRequest request = new PaymentRequest(1L, 10L);

        PaymentFacadeResponse facadeResponse = PaymentFacadeResponse.builder()
                .paymentId(1L)
                .orderId(10L)
                .payAmt(4500L)
                .paymentStatus(PaymentStatusType.PAYED)
                .build();

        when(paymentFacade.createPayment(any())).thenReturn(facadeResponse);

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.payment.paymentId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.payment.orderId").value(10L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.payment.payAmt").value(4500L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.payment.paymentStatus").value("PAYED"));

        verify(paymentFacade, times(1)).createPayment(any());
    }

}