package it.pagopa.interop.performancetest.service.impl;


import it.pagopa.interop.performancetest.dto.SignalDTO;
import it.pagopa.interop.performancetest.entity.SignalEntity;
import it.pagopa.interop.performancetest.mapper.SignalMapper;
import it.pagopa.interop.performancetest.middleware.sqs.producer.QueueProducer;
import it.pagopa.interop.performancetest.repository.SignalRepository;
import it.pagopa.interop.performancetest.service.SignalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class SignalRelationalService implements SignalService {

    @Autowired
    private QueueProducer queueProducer;
    @Autowired
    private SignalRepository signalRepository;

    @Override
    public SignalDTO pushSignal(SignalDTO signal) {
        this.queueProducer.send(SignalMapper.toSignalEntity(signal));
        return SignalMapper.toDTO(SignalMapper.toSignalEntity(signal));
    }

    @Override
    public SignalDTO saveSignal(SignalDTO signal) {
        SignalEntity entity = this.signalRepository.saveAndFlush(SignalMapper.toSignalEntity(signal));
        return SignalMapper.toDTO(entity);
    }

    @Override
    public List<SignalDTO> pullSignal(Long indexSignal, String eserviceId, String signalType, String objectType) {
        return null;
    }
}
