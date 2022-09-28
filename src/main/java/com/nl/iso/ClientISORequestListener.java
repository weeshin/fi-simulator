package com.nl.iso;

import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISORequestListener;
import org.jpos.iso.ISOSource;

import java.io.IOException;

public class ClientISORequestListener implements ISORequestListener, Configurable {
    private String clientName;

    @Override
    public void setConfiguration(Configuration configuration) throws ConfigurationException {
        clientName = configuration.get("client-name", "client");
    }

    @Override
    public boolean process(ISOSource isoSource, ISOMsg isoMsg) {
        try {
            isoMsg.setResponseMTI();
            isoMsg.set(39, "00");
            isoMsg.set(60, clientName);
            isoSource.send(isoMsg);
        } catch (ISOException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
