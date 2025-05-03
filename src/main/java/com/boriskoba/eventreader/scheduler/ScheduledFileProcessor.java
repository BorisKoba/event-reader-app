package com.boriskoba.eventreader.scheduler;

import java.io.IOException;
import java.nio.file.*;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.boriskoba.eventreader.service.EventService;

@Component
@Slf4j
public class ScheduledFileProcessor {

	private final EventService eventService;
	private Path inputFolder;

	public ScheduledFileProcessor(EventService eventService, @Value("${app.input-folder}") Path inputFolder) {
		this.eventService = eventService;
		this.inputFolder = inputFolder;
	}

	@Scheduled(fixedDelayString = "${reader.schedule.delay}")
	public void scheduleFileProcessing() {
		ensureInputFolderExists();
		try (Stream<Path> files = Files.list(inputFolder)) {
			files.filter(Files::isRegularFile).forEach(path -> eventService.processFile(path));
		} catch (IOException e) {
			log.error("Error reading input folder", e);
		}
	}

	private void ensureInputFolderExists() {
		if (!Files.exists(inputFolder)) {
			try {
				Files.createDirectories(inputFolder);
				log.info("Input folder created at: {}", inputFolder.toAbsolutePath());
			} catch (IOException e) {
				log.error("Failed to create input folder: {}", inputFolder, e);
			}
		}
	}
}
