package com.lite.blackdream.business.controller;

import com.lite.blackdream.business.parameter.system.SessionHeartbeatRequest;
import com.lite.blackdream.business.parameter.system.SessionHeartbeatResponse;
import com.lite.blackdream.framework.layer.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author LaineyC
 */
@Controller
public class SystemController extends BaseController {

    @ResponseBody
    @RequestMapping(params="method=session.heartbeat")
    public SessionHeartbeatResponse heartbeat(SessionHeartbeatRequest request) {
        return new SessionHeartbeatResponse();
    }


}
