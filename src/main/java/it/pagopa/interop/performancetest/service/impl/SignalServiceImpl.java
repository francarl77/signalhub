package it.pagopa.interop.performancetest.service.impl;


import it.pagopa.interop.performancetest.dto.SignalDTO;
import it.pagopa.interop.performancetest.mapper.SignalMapper;
import it.pagopa.interop.performancetest.middleware.db.dao.SignalDAO;
import it.pagopa.interop.performancetest.middleware.db.entities.Signal;
import it.pagopa.interop.performancetest.service.SignalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
@Service
@Qualifier("not-relational")
public class SignalServiceImpl implements SignalService {

    @Autowired
    private SignalDAO signalDAO;

    @Override
    public Mono<SignalDTO> pushSignal(SignalDTO signal){
        Signal signalEntity = SignalMapper.toSignalFromDto(signal);
        signalEntity.setIndexSignal(null);

        return this.signalDAO.pushSignal(signalEntity)
                .map(SignalMapper::signalDtoMapper)
                .delayElement(Duration.ofMillis(1000L));
    }

    @Override
    public Flux<SignalDTO> pullSignal(Long indexSignal, String eserviceId, String signalType, String objectType) {
        return this.signalDAO.pullSignal(indexSignal,eserviceId,signalType,objectType)
                .map(SignalMapper::signalDtoMapper)
                .delayElements(Duration.ofMillis(100L));
    }


}
