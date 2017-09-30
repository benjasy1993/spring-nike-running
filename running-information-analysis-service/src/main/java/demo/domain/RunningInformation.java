package demo.domain;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.omg.CORBA.PUBLIC_MEMBER;

import javax.persistence.*;
import java.util.Date;
import java.util.Random;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Entity
@Table(name = "RUNNING_ANALYSIS")
public class RunningInformation {

    private String runningId;

    private double latitude;

    private double longitude;

    private double runningDistance;

    private double totalRunningTime;

    private int heartRate;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date timestamp = new Date();

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "username", column = @Column(name = "userName")),
            @AttributeOverride(name = "address", column = @Column(name = "userAddress"))
    })
    private UserInfo userInfo;

    private HealthWarningLevel healthWarningLevel;

    public RunningInformation() {}

    @JsonCreator
    public RunningInformation(@JsonProperty("runningId") String runningId,
                              @JsonProperty("latitude") String latitude,
                              @JsonProperty("longitude") String longitude,
                              @JsonProperty("runningDistance") String runningDistance,
                              @JsonProperty("totalRunningTime") String totalRunningTime,
                              @JsonProperty("heartRate") String heartRate,
                              @JsonProperty("timestamp") String timestamp,
                              @JsonProperty("userInfo") UserInfo userInfo) {
        this.runningId = runningId;
        this.runningDistance = Double.parseDouble(runningDistance);
        this.latitude = Double.parseDouble(latitude);
        this.longitude = Double.parseDouble(longitude);
        this.totalRunningTime = Double.parseDouble(totalRunningTime);
        this.heartRate = new Random().nextInt(140) + 60;
        this.userInfo = userInfo;
        this.healthWarningLevel = HealthWarningLevel.getHealthWarningLevel(this.heartRate);
    }

    public String getUserName() {
        return this.userInfo == null ? null : userInfo.getUsername();
    }

    public String getUserAddress() {
        return this.userInfo == null ? null : userInfo.getAddress();
    }

    @JsonIgnore
    public double getLatitude() {
        return latitude;
    }

    @JsonIgnore
    public double getLongitude() {
        return longitude;
    }

    @JsonIgnore
    public double getRunningDistance() {
        return runningDistance;
    }

    @JsonIgnore
    public Date getTimestamp() {
        return timestamp;
    }

    @JsonIgnore
    public UserInfo getUserInfo() {
        return userInfo;
    }

    @JsonGetter("userId")
    public Long getId() {
        return id;
    }
}
