package demo.web.controller;

import demo.web.model.Hotel;
import demo.web.model.Request;
import demo.web.model.Room;
import demo.web.service.CompletableFutureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class HotelController {

    private static final Long DEFERRED_RESULT_TIMEOUT = 1000L;

    private final AtomicLong id = new AtomicLong(0);
    private DeferredResult<ArrayList<Hotel>> deferredResult = new DeferredResult<>(DEFERRED_RESULT_TIMEOUT);

    @Autowired
    private CompletableFutureService completableFutureService;

    @PostMapping(value ="/hotels", consumes="application/json")
    public ArrayList<Hotel> addHotel(@RequestBody Request request) {
        Hotel hotel = new Hotel(request.nameOfHotel, new ArrayList<Room>());

        for(int i =0; i<request.theAmountOfRooms; i++){
            hotel.getRoomArrayList().add(new Room());
        }

        completableFutureService.listHotels().add(hotel);

        CompletableFuture<ArrayList<Hotel>> completableFuture = completableFutureService.getHotels();
        completableFuture.whenComplete((result, error) -> {
            if (error != null) {
                deferredResult.setErrorResult(error);
            } else {
                deferredResult.setResult(result);
            }
        });
        return completableFutureService.listHotels();
    }

    @PostMapping(value ="/newhotel", consumes="application/json")
    public ArrayList<Hotel> addHotelTo(@RequestBody Request request) {
        Hotel hotel = new Hotel(request.nameOfHotel, new ArrayList<Room>());

        for(int i =0; i<request.theAmountOfRooms; i++){
            hotel.getRoomArrayList().add(new Room());
        }

        completableFutureService.listHotels().add(hotel);

        CompletableFuture<ArrayList<Hotel>> completableFuture = completableFutureService.getHotels();
        completableFuture.whenComplete((result, error) -> {
            if (error != null) {
                deferredResult.setErrorResult(error);
            } else {
                deferredResult.setResult(result);
            }
        });
        return completableFutureService.listHotels();
    }

    @GetMapping("/hotels")
    public ArrayList<Hotel> listHotels() {
        CompletableFuture<ArrayList<Hotel>> completableFuture = completableFutureService.getHotels();
        completableFuture.whenComplete((result, error) -> {
            if (error != null) {
                error.printStackTrace();
                deferredResult.setErrorResult(error);
            } else {
                deferredResult.setResult(result);
            }
        });
        return completableFutureService.listHotels();
    }

    @GetMapping("/room")
    public ArrayList<Hotel> addRoom(@RequestParam int index) {
        CompletableFuture<ArrayList<Hotel>> completableFuture = completableFutureService.getHotels();
        Hotel hotel = completableFutureService.listHotels().get(index);
        hotel.getRoomArrayList().add(new Room());
        completableFuture.whenComplete((result, error) -> {
            if (error != null) {
                error.printStackTrace();
                deferredResult.setErrorResult(error);
            } else {

                deferredResult.setResult(result);
            }
        });

        return completableFutureService.listHotels();
    }

    @GetMapping("/book-room")
    public ArrayList<Hotel> bookRoom(@RequestParam int index, @RequestParam int room) {
        CompletableFuture<ArrayList<Hotel>> completableFuture = completableFutureService.getHotels();
        Hotel hotel = completableFutureService.listHotels().get(index);
        if(hotel.getRoomArrayList().get(room) !=null){
            hotel.getRoomArrayList().get(room).setAvailable(false);
        };
        completableFuture.whenComplete((result, error) -> {
            if (error != null) {
                error.printStackTrace();
                deferredResult.setErrorResult(error);
            } else {

                deferredResult.setResult(result);
            }
        });

        return completableFutureService.listHotels();
    }

    @GetMapping("/cancel-room")
    public ArrayList<Hotel> cancelRoom(@RequestParam int index, @RequestParam int room) {
        CompletableFuture<ArrayList<Hotel>> completableFuture = completableFutureService.getHotels();
        Hotel hotel = completableFutureService.listHotels().get(index);
        if(hotel.getRoomArrayList().get(room) !=null){
            hotel.getRoomArrayList().get(room).setAvailable(true);
        };
        completableFuture.whenComplete((result, error) -> {
            if (error != null) {
                error.printStackTrace();
                deferredResult.setErrorResult(error);
            } else {

                deferredResult.setResult(result);
            }
        });

        return completableFutureService.listHotels();
    }
}
