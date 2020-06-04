package me.kpali.wolfflow.core.model;

import me.kpali.wolfflow.core.exception.TaskExecuteException;
import me.kpali.wolfflow.core.exception.TaskInterruptedException;
import me.kpali.wolfflow.core.exception.TaskRollbackException;
import me.kpali.wolfflow.core.exception.TaskStopException;
import me.kpali.wolfflow.core.logger.ITaskLogger;
import me.kpali.wolfflow.core.util.SpringContextUtil;

import java.util.Map;

public class AutoTask extends Task {
    private boolean requiredToStop = false;

    @Override
    public void execute(Map<String, Object> context) throws TaskExecuteException, TaskInterruptedException {
        if (context == null) {
            throw new IllegalArgumentException();
        }
        try {
            ITaskLogger taskLogger = SpringContextUtil.getBean(ITaskLogger.class);
            TaskFlowContextWrapper taskFlowContextWrapper = new TaskFlowContextWrapper(context);
            Map<String, Object> taskContext = taskFlowContextWrapper.getTaskContext(this.getId().toString());
            TaskContextWrapper taskContextWrapper = new TaskContextWrapper(taskContext);
            Long taskLogId = taskContextWrapper.getValue(ContextKey.TASK_LOG_ID, Long.class);
            String taskLogFileId = taskContextWrapper.getValue(ContextKey.TASK_LOG_FILE_ID, String.class);
            taskLogger.log(taskLogFileId, "任务开始执行", false);
            taskLogger.log(taskLogFileId, "日志第二行\r日志第三行\n日志第四行\r\n日志第五行", false);
            int totalTime = 0;
            int timeout = 500;
            while (totalTime < timeout) {
                try {
                    if (requiredToStop) {
                        taskLogger.log(taskLogFileId, "任务被终止执行", true);
                        throw new TaskInterruptedException("任务被终止执行");
                    }
                    Thread.sleep(100);
                    totalTime += 100;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            taskLogger.log(taskLogFileId, "任务执行完成", true);
        } catch (TaskInterruptedException e) {
            throw e;
        } catch (Exception e) {
            throw new TaskExecuteException(e);
        }
    }

    @Override
    public void rollback(Map<String, Object> context) throws TaskRollbackException, TaskInterruptedException {
        try {
            ITaskLogger taskLogger = SpringContextUtil.getBean(ITaskLogger.class);
            TaskFlowContextWrapper taskFlowContextWrapper = new TaskFlowContextWrapper(context);
            Map<String, Object> taskContext = taskFlowContextWrapper.getTaskContext(this.getId().toString());
            TaskContextWrapper taskContextWrapper = new TaskContextWrapper(taskContext);
            Long taskLogId = taskContextWrapper.getValue(ContextKey.TASK_LOG_ID, Long.class);
            String taskLogFileId = taskContextWrapper.getValue(ContextKey.TASK_LOG_FILE_ID, String.class);
            taskLogger.log(taskLogFileId, "任务开始回滚", false);
            taskLogger.log(taskLogFileId, "日志第二行\r日志第三行\n日志第四行\r\n日志第五行", false);
            int totalTime = 0;
            int timeout = 500;
            while (totalTime < timeout) {
                try {
                    if (requiredToStop) {
                        taskLogger.log(taskLogFileId, "任务被终止执行", true);
                        throw new TaskInterruptedException("任务被终止执行");
                    }
                    Thread.sleep(100);
                    totalTime += 100;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            taskLogger.log(taskLogFileId, "任务回滚完成", true);
        } catch (TaskInterruptedException e) {
            throw e;
        } catch (Exception e) {
            throw new TaskRollbackException(e);
        }
    }

    @Override
    public void stop(Map<String, Object> context) throws TaskStopException {
        this.requiredToStop = true;
    }
}