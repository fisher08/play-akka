package actors;

/**
 * Created by Kirill on 05.03.2018.
 */
public class HelloActorProtocol {

    public static class SayHello {
        public final String name;

        public SayHello(String name) {
            this.name = name;
        }
    }
}