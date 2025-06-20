package org.Scsp.com.service;

import org.Scsp.com.dto.TaskCompletionStatsDTO;
import org.Scsp.com.dto.TaskDTO;

import java.util.List;

public interface TaskService {


    List<TaskDTO> getTasksForUser(Long userId);


    double getCompletionPercentage(Long userId);

    void completeTask(Long userId, Long templateId);

    TaskCompletionStatsDTO getCompletionStats(Long userId);
}
