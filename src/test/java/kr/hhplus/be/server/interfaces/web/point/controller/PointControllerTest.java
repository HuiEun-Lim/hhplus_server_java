package kr.hhplus.be.server.interfaces.web.point.controller;

import kr.hhplus.be.server.application.point.dto.PointFacadeResponse;
import kr.hhplus.be.server.application.point.facade.PointFacade;
import kr.hhplus.be.server.interfaces.web.ControllerTestSupport;
import kr.hhplus.be.server.interfaces.web.point.dto.request.PointRequest;
import kr.hhplus.be.server.interfaces.web.product.controller.ProductController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class PointControllerTest extends ControllerTestSupport {

    @Test
    @DisplayName("사용자의 포인트를 충전한다.")
    void chargeUserPoint() throws Exception {
        // given
        Long userId = 1L;
        Long amount = 1000L;

        PointRequest request = new PointRequest(userId, amount);

        PointFacadeResponse facadeResponse = PointFacadeResponse.builder()
                .userId(userId)
                .userName("임후에에엥씨")
                .amount(amount)
                .pointId(1L)
                .build();

        when(pointFacade.chargeUserPoint(userId, amount)).thenReturn(facadeResponse);

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/points")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.pointInfo.amount").value(amount));

    }

    @Test
    @DisplayName("사용자의 포인트를 조회한다.")
    void getUserPoint() throws Exception {
        // given
        Long userId = 1L;

        PointFacadeResponse facadeResponse = PointFacadeResponse.builder()
                .userId(userId)
                .userName("임후에에엥씨")
                .amount(1000L)
                .pointId(1L)
                .build();

        when(pointFacade.getUserPoint(any())).thenReturn(facadeResponse);

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/points")
                .param("userId", String.valueOf(userId))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.pointInfo.amount").value(1000L));

    }

}