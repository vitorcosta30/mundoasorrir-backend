package com.mundoasorrir.mundoasorrirbackend.Controllers;

import com.mundoasorrir.mundoasorrirbackend.Auth.Response.MessageResponse;
import com.mundoasorrir.mundoasorrirbackend.DTO.Presence.PresenceMapper;
import com.mundoasorrir.mundoasorrirbackend.DTO.User.UserDTO;
import com.mundoasorrir.mundoasorrirbackend.DTO.User.UserMapper;
import com.mundoasorrir.mundoasorrirbackend.Domain.User.SystemUser;
import com.mundoasorrir.mundoasorrirbackend.Services.AttendanceService;
import com.mundoasorrir.mundoasorrirbackend.Services.EventService;
import com.mundoasorrir.mundoasorrirbackend.Services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final UserService userService;
    @GetMapping(value = "/getUsersBusyToday")
    public ResponseEntity<List<UserDTO>> getBusyUsers() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        String todayDateString = dateFormat.format(today);
        Date obsDate = dateFormat.parse(todayDateString);
        List<UserDTO> res = UserMapper.toDTO(eventService.usersBusy(obsDate));
        return ResponseEntity.ok().body(res);
    }

    @PostMapping("/getUsersUnmarked")
    public ResponseEntity<?> getUsersForDayAttendance(@RequestParam("obsDate")String date) {
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
    public ResponseEntity<?> getAttendanceSheet(@RequestParam("obsDate")String date) {
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
        return ResponseEntity.ok().body(PresenceMapper.toDTO(this.attendanceService.getPresencesInDay(obsDate)));
    }
    @PostMapping("/markPresent")
    public ResponseEntity<?> markPresent(@RequestParam("username") String username, @RequestParam("obsDate")String date){
        Date obsDate ;
        try{
            obsDate = handleDate(date) ;

        }catch(Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Not a valid date!"));
        }
        if(!this.attendanceService.isInstantiated(obsDate)){
            return ResponseEntity.badRequest().body(new MessageResponse("There has been an error: attendance not instantiated"));
        }
        SystemUser user = this.userService.findUserByUsername(username);
        if(this.attendanceService.markAsPresent(obsDate,user) != null){
            return ResponseEntity.ok().body(new MessageResponse("Presence marked successfully"));

        }else{
            return ResponseEntity.badRequest().body(new MessageResponse("There has been an error"));
        }

    }

    @PostMapping("/markAbsent")
    public ResponseEntity<?> markAbsent(@RequestParam("username") String username, @RequestParam("obsDate")String date){
        Date obsDate ;
        try{
            obsDate = handleDate(date) ;

        }catch(Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Not a valid date!"));
        }
        if(!this.attendanceService.isInstantiated(obsDate)){
            return ResponseEntity.badRequest().body(new MessageResponse("There has been an error: attendance not instantiated"));
        }
        SystemUser user = this.userService.findUserByUsername(username);
        if(this.attendanceService.markAsAbsent(obsDate,user) != null){
            return ResponseEntity.ok().body(new MessageResponse("Presence marked successfully"));

        }else{
            return ResponseEntity.badRequest().body(new MessageResponse("There has been an error"));
        }
    }

    private Date handleDate(String date) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //String todayDateString = dateFormat.format(date);
        return dateFormat.parse(date);
    }
}
