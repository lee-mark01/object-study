package study.chapter01;

import chapter01.step05.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("소극장 티켓 판매(step05)")
public class TheaterStep05Test {

    private static final long TICKET_FEE = 10_000L;

    @Nested
    @DisplayName("초대장이 있는 관람객이")
    class InvitedAudience {

        @Test
        @DisplayName("티켓을 사면 돈을 내지 않는다")
        void paysNothing() {

            // 초대장과 2만원을 가진 관람객
            Audience audience = new Audience(new Bag(new Invitation(), 20_000L));

            // 티켓을 산다
            Long paid = audience.buy(ticket());

            // 초대장을 가졌으니 0원 지불
            assertEquals(0L, paid);
        }

        @Test
        @DisplayName("돈이 없어도 티켓을 받는다")
        void receivesWithoutMoney() {
            Audience audience = new Audience(new Bag(new Invitation(), 0L));

            assertEquals(0L, audience.buy(ticket()));
        }
    }

    @Nested
    @DisplayName("초대장이 없는 관람객이")
    class UninvitedAudience {
        @Test
        @DisplayName("티켓을 사면 티켓 요금만큼 낸다")
        void paysFee() {
            Audience audience = new Audience(new Bag(20_000L));

            assertEquals(TICKET_FEE, audience.buy(ticket()));
        }

        @Test
        @DisplayName("가진 돈이 티켓 요금과 딱 같아도 산다")
        void paysExactAmount() {
            Audience audience = new Audience(new Bag(TICKET_FEE));

            assertEquals(TICKET_FEE, audience.buy(ticket()));
        }

        @Test
        @DisplayName("가진 돈이 티켓 요금보다 적으면 예외가 난다")
        void notEnoughMoney() {
            // 5천원만 가진 관람객
            Audience audience = new Audience(new Bag(5_000L));

            // 티켓을 사려 하면 잔액 부족 예외
            assertThrows(IllegalStateException.class, () -> audience.buy(ticket()));
        }
    }

    @Nested
    @DisplayName("매표소에 티켓이 한 장 남았을 때")
    class OneTicketLeft {

        @Test
        @DisplayName("첫 번째 관람객은 입장한다")
        void firstAudienceEnters() {
            Theater theater = theaterWithTickets(1);

            assertDoesNotThrow(() -> theater.enter(new Audience(new Bag(20_000L))));
        }

        @Test
        @DisplayName("두 번째 관람객은 티켓을 받지 못해 예외가 난다")
        void secondAudienceFails() {
            Theater theater = theaterWithTickets(1);
            theater.enter(new Audience(new Bag(20_000L)));

            assertThrows(IndexOutOfBoundsException.class,
                    () -> theater.enter(new Audience(new Bag(20_000L))));
        }

        @Test
        @DisplayName("돈이 부족한 관람객이 실패해도 티켓은 남아 다음 관람객이 입장한다")
        void ticketRemainsAfterFailedPurchase() {
            Theater theater = theaterWithTickets(1);

            // 5천원 관람객은 실패
            assertThrows(IllegalStateException.class,
                    () -> theater.enter(new Audience(new Bag(5_000L))));

            // 티켓이 남아 있어야 다음 관람객이 들어감
            assertDoesNotThrow(() -> theater.enter(new Audience(new Bag(20_000L))));
        }
    }

    private Theater theaterWithTickets(int count) {
        Ticket[] tickets = new Ticket[count];
        for (int i = 0; i < count; i++) {
            tickets[i] = ticket();
        }
        return new Theater(new TicketSeller(new TicketOffice(0L, tickets)));
    }

    private Ticket ticket() {
        return new Ticket(TICKET_FEE);
    }
}
