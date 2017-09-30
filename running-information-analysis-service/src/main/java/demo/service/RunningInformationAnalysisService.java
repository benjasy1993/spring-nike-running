package demo.service;

import demo.domain.RunningInformation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RunningInformationAnalysisService {

    List<RunningInformation> saveRunningInfos(List<RunningInformation> runningInformations);

    void deleteByRunningId(String runningId);

    void deleteAll();

    RunningInformation getByRunningId(String runningId);

    Page<RunningInformation> getAllRunningInfo(Pageable pageable);

    Page<RunningInformation> getRunningInfoByUsername(String username, Pageable pageable);
}
