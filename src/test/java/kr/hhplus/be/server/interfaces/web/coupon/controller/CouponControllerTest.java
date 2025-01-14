package kr.hhplus.be.server.interfaces.web.coupon.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.application.coupon.dto.CouponIssuanceFacadeResponse;
import kr.hhplus.be.server.application.coupon.facade.CouponFacade;
import kr.hhplus.be.server.domain.coupon.enums.CouponStateType;
import kr.hhplus.be.server.interfaces.web.coupon.dto.request.CouponRequest;
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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CouponController.class)
@ExtendWith(MockitoExtension.class)
class CouponControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CouponFacade couponFacade;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("선착순 쿠폰 발급")
    void CouponIssue() throws Exception {
        // Given
        CouponRequest request = new CouponRequest(1L, 100L);

        CouponIssuanceFacadeResponse mockResponse = CouponIssuanceFacadeResponse.create(1L, 1L, 100L, "컨트롤러 쿠폰", CouponStateType.UNUSED, LocalDateTime.now().plusDays(3));
        when(couponFacade.issueCoupon(any(Long.class), any(Long.class))).thenReturn(mockResponse);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/coupons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.issuedCoupon.userId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.issuedCoupon.couponId").value(100L));
    }

    @Test
    @DisplayName("사용자 쿠폰 발급 목록 조회 - 성공")
    void testGetUserCoupons_Success() throws Exception {
        // Given
        Long userId = 1L;

        List<CouponIssuanceFacadeResponse> mockResponseList = Arrays.asList(
                CouponIssuanceFacadeResponse.create(1L, 1L, 101L, "쿠폰1", CouponStateType.UNUSED, LocalDateTime.now().plusDays(3)),
                CouponIssuanceFacadeResponse.create(2L, 1L, 102L, "쿠폰2", CouponStateType.USE, LocalDateTime.now().plusDays(3))
        );
        when(couponFacade.userIssuedCoupons(userId)).thenReturn(mockResponseList);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/coupons")
                        .param("userId", String.valueOf(userId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.issuedCouponList[0].issuanceId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.issuedCouponList[1].issuanceId").value(2L));
    }

}