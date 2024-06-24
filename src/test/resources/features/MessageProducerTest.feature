Feature: Workload message sending

  Scenario: Send WorkloadDTO message to ActiveMQ
    Given gym-api is running
    When a WorkloadDTO is sent to ActiveMQ
    Then the message should be in the "workload-queue" queue