☕ Toy Language Interpreter Overview

This project is an interpreter for a custom, strongly-typed, imperative Toy Language, designed to demonstrate foundational concepts in programming language design and runtime system architecture. The interpreter processes programs written in the Toy Language, manages memory, and executes statements step-by-step.

The entire application is developed using Java and follows the Model-View-Controller (MVC) architectural pattern. It features both a command-line interface and a fully functional JavaFX GUI for visualizing the program state during execution.

✨ Core Implemented Concepts

The interpreter provides full functionality for managing complex program states, concurrency, and memory references:

🖥️ Data & Architecture

Architecture: Implements the Model-View-Controller (MVC) paradigm, strictly separating the core language logic (Model) from the execution flow (Controller) and the user interfaces (View).

Abstract Data Types (ADTs): Custom, generic implementations of fundamental structures like MyStack, MyDictionary, MyList, and MyHeap.

Types: Supports int, bool, string, and Reference Types (Ref), allowing for complex pointer-like operations.

🧠 Memory & Execution

Execution Flow: Uses an Execution Stack (ExeStack) and a Symbol Table (SymTable) to manage local variable scope and instruction flow.

Dynamic Memory (Heap): Implements a shared memory structure (MyHeap) supporting dynamic memory allocation (new), reading from heap (rH), and writing to heap (wH).

Garbage Collection (GC): Features a custom Safe Garbage Collector that identifies and automatically deallocates unreferenced heap addresses, capable of handling complex chains of indirect references (e.g., Ref Ref int).

Control Flow: Native implementation of standard control structures including If-Else and While loops.

🚀 Advanced Features (Branch-Specific)

The project repository includes multiple branches, each implementing specific advanced language features and synchronization mechanisms typically found in complex runtime environments:

Concurrency: Support for multi-threading via fork() statements.

Synchronization Primitive: Implementations (in separate branches) for CyclicBarrier, Lock Mechanism, Semaphore, and CountDownLatch.

Advanced Loops: Transformations and support for For loops, Repeat-Until, and Do-While constructs.

Procedures: Support for defining and calling procedures.

Type Checker: A static analysis pass that verifies type safety (e.g., int vs bool usage) before execution begins.

📂 I/O and Debugging

File Handling: Implements file I/O operations (openRFile, readFile, closeRFile) with a dedicated File Table.

Logging: Detailed logging of the entire program state (ExeStack, SymTable, Out, FileTable, Heap) after every execution step into dedicated log files.

Graphical User Interface (GUI): A JavaFX-based interactive debugger that displays the real-time state of the Heap, Symbol Table, Output, File Table, and Execution Stack, allowing users to select programs and run them step-by-step.
