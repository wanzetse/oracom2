package controllers;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.dispatch.ExecutionContexts;
import akka.http.javadsl.Http;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.unmarshalling.Unmarshaller;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.concurrent.ExecutionContextExecutor;

import java.util.concurrent.CompletionStage;

import static akka.pattern.PatternsCS.pipe;

public class SmsController extends AbstractActor {

    static public Props getProps() {
        return Props.create(SmsController.class, SmsController::new);
    }

    final Http http = Http.get(context().system());
    final ExecutionContextExecutor dispatcher = context().dispatcher();
    final Materializer materializer = ActorMaterializer.create(context());


    static public class smsObject {

        public final String mobileNumber;
        public final String message;

        public smsObject(String mobileNumber, String message) {

            this.mobileNumber = mobileNumber;
            this.message = message;
        }

    }

    private static final Logger logger = LoggerFactory.getLogger(SmsController.class);

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(smsObject.class, message -> {

                    logger.info("SMS Request to PhoneNumber | {} | Message | {} |", message.mobileNumber, message.message);

                    HttpRequest complexRequest
                            = HttpRequest.GET("https://api.africastalking.com/restless/send?username=jmwai&Apikey=414a126c6471b1ae3f8e18f9c72a3e2da386a16ab5dfe626493d6be6ab1cd342&from=M-PAYA&to=" + message.mobileNumber + "&message=" + message.message);

                    CompletionStage<HttpResponse> response = http.singleRequest(complexRequest);
                    pipe(response, dispatcher).to(self());
                }).match(HttpResponse.class, africastalking -> {
                    CompletionStage<String> jsonResponse = Unmarshaller.entityToString().unmarshal(africastalking.entity(), ExecutionContexts.global(), materializer);

                    jsonResponse.thenApply(this::parse);

                })
                .build();
    }

    private boolean parse(String response) {
        logger.info("SMS response request from AfricasTalking : {}", response);
        return true;
    }
}
