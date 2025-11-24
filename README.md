☕ Toy Language Interpreter
Overview
This project is an interpreter for a custom, strongly-typed, imperative Toy Language, designed to demonstrate foundational concepts in programming language design and runtime system architecture. The interpreter processes programs written in the Toy Language, manages memory, and executes statements step-by-step.

The entire application is developed using Java and follows the Model-View-Controller (MVC) architectural pattern.

✨ Core Implemented Concepts
The interpreter provides full functionality for managing complex program states and memory references:

Data & Architecture
Architecture: Implements the Model-View-Controller (MVC) paradigm, separating the core language logic (Model) from the execution flow (Controller) and the console menu (View/Command Pattern).

Abstract Data Types (ADTs): Custom implementations of structures like MyStack, MyDictionary, MyList, and MyHeap.

Types: Supports int, bool, string, and Reference Types (Ref).

Memory & Execution
Execution Flow: Uses an Execution Stack (ExeStack) and a Symbol Table (SymTable) to manage local variable scope and instruction flow.

Dynamic Memory (Heap): Implements a shared memory structure (MyHeap) supporting memory allocation (new), reading (rH), and writing (wH).

Garbage Collection (GC): Features an implementation of a Safe Garbage Collector to identify and deallocate unreferenced addresses, handling complex indirect references (e.g., Ref Ref int).

Control Flow: Implementation of If-Else and While loop statements.

I/O and Debugging
File Handling: Implements file I/O operations (openRFile, readFile, closeRFile).

Logging: Detailed logging of the entire program state (ExeStack, SymTable, Out, FileTable, Heap) after every execution step into dedicated log files.
