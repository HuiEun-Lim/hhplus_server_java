package kr.hhplus.be.server.infrastructure.kafka;

import kr.hhplus.be.server.application.IntegrationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

class KafkaIntegrationTest extends IntegrationTestSupport {

    @Autowired
    private TestProducer producer;

    @Autowired
    private TestConsumer consumer;

    @Test
    @DisplayName("Kafka 테스트")
    public void kafkaSendAndReceiveTest() throws InterruptedException {
        // given
        int cnt = 5;
        String topic = "test-topic";
        for (int i = 0; i < cnt; i++) {
            producer.send(topic, "MESSAGE " + (i + 1));
        }

        // when
        // then
        await().pollDelay(2, TimeUnit.SECONDS)
                .atMost(10, TimeUnit.SECONDS)
                .untilAsserted(() -> assertThat(consumer.getMessageList())
                        .hasSize(cnt));
    }

}