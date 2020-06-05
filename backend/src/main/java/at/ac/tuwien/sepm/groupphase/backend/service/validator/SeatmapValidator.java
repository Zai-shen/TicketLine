package at.ac.tuwien.sepm.groupphase.backend.service.validator;

import at.ac.tuwien.sepm.groupphase.backend.entity.Seatmap;

public class SeatmapValidator extends Validator<Seatmap> {
    @Override
    protected void doValidation(Seatmap seatmap) {
        callValidatorOnChildColleciton(new SeatGroupAreaValidator(), seatmap.getSeatGroupAreas());
        callValidatorOnChildColleciton(new StandingAreaValidator(), seatmap.getStandingAreas());
    }
}