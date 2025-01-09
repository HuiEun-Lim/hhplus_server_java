package kr.hhplus.be.server.interfaces.web.point.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.application.point.dto.PointFacadeResponse;
import kr.hhplus.be.server.application.point.facade.PointFacade;
import kr.hhplus.be.server.interfaces.web.point.dto.request.PointRequest;
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
import static org.mockito.Mockito.when;

@WebMvcTest(PointController.class)
@ExtendWith(MockitoExtension.class)
class PointControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PointFacade pointFacade;

    @Autowired
    private ObjectMapper objectMapper;

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
                .andExpect(MockMvcResultMatchers.status().isOk());

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
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

}