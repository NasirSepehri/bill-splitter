package com.example.billsplitter.service;

import com.example.billsplitter.component.MessageByLocaleComponent;
import com.example.billsplitter.dto.PaymentsResponseDto;
import com.example.billsplitter.entity.Client;
import com.example.billsplitter.entity.Cost;
import com.example.billsplitter.entity.Event;
import com.example.billsplitter.exception.AppException;
import com.example.billsplitter.mapper.CostMapper;
import com.example.billsplitter.repo.CostRepository;
import com.example.billsplitter.repo.EventRepository;
import com.example.billsplitter.service.impl.CostServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Profile("test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CostServiceImpl.class)
class CostServiceTest {

    @MockBean
    EventRepository eventRepository;

    @MockBean
    CostRepository costRepository;

    @MockBean
    CostMapper costMapper;

    @MockBean
    MessageByLocaleComponent messageByLocaleComponent;

    @Autowired
    CostService costServiceImpl;


    @Test
    void test_calculatePayments() {

        Event event = new Event();
        event.setId(1L);
        event.setName("event1");
        event.setClient(new Client() {{
            setUsername("Client1");
        }});
        event.setEventMembers(List.of("Client1", "Client2", "Client3", "Client4"));

        event.setCreatedDate(Instant.now());


        Cost cost1 = new Cost();
        cost1.setCostAmount(60.0F);
        cost1.setCostDescription("Cost1");
        cost1.setSplitBetween(List.of("Client1", "Client2"));
        cost1.setPaidBy("Client3");
        cost1.setEvent(event);

        Cost cost2 = new Cost();
        cost2.setCostAmount(70.0F);
        cost2.setCostDescription("Cost2");
        cost2.setSplitBetween(List.of("Client1", "Client2", "Client3"));
        cost2.setPaidBy("Client3");
        cost2.setEvent(event);

        Cost cost3 = new Cost();
        cost3.setCostAmount(80.0F);
        cost3.setCostDescription("Cost3");
        cost3.setSplitBetween(List.of("Client1", "Client2", "Client4"));
        cost3.setPaidBy("Client3");
        cost3.setEvent(event);

        event.setCosts(List.of(cost1, cost2, cost3));

        Mockito.when(eventRepository.findById(1L)).thenReturn(Optional.of(event));


//      	        Client1	    Client2 	Client3	    Client4		 |   Cost
//    ---------------------------------------------------------------|------------------
//    Cost1	        -30	        -30	        60	        0		     |   60
//    Cost2	        -23.33	    -23.33	    46.66	    0		     |   70
//    Cost3	        -26.666666	-26.666666	79.98	    -26.666666	 |   80
//    ---------------------------------------------------------------|------------------
//    Column Sum	-80.00	    -80.00	    186.66666   -26.666666	 |   Row Sum=0


        PaymentsResponseDto paymentsResponseDto = costServiceImpl.calculatePayments(1L, "Client1");
        Map<String, Float> paymentsMap = paymentsResponseDto.getPayments();

        assertEquals(-80.00F, paymentsMap.get("Client1"));
        assertEquals(-80.00F, paymentsMap.get("Client2"));
        assertEquals(186.66666F, paymentsMap.get("Client3"));
        assertEquals(-26.666666F, paymentsMap.get("Client4"));

    }

    @Test
    void test_calculatePayments_checkPermission() {
        Event event = new Event();
        event.setId(1L);
        event.setName("event1");
        event.setClient(new Client() {{
            setUsername("Client1");
        }});
        event.setEventMembers(List.of("Client1", "Client2", "Client3", "Client4"));

        event.setCreatedDate(Instant.now());
        Mockito.when(eventRepository.findById(1L)).thenReturn(Optional.of(event));

        assertThrows(AppException.Forbidden.class, () -> costServiceImpl.calculatePayments(1L, "Client22"));
    }
}
