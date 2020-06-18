package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Seat;
import at.ac.tuwien.sepm.groupphase.backend.entity.SeatGroupArea;
import at.ac.tuwien.sepm.groupphase.backend.entity.StandingArea;

import javax.validation.constraints.NotNull;

public interface SeatService {
    /**
     * Find the seat with the specified ID
     * @param id id of the seat to fetch
     * @return the seat with this ID
     */
    Seat getSeat(Long id);

    /**
     * Find a seat based on its X,Y Coordinates and a specific seat group
     * @param seatGroup seatGroup of the seat
     * @param x location of the seat
     * @param y location of the seat
     * @return
     */
    Seat byPosition(SeatGroupArea seatGroup, Double x, Double y);

    /**
     * Find a seatgroup area by its id
     * @param id id of the area
     * @return the area with this id
     */
    SeatGroupArea getArea(Long id);

    /**
     * Find a standing area by its id
     * @param id id of the area
     * @return the area with this id
     */
    StandingArea getStandingArea(Long id);
}
