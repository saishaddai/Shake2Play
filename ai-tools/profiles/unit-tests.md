# Unit test profile

## Purpose

Validate shared or platform-specific logic through the project's existing unit-test setup.

## Allowed capabilities

- Read implementation, test, and build configuration files relevant to the requested change.
- Edit relevant production code and tests within the task scope.
- Run existing unit-test and compile tasks after checking the Gradle configuration and available tasks.
- Read test output and inspect local test reports produced by those tasks.

## Boundaries

- Do not invent Gradle task names; inspect the wrapper and task list first.
- Do not access the network, external services, personal data, or unrelated project files unless explicitly required and authorized.
- Do not change CI, dependency versions, signing, or release configuration just to make a test pass without explicit task scope.
- Do not claim a test passed unless the command completed successfully in the current environment.
