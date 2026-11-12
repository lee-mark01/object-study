package study.chapter01.step02;

public class Main {
    public static void main(String[] args) {
        Ticket ticket1 = new Ticket(10_000L);
        Ticket ticket2 = new Ticket(10_000L);

        TicketOffice ticketOffice = new TicketOffice(0L, ticket1, ticket2);

        TicketSeller ticketSeller = new TicketSeller(ticketOffice);
        Theater theater = new Theater(ticketSeller);

        Audience invitedAudience = new Audience(new Bag(new Invitation(), 0L));
        Audience normalAudience = new Audience(new Bag(20_000L));

        theater.enter(invitedAudience);
        theater.enter(normalAudience);
    }
}
