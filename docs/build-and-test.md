# Build and test guidance

This file describes how to validate the project without assuming a build tool or vendor-specific setup.

## General workflow

- Inspect the real Gradle configuration before running commands.
- Prefer the smallest relevant task for the changed area.
- Run broader verification only when the change affects shared contracts or cross-platform behavior.
- Record constraints if a target cannot be built in the current environment.

## Typical validation categories

- Shared logic: common unit tests
- Compose UI: UI tests or screenshot checks when available
- Platform integrations: target-specific compile or test tasks
- Interop: Kotlin-to-Swift validation for Apple targets when relevant

## Good practices

- Use deterministic tests.
- Avoid real network calls or wall-clock timing in CI-critical checks.
- Keep fixtures and examples sanitized and non-sensitive.
- Treat build failures as an authoritative signal; do not guess around them.

## CI expectations

- The repository CI should validate the core project tasks that are required by the architecture.
- If a target is unsupported on the current host, document the limitation instead of claiming the check passed.
- Keep the task list aligned with the actual repository configuration.
