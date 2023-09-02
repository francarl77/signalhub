package it.pagopa.interop.performancetest.rest;

import it.pagopa.interop.performancetest.entity.SignalEntity;
import it.pagopa.interop.performancetest.service.SignalService;
import it.pagopa.interop.performancetest.dto.SignalDTO;
import it.pagopa.interop.performancetest.configs.aws.async.AwsConfigs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;


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

    @PostMapping(value = "/push-signal")
    public Mono<ResponseEntity<SignalDTO>> pushSignal(
            @RequestBody() SignalDTO signalDto
    ) {
        log.debug("Name Of POD : {}", awsConfigs.getPodNameMs());
        return this.signalServiceImpl.pushSignal(signalDto).map(ResponseEntity::ok);
    }

    @PostMapping(value = "/push-signal-async")
    public Mono<ResponseEntity<SignalEntity>> pushSignalAsync(
            @RequestBody() SignalDTO signalDto
    ) {
        log.debug("Name Of POD : {}", awsConfigs.getPodNameMs());
        return this.signalServiceImpl.pushSignalAsync(signalDto).map(ResponseEntity::ok);
    }

    @GetMapping(value = "/pull-signal")
    public Mono<ResponseEntity<Flux<SignalDTO>>> pullSignal(
            @RequestParam(value="eserviceId", required= true) String eserviceId,
            @RequestParam(value= "lastSignalId", required= true) Long lastSignalId
            ) {

        return Mono.just(this.signalServiceImpl.pullSignal(lastSignalId, eserviceId,null,null).map(item -> item)).map(ResponseEntity::ok);
    }

    @GetMapping(value = "/signal-pull")
    public Mono<ResponseEntity<Flux<SignalDTO>>>pullEqualToSignal(
            @RequestParam(value="eserviceId", required= true) String eserviceId,
            @RequestParam(value="indexSignal", required= true) Long indexSignal
    ) {

        return Mono.just(this.signalServiceImpl.pullSignal(indexSignal, eserviceId,null,null).map(item -> item)).map(ResponseEntity::ok);
    }


}
