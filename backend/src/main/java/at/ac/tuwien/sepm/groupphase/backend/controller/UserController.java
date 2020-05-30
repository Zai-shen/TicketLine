package at.ac.tuwien.sepm.groupphase.backend.controller;

import at.ac.tuwien.sepm.groupphase.backend.api.UserApi;
import at.ac.tuwien.sepm.groupphase.backend.controller.mapper.*;
import at.ac.tuwien.sepm.groupphase.backend.dto.*;
import at.ac.tuwien.sepm.groupphase.backend.entity.Booking;
import at.ac.tuwien.sepm.groupphase.backend.entity.SeatedTicket;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import at.ac.tuwien.sepm.groupphase.backend.security.AuthorizationRole;
import at.ac.tuwien.sepm.groupphase.backend.service.BookingService;
import at.ac.tuwien.sepm.groupphase.backend.service.TicketService;
import at.ac.tuwien.sepm.groupphase.backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class UserController implements UserApi {

    private static final int USER_LIST_PAGE_SIZE = 25;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final UserMapper userMapper;
    private final UserService userService;
    private final UserInfoMapper userInfoMapper;
    private final BookingService bookingService;
    private final BookingMapper bookingMapper;
    private final TicketService ticketService;

    @Autowired
    public UserController(UserMapper userMapper, UserInfoMapper userInfoMapper, UserService userService, BookingService bookingService,
        BookingMapper bookingMapper, TicketService ticketService) {
        this.userMapper = userMapper;
        this.userInfoMapper = userInfoMapper;
        this.userService = userService;
        this.bookingService = bookingService;
        this.bookingMapper = bookingMapper;
        this.ticketService = ticketService;
    }

    @Override
    public ResponseEntity<Void> register(@Valid UserDTO userDTO) {
        LOGGER.info("Register new user {}", userDTO.getLogin().getEmail());
        User user = userMapper.toEntity(userDTO);
        userService.register(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    @Secured(AuthorizationRole.ADMIN_ROLE)
    public ResponseEntity<Void> resetPassword(Long userId, @Valid LoginDTO loginDTO) {
        LOGGER.info("Reset Password for ID={}", userId);
        userService.resetPassword(userId, loginDTO.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @Secured(AuthorizationRole.USER_ROLE)
    public ResponseEntity<Void> updateUser(Long userId, @Valid UserUpdateDTO userUpdateDTO) {
        LOGGER.info("updating user {}", userId);
        userService.updateUser(userId, userMapper.toEntity(userUpdateDTO));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @Secured(AuthorizationRole.USER_ROLE)
    public ResponseEntity<UserDTO> getSelf() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userService.findUserByEmail(username);
        return ResponseEntity.ok(userMapper.toDto(currentUser));
    }

    @Override
    @Secured(AuthorizationRole.ADMIN_ROLE)
    public ResponseEntity<List<UserInfoDTO>> getUsers(@Valid Optional<Boolean> locked, @Valid Optional<String> email,
        @Valid Optional<Integer> page) {
        LOGGER.info("Get users locked={}, email={}, page={}", locked, email, page);
        PageRequest pageRequest = PageRequest.of(page.orElse(0), USER_LIST_PAGE_SIZE);
        boolean showLockedUsers = locked.orElse(false);
        String searchEmail = email.orElse(null);

        Page<User> users = showLockedUsers ? userService.getLockedUsers(pageRequest, searchEmail) :
            userService.getAllUsers(pageRequest, searchEmail);

        return ResponseEntity.ok()
            .header("X-Total-Count", String.valueOf(users.getTotalElements()))
            .body(userInfoMapper.toDto(users.getContent()));
    }

    @Override
    @Secured(AuthorizationRole.USER_ROLE)
    public ResponseEntity<List<BookingDTO>> getTicketsOfUser() {
        LOGGER.info("Get all tickets for current user");
        List<BookingDTO> bookings = bookingMapper.fromEntity(bookingService.getAllBookingsOfUser());
        return ResponseEntity.ok(bookings);
    }

    @Override
    @Secured(AuthorizationRole.ADMIN_ROLE)
    public ResponseEntity<Void> unlockUser(Long userId) {
        LOGGER.info("Unlock user with id {}", userId);
        userService.unlockUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @Secured(AuthorizationRole.ADMIN_ROLE)
    public ResponseEntity<Void> lockUser(Long userId) {
        LOGGER.info("Lock user with id {}", userId);
        userService.lockUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Resource> getTicket(Long userId, Long bookingId) {
        List<TicketData> tickets = new LinkedList<>();

        Booking b = bookingService.getBooking(bookingId);
        for(Ticket ticket : b.getTickets()) {
            String seat = "Freie Platzwahl";
            if (ticket instanceof SeatedTicket) {
                seat = String.format("Reihe %d Platz %d",((SeatedTicket) ticket).getSeatRow(),((SeatedTicket) ticket).getSeatColumn());
            }
            tickets.add(new TicketData(
                b.getPerformance().getEvent(),
                seat,
                b.getPerformance(),
                UUID.randomUUID(),
                BigDecimal.valueOf(3.50)));
        }
        ByteArrayFile pdf = ticketService.renderTickets(tickets);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        headers.add("Content-Disposition", "filename=" + pdf.getName());
        headers.add("Access-Control-Expose-Headers", "Content-Disposition");
        headers.setContentLength(pdf.getContent().length);

        return new ResponseEntity<>(
            new InputStreamResource(new ByteArrayInputStream(pdf.getContent())), headers, HttpStatus.OK);
    }
}
