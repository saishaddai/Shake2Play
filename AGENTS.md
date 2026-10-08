# Repository guidance

This repository is a Kotlin Multiplatform project using Compose Multiplatform. Treat the checked-in Gradle configuration and current source tree as authoritative; targets, modules, and conventions may evolve.

## Working in this repository

- Before changing code, inspect the relevant module, neighboring implementation, tests, and Gradle configuration. Do not assume a directory or target exists based on examples in this file.
- Make the smallest change that solves the request. Preserve public APIs and established patterns unless the task requires a change.
- Keep platform-specific behavior in its platform source set. Put code in `commonMain` only when it can compile and behave correctly on every target that consumes it.
- Prefer existing libraries and project abstractions over adding dependencies or introducing new frameworks. When a dependency is needed, use the project's version catalog and established dependency conventions if present.
- Do not silently change product behavior, supported platforms, minimum OS versions, dependency versions, or build configuration outside the task's scope.
- Do not claim a build or test passed unless it was run. Report relevant checks that could not be run and why.

## Kotlin conventions

- Follow Kotlin style and the conventions used in the surrounding module. Use `UpperCamelCase` for types, `lowerCamelCase` for properties and functions, and `UPPER_SNAKE_CASE` for constants when appropriate.
- Use descriptive names. Avoid unnecessary abbreviations, one-letter names, and redundant type information in names.
- Prefer immutable `val` and read-only interfaces. Keep mutable state private and expose the smallest API needed.
- Prefer small functions with clear responsibilities, explicit contracts, and Kotlin's standard collection and scope APIs where they improve clarity.
- Model expected failures explicitly. Do not use exceptions for ordinary control flow or swallow exceptions without a deliberate recovery/reporting policy.
- Avoid `!!`, unchecked casts, and broad visibility. If unavoidable, explain the invariant in a concise comment and keep the scope narrow.
- Use structured concurrency: tie coroutines to an appropriate scope, propagate cancellation, and avoid `GlobalScope`, blocking calls, or detached jobs.
- Use `expect`/`actual` only when a real platform-specific implementation is needed. Prefer a small common interface with platform implementations when it makes dependencies and tests clearer.
- Keep serialization formats and public data models deliberate. Use the project's established serialization library and avoid exposing implementation-only types as cross-platform contracts.

## Kotlin Multiplatform and Compose Multiplatform

- Check the configured targets and source-set hierarchy before placing code or tests. Common code must not depend on JVM-, Android-, Apple-, or other target-specific APIs.
- Keep platform integrations behind narrow APIs and implement them in the appropriate platform source set. Do not duplicate shared business rules across targets.
- Put reusable UI in the existing shared Compose source set when the UI is intended to be shared. Keep OS-specific UI, lifecycle integration, permissions, and service wiring in the platform app unless the project has an established alternative.
- Keep composables focused on rendering and UI events. Hoist state to the existing state holder or screen-level owner; do not perform long-running work or side effects directly during composition.
- Use stable, meaningful keys in lazy layouts. Keep UI state saveable only where restoration is a product requirement and supported by the project's architecture.
- Keep resource lookup and platform-specific resource behavior compatible with the configured targets. Do not assume Android resource APIs are available in common code.
- Avoid blocking the main thread. Dispatch work using the project's established coroutine and dispatcher abstractions, especially in common code.

## App architecture rules

- Keep the application architecture simple and explicit: shared domain logic, shared UI where appropriate, and platform-specific adapters only where required.
- Put reusable business rules and data models in shared code. Keep platform-only concerns such as sensors, system permissions, audio routing, native lifecycle hooks, and OS integrations in platform-specific modules.
- Prefer a small number of boundaries: common logic, UI layer, and platform adapters. Do not introduce unnecessary abstraction layers for trivial code.
- Keep screen state and view behavior separate from long-running work. If a feature needs async work, use the project's existing coroutines and state representation patterns rather than embedding side effects in composables.
- Prefer one clear ownership model for each feature: one owner for business logic, one owner for UI composition, and one owner for platform integration when needed.
- Keep navigation, sound playback, motion handling, and data flow predictable and testable. Do not scatter platform-specific logic across unrelated modules.
- When adding a new feature, decide early whether it is common, shared-UI, or platform-specific. Do not mix those responsibilities in the same class or file unless the code genuinely requires it.
- Treat the module structure as the product architecture. If a feature belongs to shared logic, place it in common code; if it belongs to a device capability, place it in the platform layer.

## Basic module and package conventions

- Keep feature folders grouped by business capability, not by technical convenience. A common pattern is: `featureName/`, `featureName/ui/`, `featureName/domain/`, and `featureName/data/` when the scope requires it.
- In shared code, prefer package names that reflect the domain and capability. Avoid generic packages such as `utils`, `common`, or `misc` unless the code is legitimately cross-cutting.
- In platform-specific code, keep adapter and integration code near the platform boundary, not spread across unrelated screens.
- Keep each feature cohesive: place models, state, use cases, and UI relevant to the same feature in the same feature area when possible.
- If a high-level feature has shared and platform-specific pieces, keep the common contract small and the platform-specific implementation behind that contract.
- Do not create deep folder nesting for every tiny helper. Favor a small number of feature packages with clear responsibilities.
- Prefer naming that describes the business role of the code rather than the implementation detail. For example, `soundPlayback` or `shakeDetection` is clearer than generic wrappers or adapter-only names.

