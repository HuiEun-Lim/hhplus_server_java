package kr.hhplus.be.server.interfaces.web.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.application.product.dto.ProductFacadeResponse;
import kr.hhplus.be.server.application.product.facade.ProductFacade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(ProductController.class)
@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductFacade productFacade;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("상품 목록을 성공적으로 조회한다.")
    void getProductList() throws Exception {
        // given
        ProductFacadeResponse product1 = ProductFacadeResponse.builder()
                .productId(1L)
                .productName("알렉스 코치님의 성장학개론")
                .price(1000L)
                .stock(50L)
                .build();

        ProductFacadeResponse product2 = ProductFacadeResponse.builder()
                .productId(2L)
                .productName("라이언 코치님의 햄스터 모험 일기")
                .price(2000L)
                .stock(30L)
                .build();

        Page<ProductFacadeResponse> products = new PageImpl<>(Arrays.asList(product1, product2));

        when(productFacade.getProducts(any(Pageable.class))).thenReturn(products);

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/products")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "productId")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.products.content[0].productName").value("알렉스 코치님의 성장학개론"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.products.content[1].productName").value("라이언 코치님의 햄스터 모험 일기"));
    }

    @Test
    @DisplayName("최근 3일간 판매량 상위 상품 5개 목록을 성공적으로 조회한다.")
    void getTopProductList() throws Exception {
        // given
        ProductFacadeResponse topProduct1 = ProductFacadeResponse.builder()
                .productId(1L)
                .productName("경덕언니의 감자칩")
                .price(1000L)
                .stock(50L)
                .build();

        ProductFacadeResponse topProduct2 = ProductFacadeResponse.builder()
                .productId(2L)
                .productName("호민오빠의 코어타임 지각")
                .price(2000L)
                .stock(30L)
                .build();

        List<ProductFacadeResponse> topProducts = Arrays.asList(topProduct1, topProduct2);

        when(productFacade.getTopProducts()).thenReturn(topProducts);

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/products/top")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.topProducts[0].productName").value("경덕언니의 감자칩"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.topProducts[1].productName").value("호민오빠의 코어타임 지각"));
    }

}