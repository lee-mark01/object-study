package study.chapter01;


import chapter01.step04.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("소극장 티켓 판매")
public class TheaterTest {

    private static final long TICKET_FEE = 10_000L;

    @Nested
    @DisplayName("초대장이 있는 관람객이")
    class InvitedAudience {

        @Test
        @DisplayName("티켓을 사면 돈을 내지 않는다")
        void paysNothing() throws Exception {
            // 초대장과 2만원을 가진 관람객
            Audience audience = new Audience(new Bag(new Invitation(), 20_000L));

            // 티켓을 산다
            Long paid = audience.buy(ticket());

            // 낸 금액이 0원인지 확인한다
            assertEquals(0L, paid);
        }
    }

    @Nested
    @DisplayName("초대장이 없는 관람객이")
    class UninvitedAudience {

        @Test
        @DisplayName("티켓을 사면 티켓의 요금만큼 돈을 지불한다")
        void paysFee() throws Exception {
            // 초대장 없이 2만원만 가진 관람객
            Audience audience = new Audience(new Bag(20_000L));

            // 티켓을 산다
            Long paid = audience.buy(ticket());

            // 지불한 금액이 티켓 요금과 같은지 확인한다
            assertEquals(TICKET_FEE, paid);
        }
    }

    @Nested
    @DisplayName("매표소에 티켓이 한 장 남았을 때")
    class OneTicketLeft {

        @Test
        @DisplayName("첫 번째 관람객은 입장한다")
        void firstAudienceEnters() throws Exception {
            // 티켓의 수량을 1로 설정
            Theater theater = theaterWithTickets(1);

            // 첫 번째 관람객이 입장한다
            assertDoesNotThrow(() -> theater.enter(new Audience(new Bag(20_000L))));
        }

        @Test
        @DisplayName("두 번째 관람객은 티켓을 받지 못해 예외가 난다")
        void secondAudienceFails() throws Exception {
            // 티켓의 수량을 1로 설정 첫 번째 관람객은 이미 입장
            Theater theater = theaterWithTickets(1);
            theater.enter(new Audience(new Bag(20_000L)));

            // 두 번째 관람객은 입장하지 못한다
            assertThrows(IndexOutOfBoundsException.class, () -> theater.enter(new Audience(new Bag(20_000L))));
        }
    }


    private Theater theaterWithTickets(int count) throws Exception {
        Ticket[] tickets = new Ticket[count];
        for(int i = 0; i < count; i++) {
            tickets[i] = ticket();
        }

        return new Theater(new TicketSeller(new TicketOffice(0L, tickets)));
    }

    private Ticket ticket() throws Exception {
        Ticket ticket = new Ticket(TICKET_FEE);
        return ticket;
    }
}
