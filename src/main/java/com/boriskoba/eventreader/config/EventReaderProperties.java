package com.boriskoba.eventreader.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "eventreader")
public class EventReaderProperties {
    private long scanIntervalMs = 600000; 
    private String inputFolderPath = "input-folder-path";
    private String backupFolderPath = "backup-folder-path";
}