## Swift and Apple-platform interop

- Follow the existing Swift style in Apple app targets: `UpperCamelCase` for types and `lowerCamelCase` for properties, methods, and local values. Preserve established API naming at language boundaries.
- Keep Swift-specific app lifecycle, UIKit/SwiftUI integration, entitlements, and Apple framework use in Apple-platform code. Do not move these APIs into common Kotlin source sets.
- Treat Kotlin/Native exports as a public interop surface. Keep exported APIs small, intentional, and documented where their behavior is not obvious; avoid exposing internal implementation types.
- Account for Kotlin/Swift differences in nullability, error propagation, collection types, and concurrency. Follow the project's configured Kotlin/Native and Swift concurrency model; do not add ad hoc thread hops or suppress concurrency diagnostics without understanding them.
- Do not edit generated Kotlin/Native or Xcode build output. Change the source declaration or build configuration that generates it.
- When changing an exported API, inspect and validate its Swift call sites as well as Kotlin callers.

## Tests and validation

- Add or update tests for behavior changes. Prefer common tests for shared logic and platform-specific tests when behavior depends on a platform API.
- Test observable behavior, edge cases, failure paths, and cancellation where relevant. Avoid tests that merely mirror implementation details.
- For Compose UI changes, use the project's existing UI-test or screenshot-test patterns when available; keep business logic testable outside composables.
- For Kotlin/Swift interop changes, validate both the Kotlin API and the affected Apple-platform integration when the environment permits.
- First inspect the Gradle wrapper, available tasks, and project documentation. Run the narrowest relevant test or compile task, then broader checks required by the change. Do not invent task names.
- If a target cannot be built on the current host or required tooling is unavailable, run the checks that are available and state the limitation.
- Keep tests deterministic: avoid real network access, wall-clock timing, and shared mutable state unless specifically under test. Use fakes or the project's existing test utilities.

## Security and privacy

- Never add credentials, API keys, signing keys, private certificates, tokens, or personal data to source control, tests, logs, or documentation. Use the repository's established secret-management mechanism and safe local examples.
- Validate and constrain untrusted input at system boundaries. Avoid logging sensitive values, authentication material, precise location, or user-generated content unless explicitly required and appropriately redacted.
- Request only the platform permissions and capabilities required for the feature. Explain user-facing permission use and handle denial or revocation without crashing.
- Use secure transport and vetted cryptographic libraries for security-sensitive features. Do not implement custom cryptography or weaken certificate validation.
- Review dependency additions for necessity, maintenance, licensing, and known security concerns. Avoid broad permissions and unsafe deserialization.
- Do not include real user data in fixtures. Keep test credentials fake and clearly nonfunctional.

## Dependencies, generated files, and documentation

- Keep dependency and plugin versions in the project's existing central configuration when available. Avoid duplicate or floating versions.
- Do not commit generated build output, local IDE state, machine-specific paths, or large derived artifacts unless the repository explicitly requires them.
- Update relevant documentation when changing setup, supported targets, user-visible behavior, or developer workflows.
- Keep comments focused on non-obvious rationale or constraints; do not narrate straightforward code.

## GitHub issue workflow

- When a task depends on a tracked bug, feature, or improvement, inspect the repository's open issues before making changes.
- Use the repository's GitHub issue workflow as the source of truth for planned work. Do not create duplicate issues for the same root cause.
- Before creating a new issue, confirm the task is not already tracked and gather the required context: summary, expected behavior, current behavior, reproduction steps, and acceptance criteria.
- Do not create or update GitHub issues without an explicit repository context and an appropriate reason. Treat issue changes as operational metadata, not code changes.
- Keep issue bodies factual and concise. Never include secrets, tokens, private URLs, credentials, or user data.
- If the project uses a GitHub-connected tool or MCP server, that integration must be scoped to the current repository, with the minimum required permissions only.
- Only read, create, update, or close issues that are explicitly relevant to this repository. Do not touch unrelated repositories or organization settings.
- Prefer issue comments and updates over broad or undocumented changes. Record the outcome, links to implementation, and any validation status when appropriate.
- If the environment cannot reach GitHub or the required token is unavailable, state the limitation and continue with local work without claiming the issue was updated.
- Never claim an issue was created or modified unless the operation was actually confirmed by the connected GitHub tool or API.

## GitHub integration guardrails

- Store GitHub credentials or access tokens outside the repository. Never commit them to source control or documentation.
- Limit issue-management access to the current repository and the minimum set of permissions needed for listing, reading, creating, commenting on, and updating issues.
- Do not allow issue automation to perform unrelated GitHub actions such as repository configuration changes, branch administration, or release publication unless explicitly approved.
- Keep issue automation architecture transparent and auditable. Document which operations are allowed and which are blocked.
- Treat issue automation as a support tool for project coordination, not as an unrestricted external action channel for the AI.
