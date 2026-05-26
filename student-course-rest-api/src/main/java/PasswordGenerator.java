import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/* for testing */
public class PasswordGenerator {
    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("student"));
        System.out.println(new BCryptPasswordEncoder().encode("admin"));
    }
}