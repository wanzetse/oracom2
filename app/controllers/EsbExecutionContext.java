package controllers;

import akka.actor.ActorSystem;
import play.libs.concurrent.CustomExecutionContext;

import javax.inject.Inject;

public class EsbExecutionContext extends CustomExecutionContext {

    @Inject
    public EsbExecutionContext(ActorSystem actorSystem) {
        super(actorSystem, "esb-dispatcher");
    }
}
