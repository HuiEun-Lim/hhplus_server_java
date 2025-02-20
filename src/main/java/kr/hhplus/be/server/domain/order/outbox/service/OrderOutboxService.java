package kr.hhplus.be.server.domain.order.outbox.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.domain.order.outbox.entity.OrderOutbox;
import kr.hhplus.be.server.domain.order.outbox.repository.OrderOutboxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderOutboxService {

    private final OrderOutboxRepository orderOutboxRepository;

    public void save(OrderOutbox orderOutbox) {
        orderOutboxRepository.save(orderOutbox);
    }

    public OrderOutbox findByMessageId(String messageId) {
        return orderOutboxRepository.findByMessageId(messageId);
    }

}
