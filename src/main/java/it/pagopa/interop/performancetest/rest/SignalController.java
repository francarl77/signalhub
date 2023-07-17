package it.pagopa.interop.performancetest.rest;

import it.pagopa.interop.performancetest.service.impl.SignalServiceImpl;
import it.pagopa.interop.performancetest.dto.SignalDTO;
import it.pagopa.interop.performancetest.mapper.SignalMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping( value = "/interop-signal-hub")
public class SignalController {

    //POST: interop-signal-hub/push-signal + BODY
    //GET : interop-signal-hub/pull-signal?eserviceid=abc&indexSignal=1234
    @Autowired
    private SignalServiceImpl signalServiceImpl;

    @PostMapping(value = "/push-signal")
    public Mono<ResponseEntity<SignalDTO>> pushSignal(
            @RequestBody() SignalDTO signalDto
    ) {
        return this.signalServiceImpl.pushSignal(SignalMapper.toSignalFromDto(signalDto)).map(ResponseEntity::ok);
    }

    @GetMapping(value = "/pull-signal")
    public Mono<ResponseEntity<Flux<SignalDTO>>>pullSignal(
            @RequestParam(value="eServiceId", required= true) String eserviceId,
            @RequestParam(value="indexSignal", required= true) Long indexSignal
            ) {

        return Mono.just(this.signalServiceImpl.pullSignal(indexSignal, eserviceId,null,null)).map(ResponseEntity::ok);
    }


}
