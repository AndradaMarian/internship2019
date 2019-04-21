import java.time.LocalDate;
import java.util.Objects;

public class Card {
    private final String id;
    private final String cardType;
    private final double fee;
    private int withdrawLimit;
    private final LocalDate expirationDate;
    private double availableAmount;



    public Card(String id, String cardType, double fee, int withdrawLimit, LocalDate expirationDate, float availableAmount) {
        this.id = id;
        this.cardType = cardType;
        this.fee = fee;
        this.withdrawLimit = withdrawLimit;
        this.expirationDate = expirationDate;
        this.availableAmount = availableAmount;
    }

    public String getCardType() {
        return cardType;
    }

    public double getFee() {
        return fee;
    }

    public int getWithdrawLimit() {
        return withdrawLimit;
    }

    public void setWithdrawLimit(int withdrawLimit) {
        this.withdrawLimit = withdrawLimit;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public double getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(double availableAmount) {
        this.availableAmount = availableAmount;
    }
public double getMaxUsableAmount(){
        return availableAmount/(1+fee);
}
    public String getId() {
        return id;
    }
    public void payment(double amount) throws BankException {
        if(amount<=this.availableAmount){
            availableAmount-=amount;
            availableAmount-=amount*fee;
        }
        else
        {
            throw new BankException("Transaction refused");
        }
    }
    public void deposit(double amount){
        availableAmount+=amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(id, card.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
