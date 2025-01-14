package kr.hhplus.be.server.interfaces.web.order.contoller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.application.order.dto.request.OrderProductDto;
import kr.hhplus.be.server.application.order.dto.response.OrderFacadeResponse;
import kr.hhplus.be.server.application.order.facade.OrderFacade;
import kr.hhplus.be.server.domain.order.dto.response.OrderItem;
import kr.hhplus.be.server.interfaces.web.order.dto.request.OrderRequest;
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

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@WebMvcTest(OrderController.class)
@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderFacade orderFacade;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("주문을 성공적으로 생성한다.")
    void createOrder() throws Exception {
        // given
        OrderRequest request = new OrderRequest(1L,  List.of(
                new OrderProductDto(100L, 2L),
                new OrderProductDto(101L, 1L)
        ),
        10L);

        OrderFacadeResponse facadeResponse = OrderFacadeResponse.builder()
                .orderId(1L)
                .userId(1L)
                .originPrice(5000L)
                .discountPrice(500L)
                .salePrice(4500L)
                .items(List.of(
                        new OrderItem(1L, 1L, 100L, "정보처리기사 필기 문제집", 2L, 2000L, 4000L),
                        new OrderItem(2L, 1L, 101L, "정보처리기사 실기 문제집", 1L, 1000L, 1000L)
                ))
                .build();

        when(orderFacade.createOrder(any())).thenReturn(facadeResponse);

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.orderInfo.orderId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.orderInfo.originPrice").value(5000L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.orderInfo.discountPrice").value(500L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.orderInfo.salePrice").value(4500L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.orderInfo.items[0].productName").value("정보처리기사 필기 문제집"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.orderInfo.items[1].productName").value("정보처리기사 실기 문제집"));

        verify(orderFacade, times(1)).createOrder(any());
    }
}