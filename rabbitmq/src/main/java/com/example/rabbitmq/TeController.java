package com.example.rabbitmq;

import com.example.rabbitmq.Producer.RabbitMQJsonProducer;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class TeController {
    private final RabbitMQJsonProducer jsonProducer;

    public TeController(RabbitMQJsonProducer jsonProducer) {
        this.jsonProducer = jsonProducer;
    }

    @GetMapping(value = "/rabbit")
    public ResponseEntity<?> searchDocument(@RequestParam(value = "documentNumber", required = false) Long documentNumber,
                                            @RequestParam(value = "documentTypeId", required = false) Long documentTypeId,
                                            @RequestParam(value = "employeeId", required = false) Long employeeId,
                                            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                                            @RequestParam(value = "state", required = false) Long state,
                                            @RequestParam(value = "type") Long type,
                                            @RequestParam(value = "docNumber", required = false) String docNumber,
                                            @RequestParam(value = "personDocTypeId", required = false) Long personDocTypeId,
                                            @RequestParam(value = "lastName", required = false) String lastName,
                                            @RequestParam(value = "firstName", required = false) String firstName
    ) {
        ParamReport report = ParamReport.builder()
                .documentNumber(documentNumber)
                .documentTypeId(documentTypeId)
                .employeeId(employeeId)
                .fileName("reportSPSS.getName()")
                .state(state)
                .startDate(startDate)
                .endDate(endDate)
                .personIds(null)
                .fileReportId(1L)
                .type(type)
                .docNumber(docNumber)
                .personDocTypeId(personDocTypeId)
                .lastName(lastName)
                .firstName(firstName)
                .departmentIds(null)
                .build();
        jsonProducer.sendJsonMessage(report);

        return new ResponseEntity<>(report, HttpStatus.OK);
    }
}
