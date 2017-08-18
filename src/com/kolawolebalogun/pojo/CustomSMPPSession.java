package com.kolawolebalogun.pojo;

import org.jsmpp.session.SMPPSession;

/**
 * Created by KolawoleBalogun on 8/4/17.
 */
public class CustomSMPPSession {
    private SMPPSession session;
    private SMPPBind smppBind;

    public SMPPSession getSession() {
        return session;
    }

    public void setSession(SMPPSession session) {
        this.session = session;
    }

    public SMPPBind getSmppBind() {
        return smppBind;
    }

    public void setSmppBind(SMPPBind smppBind) {
        this.smppBind = smppBind;
    }
}
