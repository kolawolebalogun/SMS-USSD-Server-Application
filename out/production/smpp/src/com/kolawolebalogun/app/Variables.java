package com.kolawolebalogun.app;

import com.kolawolebalogun.pojo.CustomSMPPSession;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by KolawoleBalogun on 7/11/17.
 */
public class Variables {
    public static Map<String, ConcurrentLinkedQueue<CustomSMPPSession>> smppSessions = new HashMap<>();
}
