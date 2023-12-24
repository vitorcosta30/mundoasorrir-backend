package com.mundoasorrir.mundoasorrirbackend.Controllers;

import com.mundoasorrir.mundoasorrirbackend.Auth.AuthUtils;
import com.mundoasorrir.mundoasorrirbackend.Auth.Response.MessageResponse;
import com.mundoasorrir.mundoasorrirbackend.DTO.Presence.PresenceMapper;
import com.mundoasorrir.mundoasorrirbackend.DTO.User.UserDTO;
import com.mundoasorrir.mundoasorrirbackend.DTO.User.UserMapper;
import com.mundoasorrir.mundoasorrirbackend.Domain.User.SystemUser;
import com.mundoasorrir.mundoasorrirbackend.Message.ResponseMessage;
import com.mundoasorrir.mundoasorrirbackend.Services.AttendanceService;
import com.mundoasorrir.mundoasorrirbackend.Services.EventService;
import com.mundoasorrir.mundoasorrirbackend.Services.UserService;
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
import java.util.Date;
import java.util.List;

@Slf4j
@CrossOrigin(origins = "${mundoasorrir.app.frontend}", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {
    @Autowired
    private EventService eventService;
    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private final AuthUtils authUtils;
    @Autowired

    private final UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(AttendanceController.class);

    @GetMapping(value = "/getUsersBusyToday")
    public ResponseEntity<?> getBusyUsers(HttpServletRequest request) throws ParseException {
        if(!this.authUtils.lowPermissions(request)){
            return ResponseEntity.status(401).body(new ResponseMessage("Not allowed"));
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        String todayDateString = dateFormat.format(today);
        Date obsDate = dateFormat.parse(todayDateString);
        List<UserDTO> res = UserMapper.toDTO(eventService.usersBusy(obsDate));
        return ResponseEntity.ok().body(res);
    }

    @PostMapping("/getUsersUnmarked")
    public ResponseEntity<?> getUsersForDayAttendance(@RequestParam("obsDate")String date, HttpServletRequest request ) {
        if(!this.authUtils.mediumPermissions(request)){
            return ResponseEntity.status(401).body(new ResponseMessage("Not allowed"));
        }

        Date obsDate ;
        try{
            obsDate = handleDate(date);

        }catch(Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Not a valid date!"));
        }
        if(!this.attendanceService.isInstantiated(obsDate)){
            List<SystemUser> users = userService.findAll();
            users.removeAll(eventService.usersBusy(obsDate));
            attendanceService.save(obsDate,users);
        }
        return ResponseEntity.ok().body(UserMapper.toDTO(this.attendanceService.getAttendanceUnmarkedByDate(obsDate)));
    }


    @PostMapping("/getAttendanceSheet")
    public ResponseEntity<?> getAttendanceSheet(@RequestParam("obsDate")String date, HttpServletRequest request ) {
        if(!this.authUtils.mediumPermissions(request)){
            return ResponseEntity.status(401).body(new ResponseMessage("Not allowed"));
        }
        Date obsDate ;
        try{
            obsDate = handleDate(date);

        }catch(Exception e){
            logger.error("Not a valid date - "+ date );
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Not a valid date!"));
        }
        if(!this.attendanceService.isInstantiated(obsDate)){
            List<SystemUser> users = userService.findAll();
            users.removeAll(eventService.usersBusy(obsDate));
            attendanceService.save(obsDate,users);
            logger.info("Created new attendance for day - " + obsDate.toString() );
        }
        return ResponseEntity.ok().body(PresenceMapper.toDTO(this.attendanceService.getPresencesInDay(obsDate)));
    }
    @PostMapping("/markPresent")
    public ResponseEntity<?> markPresent(@RequestParam("username") String username, @RequestParam("obsDate")String date, HttpServletRequest request ){
        if(!this.authUtils.mediumPermissions(request)){
            return ResponseEntity.status(401).body(new ResponseMessage("Not allowed"));
        }
        Date obsDate ;
        try{
            obsDate = handleDate(date) ;

        }catch(Exception e){
            logger.error("Not a valid date - "+ date );
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Not a valid date!"));
        }
        if(!this.attendanceService.isInstantiated(obsDate)){
            return ResponseEntity.badRequest().body(new MessageResponse("There has been an error: attendance not instantiated"));
        }
        SystemUser user = this.userService.findUserByUsername(username);
        if(this.attendanceService.markAsPresent(obsDate,user) != null){
            logger.info("Presence marked for user - "+ username + "in day" + obsDate.toString() );

            return ResponseEntity.ok().body(new MessageResponse("Presence marked successfully"));

        }else{
            return ResponseEntity.badRequest().body(new MessageResponse("There has been an error"));
        }

    }

    @PostMapping("/markAbsent")
    public ResponseEntity<?> markAbsent(@RequestParam("username") String username, @RequestParam("obsDate")String date,  HttpServletRequest request){
        if(!this.authUtils.mediumPermissions(request)){
            return ResponseEntity.status(401).body(new ResponseMessage("Not allowed"));
        }
        Date obsDate ;
        try{
            obsDate = handleDate(date) ;

        }catch(Exception e){
            logger.error("Not a valid date - "+ date );
            logger.error("Not a valid date - "+ date );
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Not a valid date!"));
        }
        if(!this.attendanceService.isInstantiated(obsDate)){
            return ResponseEntity.badRequest().body(new MessageResponse("There has been an error: attendance not instantiated"));
        }
        SystemUser user = this.userService.findUserByUsername(username);
        if(this.attendanceService.markAsAbsent(obsDate,user) != null){
            logger.info("Absence marked for user - "+ username + "in day" + obsDate.toString() );

            return ResponseEntity.ok().body(new MessageResponse("Absence marked successfully"));

        }else{
            return ResponseEntity.badRequest().body(new MessageResponse("There has been an error"));
        }
    }

    private Date handleDate(String date) throws ParseException {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.parse(date);
    }
}
