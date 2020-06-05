package at.ac.tuwien.sepm.groupphase.backend.dto;

import at.ac.tuwien.sepm.groupphase.backend.entity.Address;
import at.ac.tuwien.sepm.groupphase.backend.entity.Location;
import at.ac.tuwien.sepm.groupphase.backend.entity.Seatmap;

public class LocationCreationWithSeatmap {
    private Location location;
    private Seatmap seatmap;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Seatmap getSeatmap() {
        return seatmap;
    }

    public void setSeatmap(Seatmap seatmap) {
        this.seatmap = seatmap;
    }
}
