package kr.hhplus.be.server.interfaces.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.application.coupon.facade.CouponFacade;
import kr.hhplus.be.server.application.order.facade.OrderFacade;
import kr.hhplus.be.server.application.payment.facade.PaymentFacade;
import kr.hhplus.be.server.application.point.facade.PointFacade;
import kr.hhplus.be.server.application.product.facade.ProductFacade;
import kr.hhplus.be.server.interfaces.web.coupon.controller.CouponController;
import kr.hhplus.be.server.interfaces.web.order.contoller.OrderController;
import kr.hhplus.be.server.interfaces.web.payment.controller.PaymentController;
import kr.hhplus.be.server.interfaces.web.point.controller.PointController;
import kr.hhplus.be.server.interfaces.web.product.controller.ProductController;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {
        OrderController.class,
        ProductController.class,
        PaymentController.class,
        PointController.class,
        CouponController.class
})
@ExtendWith(MockitoExtension.class)
public abstract class ControllerTestSupport {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockitoBean
    protected ProductFacade productFacade;

    @MockitoBean
    protected PointFacade pointFacade;

    @MockitoBean
    protected PaymentFacade paymentFacade;

    @MockitoBean
    protected OrderFacade orderFacade;

    @MockitoBean
    protected CouponFacade couponFacade;
}
