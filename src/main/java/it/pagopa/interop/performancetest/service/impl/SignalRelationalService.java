package it.pagopa.interop.performancetest.service.impl;


import it.pagopa.interop.performancetest.dto.SignalDTO;
import it.pagopa.interop.performancetest.mapper.SignalMapper;
import it.pagopa.interop.performancetest.repository.SignalRepository;
import it.pagopa.interop.performancetest.service.SignalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
@Qualifier("relational")
public class SignalRelationalService implements SignalService {

    @Autowired
    private SignalRepository signalRepository;

    @Override
    @Transactional
    public Mono<SignalDTO> pushSignal(SignalDTO signal) {
        return this.signalRepository.save(SignalMapper.toSignalEntity(signal))
                .map(SignalMapper::toDTO);
    }

    @Override
    public Flux<SignalDTO> pullSignal(Long indexSignal, String eserviceId, String signalType, String objectType) {
        return null;
    }
}
