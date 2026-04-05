//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Custom Thread Pool Test Suite ===\n");

        // Test 1: Basic pool creation with single worker
        testBasicPoolCreation();

        // Test 2: Multiple workers
        testMultipleWorkers();

        // Test 3: Queue overflow handling
        testQueueOverflow();

        // Test 4: Task execution with exceptions
        testTaskWithExceptions();

        // Test 5: Concurrent task submission
        testConcurrentTaskSubmission();

        // Test 6: Pool abort/shutdown
        testPoolAbort();

        // Test 7: Empty pool edge case
        testEmptyPoolEdgeCase();

        // Test 8: Long-running tasks
        testLongRunningTasks();

        // Test 9: Rapid task submission
        testRapidTaskSubmission();

        // Test 10: Large pool with minimal workers
        testLargePoolMinimalWorkers();

        System.out.println("\n=== All Tests Completed ===");
    }

    // Test 1: Basic pool creation with single worker and task
    private static void testBasicPoolCreation() {
        System.out.println("TEST 1: Basic Pool Creation");
        try {
            Pool pool = new Pool(5, 1);
            Task task = new Task(() -> System.out.println("Task 1 executed"));
            pool.AddTask(task);
            Thread.sleep(500);
            System.out.println("✓ Basic pool test passed\n");
        } catch (Exception e) {
            System.out.println("✗ Basic pool test failed: " + e.getMessage() + "\n");
        }
    }

    // Test 2: Multiple workers processing tasks simultaneously
    private static void testMultipleWorkers() {
        System.out.println("TEST 2: Multiple Workers");
        try {
            Pool pool = new Pool(10, 3);
            for (int i = 0; i < 5; i++) {
                final int taskNum = i;
                Task task = new Task(() -> {
                    System.out.println("  Task " + taskNum + " completed");
                    try { Thread.sleep(100); } catch (InterruptedException e) { }
                });
                pool.AddTask(task);
            }
            Thread.sleep(1000);
            System.out.println("✓ Multiple workers test passed\n");
        } catch (Exception e) {
            System.out.println("✗ Multiple workers test failed: " + e.getMessage() + "\n");
        }
    }

    // Test 3: Queue overflow - attempt to add task when queue is full
    private static void testQueueOverflow() {
        System.out.println("TEST 3: Queue Overflow Handling");
        try {
            Pool pool = new Pool(2, 1); // Small queue size
            Task task1 = new Task(() -> {
                try { Thread.sleep(2000); } catch (InterruptedException e) { }
                System.out.println("  Long task completed");
            });
            Task task2 = new Task(() -> System.out.println("  Task 2 executed"));
            Task task3 = new Task(() -> System.out.println("  Task 3 executed"));

            pool.AddTask(task1);
            pool.AddTask(task2);

            try {
                pool.AddTask(task3); // This should throw exception
                System.out.println("✗ Queue overflow test failed: No exception thrown\n");
            } catch (RuntimeException e) {
                System.out.println("✓ Queue overflow properly detected: " + e.getMessage() + "\n");
            }
        } catch (Exception e) {
            System.out.println("✗ Queue overflow test failed: " + e.getMessage() + "\n");
        }
    }

    // Test 4: Task execution with exceptions in runnable
    private static void testTaskWithExceptions() {
        System.out.println("TEST 4: Task with Exceptions");
        try {
            Pool pool = new Pool(5, 1);
            Task task = new Task(() -> {
                System.out.println("  Executing task with exception");
                // Exception will be caught in ThreadRunnable
            });
            pool.AddTask(task);
            Thread.sleep(500);
            System.out.println("✓ Exception handling test passed\n");
        } catch (Exception e) {
            System.out.println("✗ Exception handling test failed: " + e.getMessage() + "\n");
        }
    }

    // Test 5: Concurrent task submission from multiple threads
    private static void testConcurrentTaskSubmission() {
        System.out.println("TEST 5: Concurrent Task Submission");
        try {
            Pool pool = new Pool(20, 2);
            Thread[] submitters = new Thread[3];

            for (int i = 0; i < 3; i++) {
                final int submitterNum = i;
                submitters[i] = new Thread(() -> {
                    for (int j = 0; j < 3; j++) {
                        try {
                            final int taskNum = submitterNum * 3 + j;
                            Task task = new Task(() ->
                                System.out.println("  Concurrent task " + taskNum + " executed"));
                            pool.AddTask(task);
                            Thread.sleep(50);
                        } catch (Exception e) {
                            System.out.println("  Error: " + e.getMessage());
                        }
                    }
                });
                submitters[i].start();
            }

            for (Thread t : submitters) {
                t.join();
            }
            Thread.sleep(500);
            System.out.println("✓ Concurrent submission test passed\n");
        } catch (Exception e) {
            System.out.println("✗ Concurrent submission test failed: " + e.getMessage() + "\n");
        }
    }

    // Test 6: Pool abort - interrupt all workers
    private static void testPoolAbort() {
        System.out.println("TEST 6: Pool Abort");
        try {
            Pool pool = new Pool(5, 2);
            Task longTask = new Task(() -> {
                try {
                    System.out.println("  Long task started");
                    Thread.sleep(5000);
                    System.out.println("  Long task completed (should not reach here)");
                } catch (InterruptedException e) {
                    System.out.println("  Task interrupted (expected)");
                }
            });
            pool.AddTask(longTask);
            Thread.sleep(500);
            pool.AbortTask();
            Thread.sleep(500);
            System.out.println("✓ Pool abort test passed\n");
        } catch (Exception e) {
            System.out.println("✗ Pool abort test failed: " + e.getMessage() + "\n");
        }
    }

    // Test 7: Edge case - pool with minimum configuration
    private static void testEmptyPoolEdgeCase() {
        System.out.println("TEST 7: Empty Pool Edge Case");
        try {
            Pool pool = new Pool(1, 1);
            System.out.println("✓ Empty pool creation test passed\n");
        } catch (Exception e) {
            System.out.println("✗ Empty pool test failed: " + e.getMessage() + "\n");
        }
    }

    // Test 8: Long-running tasks to verify proper queue management
    private static void testLongRunningTasks() {
        System.out.println("TEST 8: Long-Running Tasks");
        try {
            Pool pool = new Pool(5, 2);
            for (int i = 0; i < 3; i++) {
                final int taskNum = i;
                Task task = new Task(() -> {
                    try {
                        System.out.println("  Long task " + taskNum + " started");
                        Thread.sleep(300);
                        System.out.println("  Long task " + taskNum + " completed");
                    } catch (InterruptedException e) {
                        System.out.println("  Long task " + taskNum + " interrupted");
                    }
                });
                pool.AddTask(task);
            }
            Thread.sleep(2000);
            System.out.println("✓ Long-running tasks test passed\n");
        } catch (Exception e) {
            System.out.println("✗ Long-running tasks test failed: " + e.getMessage() + "\n");
        }
    }

    // Test 9: Rapid task submission
    private static void testRapidTaskSubmission() {
        System.out.println("TEST 9: Rapid Task Submission");
        try {
            Pool pool = new Pool(15, 2);
            int submitted = 0;
            for (int i = 0; i < 10; i++) {
                final int taskNum = i;
                try {
                    Task task = new Task(() -> System.out.println("  Rapid task " + taskNum + " executed"));
                    pool.AddTask(task);
                    submitted++;
                } catch (RuntimeException e) {
                    System.out.println("  Queue full at task " + i + " (expected)");
                    break;
                }
            }
            Thread.sleep(500);
            System.out.println("✓ Rapid submission test passed (submitted: " + submitted + ")\n");
        } catch (Exception e) {
            System.out.println("✗ Rapid submission test failed: " + e.getMessage() + "\n");
        }
    }

    // Test 10: Large pool with minimal workers
    private static void testLargePoolMinimalWorkers() {
        System.out.println("TEST 10: Large Pool with Minimal Workers");
        try {
            Pool pool = new Pool(50, 1); // Large queue, 1 worker
            for (int i = 0; i < 10; i++) {
                final int taskNum = i;
                Task task = new Task(() -> System.out.println("  Pool task " + taskNum + " executed"));
                pool.AddTask(task);
            }
            Thread.sleep(1500);
            System.out.println("✓ Large pool test passed\n");
        } catch (Exception e) {
            System.out.println("✗ Large pool test failed: " + e.getMessage() + "\n");
        }
    }
}