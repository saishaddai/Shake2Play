# Project map

This file helps humans and agents find the intended ownership of code and configuration.

## Top-level directories

- `docs/`: project architecture, build/test guidance, and decision records.
- `skills/`: reusable task instructions for recurring work patterns.
- `ai-tools/`: vendor-neutral capability profiles describing which tool actions are appropriate for task types.
- `mcp/`: documentation for Model Context Protocol integrations when the project uses them.
- `scripts/`: local automation and validation helpers.
- `shared/`: shared KMP logic and cross-platform models.
- `composeApp/`: Compose Multiplatform UI module or equivalent shared presentation layer.
- `platform/` or app-specific modules: Android, iOS, or other native targets.

## Common ownership rules

- Shared domain logic and data models live in common sources.
- Platform code owns permissions, native adapters, and lifecycle wiring.
- Tests belong next to the code they verify or in a clearly named test module.
- Generated files, build outputs, and local machine state should not be committed.

## When adding a feature

1. Decide whether the work is common or platform-specific.
2. Place logic in the correct module before adding UI or infrastructure.
3. Add tests for behavior, failure paths, and edge cases.
4. Verify with the smallest relevant build or test command.
