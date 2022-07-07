package com.shape.test_shape_backpl.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.shape.test_shape_backpl.bean.EquipmentBean;
import com.shape.test_shape_backpl.bean.OperationBean;
import com.shape.test_shape_backpl.dto.EquipmentCostDTO;
import com.shape.test_shape_backpl.dto.VesselEquipmentsDTO;
import com.shape.test_shape_backpl.exception.InvalidJSONParameterException;
import com.shape.test_shape_backpl.model.Equipment;
import com.shape.test_shape_backpl.repository.EquipmentRepository;
import com.shape.test_shape_backpl.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/equipment")
public class EquipmentController {
    @Autowired
    private EquipmentService service;

    @GetMapping("/")
    public ResponseEntity<List<EquipmentBean>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping("/insert_equipment")
    public ResponseEntity inserEquipment(@RequestBody ObjectNode json){
        return ResponseEntity.status(HttpStatus.OK).body(this.service.insertEquipment(json));
    }

    @RequestMapping(value = "/update_equipment_status", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateEquipmentStatus(@RequestHeader("equipmentCodes") List<String> equipmentCodes){
        this.service.updateEquipmentStatus(equipmentCodes);
    }

    @GetMapping("/active_equipments/{vesselCode}")
    public VesselEquipmentsDTO activeEquipments(@PathVariable(value = "vesselCode") String vesselCode){

        return this.service.getActiveByVesselCode(vesselCode);
    }

    @PostMapping("/register_operation")
    public OperationBean registerOperation(@RequestBody OperationBean operationBean){
        return this.service.newOperation(operationBean);
    }

    @GetMapping("/cost_by_code/{equipmentCode}")
    public EquipmentCostDTO totalCostByCode(@PathVariable(value = "equipmentCode") String equipmentCode){
        return this.service.getTotalCostByCode(equipmentCode);
    }

    @GetMapping("/cost_by_name/{equipmentName}")
    public EquipmentCostDTO totalCostByName(@PathVariable(value = "equipmentName") String equipmentName){
        return this.service.getTotalCostByName(equipmentName);
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
