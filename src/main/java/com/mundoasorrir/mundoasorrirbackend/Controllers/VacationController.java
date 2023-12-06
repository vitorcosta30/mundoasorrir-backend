package com.mundoasorrir.mundoasorrirbackend.Controllers;

import com.mundoasorrir.mundoasorrirbackend.Auth.AuthUtils;
import com.mundoasorrir.mundoasorrirbackend.Auth.JwtUtils;
import com.mundoasorrir.mundoasorrirbackend.Auth.Response.MessageResponse;
import com.mundoasorrir.mundoasorrirbackend.DTO.Vacation.VacationDTO;
import com.mundoasorrir.mundoasorrirbackend.DTO.Vacation.VacationMapper;
import com.mundoasorrir.mundoasorrirbackend.Domain.Event.BaseEventType;
import com.mundoasorrir.mundoasorrirbackend.Domain.Event.Event;
import com.mundoasorrir.mundoasorrirbackend.Domain.Event.EventType;
import com.mundoasorrir.mundoasorrirbackend.Domain.User.SystemUser;
import com.mundoasorrir.mundoasorrirbackend.Domain.Vacation.Vacation;
import com.mundoasorrir.mundoasorrirbackend.Repositories.EventTypeRepository;
import com.mundoasorrir.mundoasorrirbackend.Services.EventService;
import com.mundoasorrir.mundoasorrirbackend.Services.UserService;
import com.mundoasorrir.mundoasorrirbackend.Services.VacationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@CrossOrigin(origins = "${mundoasorrir.app.frontend}", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/vacation")
@RequiredArgsConstructor
public class VacationController {
    private final VacationService vacationService;
    private final EventService eventService;

    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(VacationController.class);

    @Autowired
    private final AuthUtils authUtils;

    @Autowired
    JwtUtils jwtUtils;
    private final EventTypeRepository eventTypeRepository;

    @GetMapping(value = "/getPendingRequests")
    public ResponseEntity<?> getPendingRequests(HttpServletRequest request) {
        if(!this.authUtils.lowPermissions(request)){
            return ResponseEntity.status(401).body(new MessageResponse("Not allowed"));
        }
        List<VacationDTO> res = VacationMapper.toDTO(this.vacationService.getPendingRequests());
        return ResponseEntity.ok().body(res);
    }

    @PostMapping(value = "/createRequest")
    public ResponseEntity<?> createRequest(@RequestParam("startDate") String startDate,@RequestParam("endDate") String endDate, HttpServletRequest request ){
        if(!this.authUtils.lowPermissions(request)){
            return ResponseEntity.status(401).body(new MessageResponse("Not allowed"));
        }
        String username = jwtUtils.getUserNameFromJwtToken(jwtUtils.getJwtFromCookies(request));
        SystemUser user = this.userService.findUserByUsername(username);
        Date startDateOb;
        Date endDateOb;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try{
            startDateOb = dateFormat.parse(startDate);
            endDateOb = dateFormat.parse(endDate);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Not a valid date!"));
        }
        Vacation vacation = new Vacation(startDateOb,endDateOb,user);
        this.vacationService.save(vacation);
        return ResponseEntity.ok(new MessageResponse("Vacation request created successfully!"));
    }

    @PostMapping(value = "/rejectRequest")
    public ResponseEntity<?> rejectRequest(@RequestParam("requestId")Long  requestId, HttpServletRequest request){
        if(!this.authUtils.mediumPermissions(request)){
            return ResponseEntity.status(401).body(new MessageResponse("Not allowed"));

        }
        try{
            this.vacationService.rejectVacation(requestId);
            logger.info("Vacation request rejected.");

            return ResponseEntity.ok(new MessageResponse("Vacation request rejected successfully!"));

        }catch(Exception e){
            logger.info("Error rejecting vacation request.");

            return ResponseEntity.badRequest().body(new MessageResponse("There was an error in rejecting the request"));
        }
    }


    @PostMapping(value = "/acceptRequest")
    public ResponseEntity<?> acceptRequest(@RequestParam("requestId")Long  requestId, HttpServletRequest request){
        if(!this.authUtils.mediumPermissions(request)){
            return ResponseEntity.status(401).body(new MessageResponse("Not allowed"));

        }
        try{
            this.vacationService.acceptVacation(requestId);
            Vacation vacation = this.vacationService.getById(requestId);
            String username = jwtUtils.getUserNameFromJwtToken(jwtUtils.getJwtFromCookies(request));
            String description = "Aprovado por: " + username;
            List<SystemUser> users = new ArrayList<>();
            users.add(vacation.getUser());
            Event markedVacation = new Event(users,"Não definido",description,vacation.getStartDate(),vacation.getEndDate());
            List<EventType> eventTypesSaved = eventTypeRepository.findAll();
            for(int i = 0; i  < eventTypesSaved.size(); i++){
                if(BaseEventType.VACATION.equals(eventTypesSaved.get(i))){
                    markedVacation.setEventType(eventTypesSaved.get(i));
                    eventService.save(markedVacation);
                    logger.info("Vacation request accepted;");
                    return ResponseEntity.ok(new MessageResponse("Vacation request accepted successfully!"));

                }
            }

            markedVacation.setEventType(BaseEventType.VACATION);
            eventTypeRepository.save(BaseEventType.VACATION);
            eventService.save(markedVacation);



            logger.info("Vacation request accepted;");

            return ResponseEntity.ok(new MessageResponse("Vacation request accepted successfully!"));

        }catch(Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse("There was an error in accepting the request"));
        }
    }
    @PostMapping("/getActiveVacations")
    public ResponseEntity<?> getActiveVacations(@RequestParam("obsDate") String obsDate,HttpServletRequest request) {
        if(!this.authUtils.lowPermissions(request)){
            return ResponseEntity.status(401).body(new MessageResponse("No permission for this funcionality!!"));
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateOb;
        try{
            dateOb = dateFormat.parse(obsDate);
            List<VacationDTO> res = VacationMapper.toDTO(this.vacationService.getActiveVacations(dateOb));
            return ResponseEntity.ok(res);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Not a valid date!"));
        }
    }
    @GetMapping("/getActiveVacationsToday")
    public ResponseEntity<?> getActiveVacations(HttpServletRequest request) throws ParseException {
        if(!this.authUtils.lowPermissions(request)){
            return ResponseEntity.status(401).body(new MessageResponse("No permission for this funcionality!!"));
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        String todayDateString = dateFormat.format(today);
        Date obsDate = dateFormat.parse(todayDateString);

        List<VacationDTO> res = VacationMapper.toDTO(this.vacationService.getActiveVacations(obsDate));
        return ResponseEntity.ok(res);

    }






}