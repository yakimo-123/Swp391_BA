package org.Scsp.com.scheduler;

import lombok.RequiredArgsConstructor;
import org.Scsp.com.service.HealthMilestoneService;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MilestoneScheduler {

    private final HealthMilestoneService healthMilestoneService;

    @Scheduled(cron = "0 0 * * * *")
    public void scheduledAutoMarkAchieved() {
        healthMilestoneService.autoMarkAchievedMilestones();
    }
}
