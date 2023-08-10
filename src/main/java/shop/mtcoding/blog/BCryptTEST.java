package shop.mtcoding.blog;

import org.mindrot.jbcrypt.BCrypt;

public class BCryptTEST {
    public static void main(String[] args) {
        String encPassword = BCrypt.hashpw("1234", BCrypt.gensalt());
        System.out.println("encPassword:" + encPassword);

        boolean isValid = BCrypt.checkpw("1235", encPassword);
        System.out.println(isValid);
    }

}
