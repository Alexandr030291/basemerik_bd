package com.monitoring.Controller;

import com.monitoring.Service.InfoBd;
import com.monitoring.model.Info;
import com.monitoring.model.LenMetric;
import com.monitoring.model.NameInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class EchoPort {
    private InfoBd infoBd;
    private int len_metric;

    public EchoPort(InfoBd infoBd) {
        this.infoBd = infoBd;
        this.len_metric = 600;
    }

    @RequestMapping(path = "db/api/info", method = RequestMethod.POST)
    public ResponseEntity postInfo(HttpServletRequest httpServletRequest, @RequestBody Info body) {
        String clientIp = httpServletRequest.getRemoteAddr();
        String ipArray[] = clientIp.split("\\D", 5);
        int ip = 0;
        int _byte = 256;
        for (String anIpArray : ipArray) {
            ip *= _byte;
            ip += new Integer(anIpArray);
        }
        if (infoBd.checkIp(ip) > 0) {
            infoBd.create(ip, body);
        }
        return ResponseEntity.ok().body(body.toString());
    }

    @RequestMapping(path = "db/api/info", method = RequestMethod.GET)
    public ResponseEntity getInfo(@RequestParam("len") int len){
        return ResponseEntity.ok().body(infoBd.getList(len));
    }

    @RequestMapping(path = "db/api/name", method = RequestMethod.POST)
    public ResponseEntity postName(HttpServletRequest httpServletRequest, @RequestBody NameInfo body) {
        String clientIp = httpServletRequest.getRemoteAddr();
        String ipArray[] = clientIp.split("\\D", 5);
        int ip = 0;
        int _byte = 256;
        for (String anIpArray : ipArray) {
            ip *= _byte;
            ip += new Integer(anIpArray);
        }
        body.setIp(ip);
        infoBd.addLook(body);
        return ResponseEntity.ok().body(body.toString());
    }

    @RequestMapping(path = "db/api/name", method = RequestMethod.GET)
    public ResponseEntity getName() {
        return ResponseEntity.ok().body(infoBd.getIp());
    }

    @RequestMapping(path = "db/api/clear", method = RequestMethod.POST)
    public ResponseEntity clear() {
        infoBd.allClear();
        return ResponseEntity.ok().body("[]");
    }

    @RequestMapping(path = "db/api/len", method = RequestMethod.POST)
    public ResponseEntity setLen(@RequestBody LenMetric body) {
        this.len_metric=body.getLen();
        return ResponseEntity.ok().body("[]");
    }

    @RequestMapping(path = "db/api/len", method = RequestMethod.GET)
    public ResponseEntity getLen() {
        return ResponseEntity.ok().body(this.len_metric);
    }

}
