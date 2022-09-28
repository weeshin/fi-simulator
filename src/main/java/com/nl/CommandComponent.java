package com.nl;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.MUX;
import org.jpos.q2.Q2;
import org.jpos.q2.iso.QMUX;
import org.jpos.util.NameRegistrar;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class CommandComponent {
    private Q2 q2;

    @ShellMethod("start")
    public void start(String deploy) {
        if (q2 == null) {
            q2 = new Q2(deploy);
            q2.start();
        }
    }

    @ShellMethod("stop")
    public void stop() {
        if (q2 != null) {
            q2.shutdown();
        }
    }

    @ShellMethod("purchase")
    public void purchase(String code) {
        try {
            MUX mux = QMUX.getMUX("my-mux");
            ISOMsg reqMsg = new ISOMsg();
            reqMsg.setMTI("0200");
            reqMsg.set(4, "000000100000");
            reqMsg.set(11, "000001");
            reqMsg.set(41, "00280002");
            reqMsg.set(120, code);
            ISOMsg respMsg = mux.request(reqMsg, 30000);
            System.out.println(respMsg);
        } catch (NameRegistrar.NotFoundException | ISOException e) {
            e.printStackTrace();
        }
    }
}
