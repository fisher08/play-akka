package actors;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.cluster.ClusterEvent;
import akka.cluster.Member;
import akka.cluster.MemberStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kirill on 06.03.2018.
 */
public class MessageActor extends AbstractActor {

    Map<String, Integer> cache = new HashMap<String, Integer>();

    public static Props getProps() {
        return Props.create(MessageActor.class);
    }

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .match(String.class, message -> {
                    String reply = message;
                    System.out.println(reply);
                    sender().tell(reply, self());
                })
               .build();
    }
}