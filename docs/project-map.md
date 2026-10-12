# Project map

This file helps humans and agents find the intended ownership of code and configuration. The paths below describe recommended ownership; use the actual checked-in Gradle modules and source sets when they differ.

## Top-level directories

- `docs/`: project architecture, build/test guidance, and decision records.
- `skills/`: reusable task instructions for recurring work patterns.
- `ai-tools/`: vendor-neutral capability profiles describing which tool actions are appropriate for task types.
- `mcp/`: documentation for Model Context Protocol integrations when the project uses them.
- `scripts/`: local automation and validation helpers.
- Shared Compose KMP app module (often `composeApp`): shared app shell, navigation, feature presentation, Compose UI, and common business/data code.
- `androidMain` / Android app host: Android entry point, lifecycle, permissions, and Android-specific adapters.
- `iosMain` / iOS app host (often `iosApp`): iOS adapters and the native host that launches the shared Compose UI.
- `commonTest` and platform test source sets: shared behavior tests and platform integration tests, respectively.
- `core/` inside shared code: only genuinely cross-cutting contracts or UI primitives; feature code belongs under `feature/<name>/`.

See [architecture.md](architecture.md) for the recommended source tree, dependency direction, and ownership rules.

## Common ownership rules

- Shared screens and navigation live in common Compose code so Android and iOS use the same UI by default.
- Shared domain logic and platform-independent data implementations live in common sources.
- Platform source sets own permissions, native adapters, and lifecycle wiring; app hosts provide these dependencies to shared code.
- Group code by feature first, and add domain/data subpackages only when the feature needs those boundaries.
- Tests live in the matching common or platform test source set and verify behavior at that boundary.
- Generated files, build outputs, and local machine state should not be committed.

## When adding a feature

1. Decide whether the work is common or platform-specific.
2. Place logic in the correct module before adding UI or infrastructure.
3. Add tests for behavior, failure paths, and edge cases.
4. Verify with the smallest relevant build or test command.
