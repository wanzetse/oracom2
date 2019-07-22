/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modules.sms;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.http.javadsl.Http;
import akka.http.javadsl.model.ContentTypes;
import akka.http.javadsl.model.HttpEntities;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.util.ByteString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.concurrent.ExecutionContextExecutor;

import java.util.concurrent.CompletionStage;

import static akka.actor.TypedActor.context;
import static akka.pattern.PatternsCS.pipe;

public class SmsRequestActor extends AbstractActor {

    static public Props props() {
        return Props.create(SmsRequestActor.class, () -> new SmsRequestActor());
    }

    final Http http = Http.get(context().system());
    final ExecutionContextExecutor dispatcher = context().dispatcher();
    final Materializer materializer = ActorMaterializer.create(context());

    static public class Send_ {

        public final String message;

        public Send_(String message) {

            this.message = message;
        }

    }
    static final Logger logger = LoggerFactory.getLogger(SmsRequestActor.class);

    @Override
    public AbstractActor.Receive createReceive() {

        return receiveBuilder()
                .match(Send_.class, message -> {

                    ByteString data = ByteString.fromString(message.message);

                    logger.info("Now at SMSRequest actor to send  SMS | {} |", message.message);

                    HttpRequest complexRequest
                            = HttpRequest.POST("http://192.168.2.115:8080/SmsService/RequestInit")
                            .withEntity(HttpEntities.create(ContentTypes.APPLICATION_JSON, data));

                    CompletionStage<HttpResponse> response = http.singleRequest(complexRequest, materializer);
                    pipe(response, dispatcher).to(self());
                }).match(HttpResponse.class, infobip -> {
            logger.info("Response from Data Vision | {} |", infobip.entity().toString());

        })
                .build();
    }
}
