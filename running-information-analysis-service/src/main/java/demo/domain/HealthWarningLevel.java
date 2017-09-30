package demo.domain;

public enum HealthWarningLevel {
    LOW, NORMAL, HIGH, ABNORMAL;

    public static HealthWarningLevel getHealthWarningLevel(int heartRate) {
        if (heartRate <= 75 && heartRate >= 60) {
            return HealthWarningLevel.LOW;
        }
        if (heartRate <= 120 && heartRate > 75) {
            return HealthWarningLevel.NORMAL;
        }
        if (heartRate > 120) {
            return HealthWarningLevel.HIGH;
        }
        return HealthWarningLevel.ABNORMAL;
    }


}
