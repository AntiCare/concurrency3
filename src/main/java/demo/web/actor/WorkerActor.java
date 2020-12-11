package demo.web.actor;

import akka.actor.UntypedActor;
import demo.web.model.Hotel;
import demo.web.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

@Component("workerActor")
@Scope("prototype")
public class WorkerActor extends UntypedActor {

    @Autowired
    private BusinessService businessService;

    private final CompletableFuture<Hotel> completableFuture;

    public WorkerActor(CompletableFuture<Hotel> completableFuture) {
        this.completableFuture = completableFuture;
    }

    @Override
    public void onReceive(Object hotel) throws Exception {
        businessService.perform(this + " " + hotel);

        if (hotel instanceof Hotel) {
            completableFuture.complete((Hotel) hotel);
        } else {
            unhandled(hotel);
        }

        getContext().stop(self());
    }
}
