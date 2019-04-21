import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        User user=new User("Mihai");
        user.addCard(new Card("1","SILVER",0.002,2000, LocalDate.of(2020,5,23),4000));
        user.addCard(new Card("2","GOLD",0.001,2000, LocalDate.of(2018,8,15),2000));
        user.addCard(new Card("3","PLATINUM",0.003,2000, LocalDate.of(2019,3,20),3000));
        user.addCard(new Card("4","IRIDIUM",0.002,2000, LocalDate.of(2020,6,23),5000));
        user.addCard(new Card("5","BRONZE",0.005,2000, LocalDate.of(2019,7,15),2500));
        user.addCard(new Card("6","PREMIUM",0.0015,2000, LocalDate.of(2019,8,20),2000));
        Transaction transaction=new Transaction(TransactionType.ONLINE,10000,0.19,user);
        try {
            user.buy(10000,0.19).forEach((x,y)->{
                System.out.println(x.getCardType());
                System.out.println(y);
            });
        } catch (BankException e) {
            e.printStackTrace();
        }

    }
}
