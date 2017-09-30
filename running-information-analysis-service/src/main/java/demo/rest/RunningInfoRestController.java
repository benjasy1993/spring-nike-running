package demo.rest;

import demo.domain.RunningInformation;
import demo.service.RunningInformationAnalysisService;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
public class RunningInfoRestController {

    private static final String DEFAULT_PAGE_NUMBER = "0";
    private static final String DEFAULT_PAGE_SIZE = "2";
    private static final String DEFAULT_ORDER = "desc";
    private static final String DEFAULT_ORDER_BY = "heartRate";

    @Autowired
    private RunningInformationAnalysisService service;

    @RequestMapping(value = "/runningInfo", method = RequestMethod.POST)
    public List<RunningInformation> upload(@RequestBody List<RunningInformation> runningInformations) {
        return service.saveRunningInfos(runningInformations);
    }

    @RequestMapping(value = "/runningInfo/{runningId}", method = RequestMethod.DELETE)
    public void deleteByRunningId(@PathVariable("runningId") String runningId) {
        service.deleteByRunningId(runningId);
    }

    @RequestMapping(value = "/runningInfo/{runningId}", method = RequestMethod.GET)
    public RunningInformation getByRunningId(@PathVariable("runningId") String runningId) {
        return service.getByRunningId(runningId);
    }

    @RequestMapping(value = "/runningInfo/purge", method = RequestMethod.DELETE)
    public void purge() {
        service.deleteAll();
    }

    @RequestMapping(value = "/runningInfo", method = RequestMethod.GET)
    public Page<RunningInformation> getRunningInfos(@RequestParam(value = "page", required = false, defaultValue = DEFAULT_PAGE_NUMBER) int page,
                                                    @RequestParam(value = "size", required = false, defaultValue = DEFAULT_PAGE_SIZE) int size,
                                                    @RequestParam(value = "orderBy", required = false, defaultValue = DEFAULT_ORDER_BY) String orderBy,
                                                    @RequestParam(value = "order", required = false, defaultValue = DEFAULT_ORDER) String order) {
        return service.getAllRunningInfo(new PageRequest(page, size, Sort.Direction.fromString(order), orderBy));
    }

    @RequestMapping(value = "/{username}/runningInfo", method = RequestMethod.GET)
    public Page<RunningInformation> getRunningInfoByUsername(@PathVariable("username") String username,
                                                             @RequestParam(value = "page", required = false, defaultValue = DEFAULT_PAGE_NUMBER) int page,
                                                             @RequestParam(value = "size", required = false, defaultValue = DEFAULT_PAGE_SIZE) int size) {
        return service.getRunningInfoByUsername(username, new PageRequest(page, size));
    }

}
