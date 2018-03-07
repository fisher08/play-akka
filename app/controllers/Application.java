package controllers;

/**
 * Created by Kirill on 05.03.2018.
 */
import actors.FrontendActor;
import actors.HelloActor;
import actors.HelloActorProtocol;
import actors.MessageActor;
import akka.actor.*;
import akka.routing.FromConfig;
import play.mvc.*;
import scala.compat.java8.FutureConverters;
import javax.inject.*;
import java.util.concurrent.CompletionStage;

import static akka.pattern.Patterns.ask;

@Singleton
public class Application extends Controller {

    final ActorRef helloActor;
    final ActorRef messageActor;

    @Inject public Application(ActorSystem system) {
        helloActor = system.actorOf(HelloActor.getProps());
//        ActorSelection selection =
//                system.actorSelection("akka.tcp://ClusterSystem@192.168.2.22:2552/user/message");
//        selection.tell("Pretty awesome feature", null);
        messageActor = system.actorOf(FrontendActor.getProps(), "frontend");
        messageActor.tell("345345", null);
//        workerRouter = system.actorOf(FromConfig.getInstance().props(), "workerRouter");
    }

    public CompletionStage<Result> sayHello(String name) {
        messageActor.tell("tell second", null);
        return FutureConverters.toJava(ask(helloActor, new HelloActorProtocol.SayHello(name), 1000))
                .thenApply(response -> ok((String) response));
    }

    public CompletionStage<Result> sendMessage(String message) {
        return FutureConverters.toJava(ask(messageActor, message, 1000))
                .thenApply(response -> ok((String) response));
    }
}