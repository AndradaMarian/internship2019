import java.util.HashMap;
import java.util.Map;

public class Bank {
    Map<String,User> users;

    public Bank() {
        users=new HashMap<>();
    }
    public void addUser(User newUser) throws BankException {
        if(users.putIfAbsent(newUser.getId(),newUser)==null){
            throw new BankException("User already exists");
        }
    }


}
