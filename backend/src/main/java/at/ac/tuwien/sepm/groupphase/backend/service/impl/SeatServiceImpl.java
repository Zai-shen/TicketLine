package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Seat;
import at.ac.tuwien.sepm.groupphase.backend.entity.SeatGroupArea;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.SeatGroupAreaRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.SeatRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.SeatService;
import org.springframework.stereotype.Service;

@Service
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;
    private final SeatGroupAreaRepository seatGroupAreaRepository;

    public SeatServiceImpl(SeatRepository seatRepository, SeatGroupAreaRepository seatGroupAreaRepository) {
        this.seatRepository = seatRepository;
        this.seatGroupAreaRepository = seatGroupAreaRepository;
    }

    @Override
    public Seat getSeat(Long id) {
        return seatRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public Seat byPosition(SeatGroupArea seatGroup, Double x, Double y) {
        return seatRepository.findBySeatGroupAreaAndXAndY(seatGroup,x,y);
    }

    @Override
    public SeatGroupArea getArea(Long id) {
        return seatGroupAreaRepository.findById(id).orElseThrow(NotFoundException::new);
    }
}
