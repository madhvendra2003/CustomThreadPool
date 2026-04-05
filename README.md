# Custom Thread Pool - Practice Project

> ⚠️ **This is a learning/practice project** - For educational purposes to understand concurrent programming concepts. Not recommended for production use without additional testing and hardening.

---

## 📚 Project Overview

This is a **custom implementation of a thread pool** built from scratch to understand how thread pools work under the hood. It demonstrates core concepts of:

- **Concurrent Programming** in Java
- **Thread Synchronization** and Thread Safety
- **Blocking Queues** and Task Scheduling
- **Worker Thread Patterns**
- **Task Execution** through worker threads

---

## 🎯 Purpose

This project was created to:

1. **Learn how thread pools work internally** - Not just using `ExecutorService`, but building one
2. **Practice concurrent programming** - Understanding locks, synchronization, and thread safety
3. **Write comprehensive tests** - Edge cases and stress testing scenarios
4. **Understand design patterns** - Worker pattern, producer-consumer pattern

---

## 📁 Project Structure

```
CustomThreadPool/
├── src/
│   ├── Main.java              # Comprehensive test suite (10 edge case tests)
│   ├── Pool.java              # Main thread pool class
│   ├── Worker.java            # Individual worker thread wrapper
│   ├── ThreadRunnable.java    # Runnable implementation for workers
│   ├── Task.java              # Task wrapper class
│   └── MyBlockingQueue.java   # Custom blocking queue wrapper
├── CODE_REVIEW.md             # Senior engineer code review (recommendations only)
└── README.md                  # This file
```

---

## 🚀 Quick Start

### Compile
```bash
javac src/*.java
```

### Run
```bash
java -cp src Main
```

### Expected Output
All 10 test cases should pass:
```
=== Custom Thread Pool Test Suite ===

TEST 1: Basic Pool Creation
--> Thread-0 is a worker thread doing a job
this is the tread taking this task Thread-0
Task 1 executed
✓ Basic pool test passed

TEST 2: Multiple Workers
✓ Multiple workers test passed

... (8 more tests)

=== All Tests Completed ===
```

---

## 💡 Key Features

### ✅ Current Implementation Includes

- **Thread Pool Management** - Create and manage a fixed number of worker threads
- **Blocking Queue** - Thread-safe task queue with capacity limits
- **Task Execution** - Execute Runnable tasks through worker threads
- **Pool Abort** - Interrupt and stop all workers
- **Queue Overflow Detection** - Throws exception when queue is full
- **Comprehensive Testing** - 10 edge case tests included

### ❌ Current Implementation Does NOT Have

- **Dynamic Scaling** - Cannot add/remove workers at runtime
- **Graceful Shutdown** - Workers use infinite loop (no clean shutdown path)
- **Task Priorities** - All tasks treated equally
- **Futures/Callbacks** - No return value support for tasks
- **Timeout Support** - No timeout on task submission or execution
- **Advanced Metrics** - Limited monitoring/observability
- **Input Validation** - Minimal null checks
- **Clear Naming** - Some abbreviated variable names (bq, t1, r1, temp)

---

## 📝 Usage Example

```java
// Create a pool with queue capacity 10 and 3 worker threads
Pool pool = new Pool(10, 3);

// Create and submit tasks
Task task = new Task(() -> {
    System.out.println("Task executed!");
    // Do some work
});

pool.addTask(task);

// Stop all workers (note: not graceful shutdown)
pool.AbortTask();
```

---

## 🧪 Test Coverage

The project includes **10 comprehensive test cases** covering:

| # | Test Name | Purpose |
|---|-----------|---------|
| 1 | Basic Pool Creation | Single worker, single task execution |
| 2 | Multiple Workers | Concurrent task execution with multiple threads |
| 3 | Queue Overflow Handling | Exception when queue reaches capacity |
| 4 | Task Exception Handling | Handling exceptions within tasks |
| 5 | Concurrent Task Submission | Thread-safe submission from multiple threads |
| 6 | Pool Abort | Interrupt all workers |
| 7 | Empty Pool Edge Case | Minimal configuration (1 queue, 1 worker) |
| 8 | Long-Running Tasks | Extended task execution |
| 9 | Rapid Task Submission | High-frequency task submission |
| 10 | Large Pool with Minimal Workers | Large queue with single worker |

---

## 📋 Code Review Document

A comprehensive senior engineer code review is available in `CODE_REVIEW.md`. 

