package org.example.authservice.vault;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import java.util.List;
@Getter
public class SecretResponseHcp  {
    @JsonProperty("secrets")
    private List<Secret> secrets;

    // Getters and Setters
    @Getter
    public static class Secret {
        private String name;
        private Version version;
        @JsonProperty("created_at")
        private String createdAt;
        @JsonProperty("latest_version")
        private String latestVersion;
        @JsonProperty("created_by")
        private CreatedBy createdBy;
        @JsonProperty("sync_status")
        private Object syncStatus;
        @JsonProperty("created_by_id")
        private String createdById;

        @Getter
        public static class Version {
            private String version;
            private String type;
            @JsonProperty("created_at")
            private String createdAt;
            private String value;
            @JsonProperty("created_by")
            private CreatedBy createdBy;
            @JsonProperty("created_by_id")
            private String createdById;

        }
        @Getter
        public static class CreatedBy {
            private String name;
            private String type;
            private String email;

        }
    }
}
