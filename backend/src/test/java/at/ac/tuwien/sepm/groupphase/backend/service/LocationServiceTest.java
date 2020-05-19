package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Location;
import at.ac.tuwien.sepm.groupphase.backend.exception.BusinessValidationException;
import at.ac.tuwien.sepm.groupphase.backend.exception.DuplicateEntityException;
import at.ac.tuwien.sepm.groupphase.backend.repository.LocationRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.impl.LocationServiceImpl;
import at.ac.tuwien.sepm.groupphase.backend.util.DomainTestObjectFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LocationServiceTest {

    @Mock
    private LocationRepository locationRepository;
    @InjectMocks
    private LocationServiceImpl locationService;

    @Test
    public void testCreateLocationValidInput() {
        final Location location = DomainTestObjectFactory.getLocation();

        locationService.createLocation(location);

        verify(locationRepository, times(1)).save(location);
    }

    @Test
    public void testCreateLocationInvalidInput() {
        Location location = DomainTestObjectFactory.getLocation();
        location.setAddress(null);

        assertThatThrownBy(() -> locationService.createLocation(location)).isExactlyInstanceOf(
            BusinessValidationException.class);

        verify(locationRepository, never()).save(location);
    }
}