**⚠️ Important Note:** The CODE_REVIEW.md file contains recommendations and identified issues, but the current codebase has **NOT been updated** with these suggestions. This is intentional for learning purposes.

### Issues Identified in CODE_REVIEW.md (Not Yet Fixed)
- Static BlockingQueue creating shared state across instances
- Infinite loop in ThreadRunnable with no graceful shutdown
- InterruptedException wrapped in RuntimeException
- Potential race conditions in AddTask()
- Missing null validation
- Abbreviated variable names (bq, t1, r1, temp)
- No input validation for parameters

### Purpose of Keeping Original Code
- Study the code review to understand **what issues to look for**
- Attempt to refactor the code yourself as a **learning exercise**
- Compare your fixes with the suggested improvements in CODE_REVIEW.md
- Understand the **before and after** of professional code review

---

## 🎓 Learning Objectives

By studying this code, you'll learn:

### Core Concepts
1. How thread pools are implemented internally
2. ArrayBlockingQueue usage and benefits
3. Thread synchronization with `synchronized` keyword
4. InterruptedException handling patterns
5. Worker thread patterns

### Best Practices (from CODE_REVIEW.md)
1. Input validation for concurrent code
2. Clear naming conventions
3. Proper exception handling in multi-threaded code
4. Resource cleanup and lifecycle management
5. Thread-safe state management

### Common Pitfalls (Demonstrated in This Code)
1. Static shared state across instances (memory leak)
2. Infinite loops in worker threads (thread leak)
3. Check-then-act race conditions
4. Swallowing interrupt flags
5. Missing null validation

---

## 📚 Resources for Further Learning

- **Java Concurrency in Practice** - Essential reading for concurrent programming
- **Java Thread API** - Official documentation
- **ExecutorService** - Built-in thread pool from Java standard library (compare with this implementation)
- **BlockingQueue** - Official documentation for thread-safe queues

---

## ⚡ Performance Considerations

### Known Limitations
- **Synchronous submission** - `AddTask()` is synchronized
- **Fixed worker count** - Cannot scale up/down based on load
- **No task priorities** - FIFO queue only
- **Blocking behavior** - Queue throws exception when full (no wait/retry)
- **Infinite loop** - Worker threads have no clean exit condition

---

## 🐛 Known Issues

See `CODE_REVIEW.md` for detailed analysis of:
- 3 critical issues
- 7 major issues
- Recommendations for fixes

---

## 📋 Files Description

### Main.java
- Contains test suite with 10 edge case tests
- Demonstrates all major features
- Good reference for usage patterns

### Pool.java
- Main thread pool orchestrator
- Manages worker threads and task queue
- Provides `AddTask()` and `AbortTask()` methods

### Worker.java
- Wrapper around Thread class
- Manages thread creation and lifecycle
- Provides `createAndStart()` and `interrupt()` methods

### ThreadRunnable.java
- Implements `Runnable` for worker threads
- Main loop that processes tasks from queue
- Uses `while(true)` - infinite loop with no graceful shutdown

### Task.java
- Wraps `Runnable` for execution
- Adds context (worker name) to execution

### MyBlockingQueue.java
- Thread-safe wrapper around `ArrayBlockingQueue`
- Enforces capacity limits
- **Note:** Uses `static` BlockingQueue

---

## 🎯 Challenge: Refactor This Code

This is an excellent opportunity to practice code improvement! Try implementing:

1. **Remove Static State** - Fix MyBlockingQueue to use instance variable
2. **Graceful Shutdown** - Replace infinite loop with proper shutdown mechanism
3. **Exception Handling** - Fix InterruptedException handling
4. **Input Validation** - Add null checks and parameter validation
5. **Better Naming** - Rename cryptic variables (bq, t1, r1, temp)
6. **Add Monitoring** - Implement `getQueueSize()`, `getWorkerCount()` methods
7. **Lifecycle Management** - Add `shutdown()` and `isShutdown()` methods

Then compare your refactoring with the suggestions in `CODE_REVIEW.md`!

---

## 📞 Notes

- This is a **learning project**, not production code
- Current implementation has known issues documented in CODE_REVIEW.md
- Use this as a **before** state for refactoring practice
- Excellent for understanding code review feedback
- Great interview preparation project

---

## 📄 License

This is a practice project for educational purposes. Feel free to use, modify, and learn from it.

---

## ✨ Conclusion

This custom thread pool implementation provides an excellent foundation for understanding how concurrent systems work. Use the CODE_REVIEW.md to identify areas for improvement, then refactor the code yourself as a learning exercise!

**Happy Learning! 🚀**
