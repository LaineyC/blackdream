package com.lite.blackdream.business.controller;

import com.lite.blackdream.business.parameter.system.*;
import com.lite.blackdream.business.service.SystemService;
import com.lite.blackdream.framework.component.BaseController;
import com.lite.blackdream.framework.util.ConfigProperties;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.io.*;
import java.util.Date;
import java.util.Map;

/**
 * @author LaineyC
 */
@Controller
public class SystemController extends BaseController {

    @Autowired
    private SystemService systemService;

    @ResponseBody
    @RequestMapping(params="method=session.heartbeat")
    public SessionHeartbeatResponse sessionHeartbeat(SessionHeartbeatRequest request) {
        return new SessionHeartbeatResponse();
    }

    @RequestMapping(params="method=file.download")
    public ResponseEntity<byte[]> fileDownload(FileDownloadRequest request) throws IOException {
        String url = request.getUrl();
        File file = new File(ConfigProperties.TEMPORARY_PATH + ConfigProperties.fileSeparator + url);
        HttpHeaders headers = new HttpHeaders();
        String fileName = java.net.URLEncoder.encode(file.getName(),"UTF-8");
        headers.setContentDispositionFormData("attachment", fileName);
        headers.add("filename", fileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
    }

    @ResponseBody
    @RequestMapping(params="method=data.statistic")
    public DataStatisticResponse dataStatistic(DataStatisticRequest request) {
        Map<String, Object> result = systemService.dataStatistic(request);
        return new DataStatisticResponse(result);
    }

    @ResponseBody
    @RequestMapping(params="method=date.currentTime")
    public DateCurrentTimeResponse dateCurrentTime(DateCurrentTimeRequest request) {
        return new DateCurrentTimeResponse(new Date());
    }

}
