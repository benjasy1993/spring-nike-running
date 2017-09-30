package demo.service.impl;

import demo.domain.RunningInformation;
import demo.domain.RunningInformationRepository;
import demo.service.RunningInformationAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RunningInformationAnalysisServiceImpl implements RunningInformationAnalysisService {

    @Autowired
    private RunningInformationRepository repository;

    @Override
    public List<RunningInformation> saveRunningInfos(List<RunningInformation> runningInformations) {
        return repository.save(runningInformations);
    }

    @Override
    public void deleteByRunningId(String runningId) {
        repository.deleteByRunningId(runningId);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public RunningInformation getByRunningId(String runningId) {
        return repository.findByRunningId(runningId);
    }

    @Override
    public Page<RunningInformation> getAllRunningInfo(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<RunningInformation> getRunningInfoByUsername(String username, Pageable pageable) {
        return repository.findByUserInfoUsername(username, pageable);
    }
}
