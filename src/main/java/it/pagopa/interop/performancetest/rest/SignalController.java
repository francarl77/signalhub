package it.pagopa.interop.performancetest.rest;

import it.pagopa.interop.performancetest.service.SignalService;
import it.pagopa.interop.performancetest.dto.SignalDTO;
import it.pagopa.interop.performancetest.configs.aws.async.AwsConfigs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping( value = "/interop-signal-hub")
public class SignalController {

    @Autowired
    private AwsConfigs awsConfigs;

    //POST: interop-signal-hub/push-signal + BODY
    //GET : interop-signal-hub/pull-signal?eserviceid=abc&indexSignal=1234
    @Autowired
    private SignalService signalServiceImpl;

    @PostMapping(value = "/push-signal-sqs")
    public ResponseEntity<SignalDTO> pushSignalSqs(
            @RequestBody() SignalDTO signalDto
    ) {
        log.debug("Name Of POD : {}", awsConfigs.getPodNameMs());
        return ResponseEntity.ok(this.signalServiceImpl.pushSignal(signalDto));
    }

    @PostMapping(value = "/push-signal")
    public ResponseEntity<SignalDTO> pushSignal(
            @RequestBody() SignalDTO signalDto
    ) {
        log.debug("Name Of POD : {}", awsConfigs.getPodNameMs());
        return ResponseEntity.ok(this.signalServiceImpl.saveSignal(signalDto));
    }

    @GetMapping(value = "/pull-signal")
    public ResponseEntity<List<SignalDTO>> pullSignal(
            @RequestParam(value="eserviceId", required= true) String eserviceId,
            @RequestParam(value="indexSignal", required= true) Long indexSignal
            ) {

        return ResponseEntity.ok(this.signalServiceImpl.pullSignal(indexSignal, eserviceId,null,null));
    }



}
