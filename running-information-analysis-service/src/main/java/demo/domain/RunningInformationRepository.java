package demo.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface RunningInformationRepository extends JpaRepository<RunningInformation, String>{

    @Transactional
    void deleteByRunningId(@Param("runningId") String runningId);

    Page<RunningInformation> findByUserInfoUsername(@Param("username") String username,
                                                    Pageable pageable);

    RunningInformation findByRunningId(@Param("runningId") String runningId);

}
