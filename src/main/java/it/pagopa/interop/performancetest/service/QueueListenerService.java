package it.pagopa.interop.performancetest.service;

import it.pagopa.interop.performancetest.entity.SignalEntity;

public interface QueueListenerService {

    void signalListener(SignalEntity data);

}
