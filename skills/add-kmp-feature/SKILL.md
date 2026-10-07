# Add a Kotlin Multiplatform feature

Use this workflow when introducing a new cross-platform capability.

## Goals

- Keep the feature reusable across supported targets.
- Preserve clean module boundaries.
- Add the minimum required platform-specific shims.

## Workflow

1. Inspect the existing module and target structure.
2. Determine whether the feature belongs in shared code or platform code.
3. Define the contract and public APIs before implementation.
4. Implement in the narrowest correct source set.
5. Add tests for the expected behavior and failure cases.
6. Run the smallest relevant validation command and confirm the result.

## Guardrails

- Do not mix platform APIs into shared code.
- Do not duplicate business logic across targets.
- Do not add dependencies without checking project conventions.
- If the feature requires platform-specific integration, isolate it behind a small interface.
