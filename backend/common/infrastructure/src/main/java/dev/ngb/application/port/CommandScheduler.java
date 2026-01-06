package dev.ngb.application.port;

import dev.ngb.application.command.Command;
import dev.ngb.application.command.JobId;

import java.time.Duration;

public interface CommandScheduler {
    JobId schedule(Command<Void> command);

    JobId schedule(Command<Void> command, Duration delay);

    void cancel(JobId jobId);
}
