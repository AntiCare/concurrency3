package demo.web.service;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import demo.web.model.Hotel;
import demo.web.model.Room;
import demo.web.spring.SpringExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

@Service
public class CompletableFutureService {
    public ArrayList<Hotel> hotels = new ArrayList<Hotel>();

    CompletableFutureService(){
        hotels.add(new Hotel("yang",  new ArrayList<Room>()));
        hotels.add(new Hotel("cheng",  new ArrayList<Room>()));
    }


    @Autowired
    private ActorSystem actorSystem;

    @Autowired
    private SpringExtension springExtension;

    public CompletableFuture<ArrayList<Hotel>> getHotels() {
        CompletableFuture<ArrayList<Hotel>> completableFuture = new CompletableFuture<>();
        ActorRef workerActor = actorSystem.actorOf(springExtension.props("workerActor", completableFuture), "worker-actor");
        workerActor.tell(hotels, null);
        return completableFuture;
    }

    public ArrayList<Hotel> listHotels() {
        CompletableFuture<ArrayList<Hotel>> completableFuture = new CompletableFuture<>();
        ActorRef workerActor = actorSystem.actorOf(springExtension.props("workerActor", completableFuture), randomString());
        workerActor.tell(hotels, null);
        return hotels;
    }

    public String randomString() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
