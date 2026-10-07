# Architecture overview

This document describes the intended repository architecture for a Kotlin Multiplatform + Compose Multiplatform project.

## Principles

- Shared logic belongs in common code when it can compile and run across all supported targets.
- Platform-specific code stays behind narrow APIs and is implemented in the correct platform source set.
- UI composition is shared when the feature is intended to be cross-platform; platform-only UI remains in platform-specific code.
- Public contracts are deliberate and stable.

## Layering

- `shared` or equivalent common module: domain logic, models, use cases, shared business rules, and platform-agnostic data flow.
- `composeApp` or platform UI modules: screens, state, navigation, and UI composition.
- Platform app entry points: app lifecycle, permissions, native integrations, and OS-specific services.
- Test modules: common tests for shared logic, platform tests for OS-bound behavior.

## Guidance

- Do not place Android-specific APIs in shared code.
- Do not expose implementation details through public shared APIs.
- Keep dependency boundaries explicit and avoid hidden cross-layer coupling.
- Prefer existing project abstractions over ad hoc platform-specific workarounds.
