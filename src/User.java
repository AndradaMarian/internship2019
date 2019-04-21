import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class User {
    private String name;
    private String id;
    private Map<String,Card>cards;

    public User(String name) {
        this.name = name;
        cards=new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Card> getCards() {
        return StreamSupport.stream(cards.values().spliterator(),false).collect(Collectors.toList());
    }

    public void addCard(Card card) {
        this.cards.putIfAbsent(card.getId(),card);
    }
    public Map<Card,Cost> buy(double price, double tva) throws BankException {
        Transaction transaction=new Transaction(TransactionType.ONLINE,price,tva,this);
        return transaction.getCardsCost();
    }
}
