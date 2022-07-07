package com.shape.test_shape_backpl.controller;

import com.shape.test_shape_backpl.bean.VesselBean;
import com.shape.test_shape_backpl.dto.VesselAvgCostDTO;
import com.shape.test_shape_backpl.model.Vessel;
import com.shape.test_shape_backpl.repository.VesselRepository;
import com.shape.test_shape_backpl.service.VesselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vessel")
public class VesselController {

    @Autowired
    private VesselService service;

    @PostMapping("/insert_vessel")
    public ResponseEntity insertVessel(@RequestBody VesselBean vessel){
        return ResponseEntity.status(HttpStatus.OK).body(service.insertVessel(vessel));
    }

    @GetMapping("/")
    public ResponseEntity<List<VesselBean>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/avg_cost_by_vessel/")
    public List<VesselAvgCostDTO> avgCostByVessel(){
        return this.service.avgCostByVessel();
    }

    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<String> handleConstraintViolation(HttpServletRequest req, Exception ex) {

        String msg = null;
        if (ex.getCause().getCause() instanceof ConstraintViolationException) {
            ConstraintViolationException e = (ConstraintViolationException) ex.getCause().getCause();
            Optional<ConstraintViolation<?>> optional = e.getConstraintViolations().stream().findFirst();
            msg = optional.isPresent() ? optional.get().getMessageTemplate() : ex.getMessage();
        }

        return new ResponseEntity<>(msg, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({ DataIntegrityViolationException.class })
    public ResponseEntity<String> handleDataIntegrityViolation(HttpServletRequest req, Exception ex) {

        String msg = ex.getMessage();
        if (ex.getCause().getCause() instanceof SQLException) {
            SQLException e = (SQLException) ex.getCause().getCause();

            if (e.getMessage().contains("Key")) {
                msg = e.getMessage().substring(e.getMessage().indexOf("Key"));
            }
        }

        return new ResponseEntity<>(msg, HttpStatus.CONFLICT);
    }
}
