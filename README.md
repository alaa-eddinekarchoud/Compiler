# Compiler - Test Suite

This project contains a Java-based compiler that performs lexical and syntactic analysis using an SLR (Simple LR) parser.

## How to Run the Tests

Follow these steps to compile the source code and test the provided files.

### 1. Compile the Project

Open your terminal in the project root directory and run:

```powershell
javac -d bin src/parserexemple/*.java src/compilateurnewversion/*.java
```

### 2. Execute Tests

#### Test with Accepted Code

To test the valid code that follows the grammar rules:

```powershell
echo "test/accepted.txt" | java -cp bin compilateurnewversion.Main
```

**Expected Result:** The output should end with `Analyse SLR réussie`.

#### Test with Rejected Code

To test the code containing syntax errors:

```powershell
echo "test/rejected.txt" | java -cp bin compilateurnewversion.Main
```

**Expected Result:** The output should end with `Analyse SLR échouée`.

---

## File Explanations

### `test/accepted.txt`

This file contains valid source code that strictly adheres to the compiler's grammar. It includes:

- **Assignment statements:** `x = 10 ;`
- **Conditional statements:** Full `if ... then ... else ...` blocks with terminating semicolons.
- **Loop structures:** `while ... do { ... }` blocks with multiple statements.
- **Complex Expressions:** Use of parentheses, operators (`+`, `-`, `*`, `/`), and identifiers/numbers.

### `test/rejected.txt`

This file contains several deliberate syntax errors to test the compiler's error detection:

1. **Missing Semicolon:** `x = 10` (Missing `;` at the end).
2. **Missing Else Clause:** The grammar requires `if Condition then Stmt else Stmt`. Providing an `if` without an `else` causes a failure.
3. **Invalid Condition:** `while x do ...` (The grammar requires a comparison like `x > 0` for conditions).
4. **Invalid Expression:** `y = * 5 ;` (An assignment cannot start with a multiplication operator).

## Project Structure

- `src/compilateurnewversion/`: Contains the main entry point and the SLR parser logic.
- `src/parserexemple/`: Contains the lexical analyzer (Scanner) and token definitions.
- `test/`: Contains the test cases used for verification.
- `bin/`: Directory where compiled `.class` files are stored.
