package actors;

import akka.actor.*;
import akka.cluster.Member;
import akka.cluster.MemberStatus;
import akka.japi.*;
import actors.HelloActorProtocol.*;
import akka.cluster.ClusterEvent.CurrentClusterState;



/**
 * Created by Kirill on 05.03.2018.
 */

public class HelloActor extends AbstractActor {

    public static Props getProps() {
        return Props.create(HelloActor.class);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(SayHello.class, hello -> {
                    String reply = "Hello, " + hello.name;
                    sender().tell(reply, self());
                })
                .match(CurrentClusterState.class, message -> {
                    CurrentClusterState state = (CurrentClusterState) message;
                    System.out.println(message);
                    for (Member member : state.getMembers()) {
                        if (member.status().equals(MemberStatus.up())) {
                            System.out.println(member.address());
                        }
                    }
                })
                .build();
    }
}