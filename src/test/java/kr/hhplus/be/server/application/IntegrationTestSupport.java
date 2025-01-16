package kr.hhplus.be.server.application;

import kr.hhplus.be.server.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@TestExecutionListeners(value = {
        DependencyInjectionTestExecutionListener.class
})
public abstract class IntegrationTestSupport {

    @Autowired
    protected UserRepository userRepository;
}
