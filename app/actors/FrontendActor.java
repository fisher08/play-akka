package actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.routing.FromConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kirill on 06.03.2018.
 */
public class FrontendActor extends AbstractActor {

    Map<String, Integer> cache = new HashMap<String, Integer>();
    ActorRef backend = getContext().actorOf(FromConfig.getInstance().props(),
            "remote");

    public static Props getProps() {
        return Props.create(FrontendActor.class);
    }

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .match(String.class, message -> {
                    String reply = "Hello, " + message;
                    System.out.println(reply);
                    backend.tell(reply, getSelf());
                })
                .build();
    }
}