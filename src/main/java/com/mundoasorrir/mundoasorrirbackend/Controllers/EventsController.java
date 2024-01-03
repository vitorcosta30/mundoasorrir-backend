package com.mundoasorrir.mundoasorrirbackend.Controllers;

import com.mundoasorrir.mundoasorrirbackend.Auth.AuthUtils;
import com.mundoasorrir.mundoasorrirbackend.Auth.JwtUtils;
import com.mundoasorrir.mundoasorrirbackend.Auth.Response.MessageResponse;
import com.mundoasorrir.mundoasorrirbackend.DTO.Event.EventDTO;
import com.mundoasorrir.mundoasorrirbackend.DTO.Event.EventMapper;
import com.mundoasorrir.mundoasorrirbackend.DTO.User.UserDTO;
import com.mundoasorrir.mundoasorrirbackend.DTO.User.UserMapper;
import com.mundoasorrir.mundoasorrirbackend.Domain.Event.BaseEventType;
import com.mundoasorrir.mundoasorrirbackend.Domain.Event.Event;
import com.mundoasorrir.mundoasorrirbackend.Domain.Event.EventType;
import com.mundoasorrir.mundoasorrirbackend.Domain.User.SystemUser;
import com.mundoasorrir.mundoasorrirbackend.Domain.UserGroup.UserGroup;
import com.mundoasorrir.mundoasorrirbackend.Message.ErrorMessage;
import com.mundoasorrir.mundoasorrirbackend.Message.ResponseMessage;
import com.mundoasorrir.mundoasorrirbackend.Message.SuccessMessage;
import com.mundoasorrir.mundoasorrirbackend.Repositories.EventTypeRepository;
import com.mundoasorrir.mundoasorrirbackend.Services.EventService;
import com.mundoasorrir.mundoasorrirbackend.Services.UserGroupService;
import com.mundoasorrir.mundoasorrirbackend.Services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
@CrossOrigin(origins = "${mundoasorrir.app.frontend}", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventsController {

    private static final Logger logger = LoggerFactory.getLogger(EventsController.class);

    @Autowired
    private EventService eventService;
    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    EventTypeRepository eventTypeRepository;
    @Autowired
    private final AuthUtils authUtils;

    private final UserService userService;

    private final UserGroupService userGroupService;

    /**
     *
     * @param request
     * @return
     */
    @GetMapping("/getEvents")
    public ResponseEntity<List<EventDTO>> getEvents(HttpServletRequest request) {
        String username = jwtUtils.getUserNameFromJwtToken(jwtUtils.getJwtFromCookies(request));
        List<EventDTO> userEvents = EventMapper.toDTO(eventService.getAllEventsFromUsername(username).toList());


        logger.info("Events fetched for user "+username);
        return ResponseEntity.status(HttpStatus.OK).body(userEvents);

    }

    /**
     *
     * @param username
     * @param request
     * @return
     */

    @GetMapping("/getUserEvents/{username}")
    public ResponseEntity<?> getUserEvents(@PathVariable String username,HttpServletRequest request) {
        if(!this.authUtils.lowPermissions(request)){
            return ResponseEntity.status(401).body(ErrorMessage.NOT_ALLOWED);
        }
        List<EventDTO> userEvents = EventMapper.toDTO(eventService.getAllEventsFromUsername(username).toList());
        logger.info("Events fetched for user "+username);
        return ResponseEntity.status(HttpStatus.OK).body(userEvents);

    }

    /**
     *
     * @param description
     * @param place
     * @param eventType
     * @param startDate
     * @param endDate
     * @param request
     * @param users
     * @param groups
     * @return
     */



    @PostMapping("/createEvent")
    public ResponseEntity<?> createEvent(@RequestParam("description") String description,@RequestParam("place") String place,@RequestParam("eventType") String eventType,@RequestParam("startDate") String startDate,@RequestParam("endDate") String endDate,HttpServletRequest request, @RequestParam(name = "users", required = false) List<String> users,@RequestParam(name = "groups", required = false) List<String> groups) {
        if(!this.authUtils.mediumPermissions(request)){
            return ResponseEntity.status(401).body(ErrorMessage.NOT_ALLOWED);
        }
        List<SystemUser> usersEnrolled = new ArrayList<>();
        if(groups != null){
            List<Long> groupsConverted = new ArrayList<>();
            for(int i = 0 ; i < groups.size(); i++){
                groupsConverted.add(Long.valueOf(groups.get(i)));
            }
            usersEnrolled.addAll(this.getUsersFromGroup(groupsConverted));
        }
        if(users != null){
            usersEnrolled.addAll(this.getUsersFromUsername(users));
        }
        usersEnrolled.add(this.authUtils.getUserFromRequest(request));
        usersEnrolled = usersEnrolled.stream().distinct().toList();

        Date startDateOb;
        Date endDateOb;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        try{
             startDateOb = dateFormat.parse(startDate);
             endDateOb = dateFormat.parse(endDate);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(ErrorMessage.INVALID_DATE);
        }

        Event event = new Event(usersEnrolled,place,description,startDateOb,endDateOb);
        if(eventType == null ){
            return ResponseEntity.badRequest().body(ErrorMessage.EVENT_TYPE_NULL);
        }
        if(eventService.create(event,eventType) != null){
            return ResponseEntity.ok(SuccessMessage.EVENT_CREATED);

        }else{
            return ResponseEntity.badRequest().body(ErrorMessage.EVENT_TYPE_NOT_EXIST);
        }

    }

    /**
     *
     * @return
     */
    @GetMapping(value = "/getEventType")
    public ResponseEntity<List<EventType>> getEventType() {
        return ResponseEntity.ok().body(Arrays.asList(BaseEventType.eventTypes()));
    }

    /**
     *
     * @param id
     * @param request
     * @return
     */

    @GetMapping(value = "/getEvent/{id}")
    public ResponseEntity<?> getEvent(@PathVariable Long id,HttpServletRequest request) {
        if(!this.authUtils.lowPermissions(request)){
            return ResponseEntity.status(401).body(ErrorMessage.NOT_ALLOWED);
        }
        try {
            EventDTO res = EventMapper.toDTO(eventService.getEventFromId(id));
            return ResponseEntity.ok().body(res);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(ErrorMessage.ERROR);
        }
    }

    /**
     *
     * @param id
     * @param request
     * @return
     */

    @GetMapping(value = "/getEventMembers/{id}")
    public ResponseEntity<?> getEventMembers(@PathVariable Long id, HttpServletRequest request) {
        if(!this.authUtils.lowPermissions(request)){
            return ResponseEntity.status(401).body(ErrorMessage.NOT_ALLOWED);
        }
        List<UserDTO> res = UserMapper.toDTO(eventService.getEventFromId(id).getEnrolledUsers());
        return ResponseEntity.ok().body(res);
    }

    /**
     *
     * @param groups
     * @return
     */

    private List<SystemUser> getUsersFromGroup(List<Long> groups){
        List<SystemUser> res = new ArrayList<>();
        for(int i = 0; i < groups.size(); i++){
            UserGroup group = this.userGroupService.findByGroupId(groups.get(i));
            res.addAll(group.getGroupUsers());
        }
        return res;
    }

    /**
     *
     * @param users
     * @return
     */
    private List<SystemUser> getUsersFromUsername(List<String> users){
        List<SystemUser> res = new ArrayList<>();
        for(int i = 0; i < users.size(); i++){
            SystemUser user = this.userService.findUserByUsername(users.get(i));
            res.add(user);
        }
        return res;
    }

}
