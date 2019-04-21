import java.time.LocalDate;
import java.util.*;

public class Transaction {


    private final TransactionType transactionType;
    private final double amount;
    private final double tvaRate;
    private final User user;
    public Transaction(TransactionType transactionType, double amount, double tvaRate, User user) {
        this.transactionType = transactionType;
        this.amount = amount;
        this.tvaRate = tvaRate;
        this.user = user;
    }
    public Map<Card, Cost> getCardsCost() throws BankException {
        Map<Card, Cost>costMap=new HashMap<>();
        /*
        *
        */
        double remainingSum=amount;
        List<Card> validCards=getValidCards();
        if(validCards.isEmpty()){
            throw new BankException("The user doesn't have any cards");
        }
        /*
         *We choose the card with the minumum fee to be the card used for the payment
         */
        Card firstChoice=validCards.get(0);
        if(firstChoice.getMaxUsableAmount()>=remainingSum){
            firstChoice.payment(remainingSum);
            costMap.putIfAbsent(firstChoice,getCost(firstChoice,remainingSum));
            return costMap;
        }
        double tvaFirst=tvaRate*firstChoice.getMaxUsableAmount();   //saving the tva only for the money that were initially on the first card
        remainingSum=amount/(1-firstChoice.getFee());               //calculating the sum which we need to have on the card for the payment to be succesfull
                                                                    //sum-sum*fee=payment=>payment=sum/(1-fee)
        remainingSum-=firstChoice.getMaxUsableAmount();
        int noCards=validCards.size();
        int index=1;
        double deposit;
        double payment;
        while(amount>firstChoice.getMaxUsableAmount() && index<noCards){  //continue choosing cards tiil we reach the sum or we don't have any cards left
            Card nextChoice=validCards.get(index);

            if(remainingSum>=nextChoice.getMaxUsableAmount()){
                deposit=nextChoice.getMaxUsableAmount();
                payment=nextChoice.getAvailableAmount();
                nextChoice.payment(payment);
                firstChoice.deposit(deposit);
                remainingSum-=deposit;
                costMap.putIfAbsent(nextChoice,getCost(nextChoice,payment));

            }
            else
            {
                deposit=remainingSum;
                payment=remainingSum;
                nextChoice.payment(payment);
                firstChoice.deposit(payment);
                costMap.putIfAbsent(nextChoice,getCost(nextChoice,payment));
                remainingSum-=deposit;
            }
            index++;
        }
        double comisionFirst=firstChoice.getMaxUsableAmount()*firstChoice.getFee();
        costMap.putIfAbsent(firstChoice,new Cost(tvaFirst,comisionFirst));
        return costMap;
    }
    private void transfer(Card source, Card destination,float amount) throws BankException {
        source.payment(amount);
        destination.deposit(amount);
    }
    private Cost getCost(Card card, double amount){
        double fee=amount*card.getFee();
        amount-=amount*card.getFee();
       double tva=amount*tvaRate;

       return new Cost(tva,fee);
    }

    private List<Card> getValidCards(){
        List<Card> validCards=new ArrayList<>();
        user.getCards().forEach(x->{
            if(x.getExpirationDate().isAfter(LocalDate.of(2019,3,19))){
                validCards.add(x);
            }
        });
        Comparator<Card>cardComparator=new Comparator<Card>() {
            @Override
            public int compare(Card o1, Card o2) {
                if(o1.getFee()==o2.getFee())
                    return 0;
                if(o1.getFee()<o2.getFee()){
                    return -1;
                }
                if(o1.getFee()>o2.getFee()){
                    return 1;
                }
                return 0;
            }
        };

        Collections.sort(validCards,cardComparator);
        return validCards;

    }

}
