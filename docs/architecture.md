# Architecture overview

This project targets Android and iOS with Kotlin Multiplatform and shares its application UI using Compose Multiplatform. This is the recommended architecture for new work; the checked-in Gradle configuration and source sets remain authoritative if they differ from the example below.

## Default shape

Start with the existing shared Compose application module (often named `composeApp`) and organize its common code by feature. Keep the app entry points thin: Android and iOS host the same shared root composable, while native lifecycle and device integrations remain at their platform boundaries. Avoid splitting code into more Gradle modules until a real build, ownership, reuse, or dependency-isolation need appears.

```text
composeApp/
	src/
		commonMain/kotlin/<package>/
			app/
				App.kt
				navigation/
			core/
				platform/                 # Small cross-cutting platform contracts only
				ui/                       # Shared design system and reusable components
			feature/
				play/
					domain/                 # Feature rules and models, when warranted
					data/                   # Implementations and mappings, when warranted
					presentation/           # State, actions, state holder, route
					ui/                     # Stateless Compose screen/components
				settings/
					presentation/
					ui/
		commonTest/kotlin/<package>/
			feature/...
		androidMain/kotlin/<package>/
			platform/                   # Android adapters and actual declarations
		iosMain/kotlin/<package>/
			platform/                   # iOS adapters and actual declarations
		androidUnitTest/kotlin/<package>/
		iosTest/kotlin/<package>/
iosApp/                           # Native iOS host, if the project has one
	...
```

This is a package/source-set guide, not a mandate to create empty directories. Use the repository's actual module names and target hierarchy. A template may place Android entry code in `composeApp/src/androidMain` and the SwiftUI host in a separate `iosApp`; follow the existing setup.

## Ownership and dependencies

- `commonMain` owns shared Compose screens, shared navigation, presentation state, platform-independent domain rules, and data code that can truly run on both targets.
- A feature's presentation layer translates user actions into domain operations and exposes UI state. Its Compose screen renders that state and emits actions; it does not own repositories, platform APIs, or long-running work.
- Domain code contains business rules and stable feature models. It does not depend on Compose, platform frameworks, or data implementations. Add use cases or repository interfaces only when they clarify orchestration, a real data boundary, or testing.
- Data code implements required persistence, network, or other external boundaries. Keep platform-independent implementations in common code; use platform implementations only when native APIs or platform constraints require them.
- `androidMain` and `iosMain` own platform adapters, permissions, native lifecycle hooks, and actual implementations of common contracts. Keep SwiftUI/UIKit and Android framework types out of `commonMain`.
- Android and iOS entry points initialize/host the shared app and provide platform dependencies. They should not fork shared business behavior or duplicate shared screens.
- Dependencies flow from UI/presentation toward domain contracts, and from data/platform implementations toward those contracts. Domain rules must not depend on UI or infrastructure.
- Keep app-wide cross-cutting code genuinely small. Prefer feature-local code over generic `common`, `utils`, or `base` packages.

## Shared UI and state

- Prefer one shared Compose implementation for screens and navigation. Introduce platform-specific UI only for a native experience or API that cannot reasonably be shared.
- Keep composables deterministic: render inputs, emit callbacks, and keep side effects out of the composition body. Use effects only for composition-scoped UI work and follow existing project patterns.
- Give each screen or feature one state owner. Expose immutable state and explicit user actions; use the project's established `StateFlow`/coroutine conventions and lifecycle-aware collection approach.
- Do not assume AndroidX ViewModel, Android lifecycle APIs, or a particular navigation/DI library are available in common code. Use the existing KMP-compatible choice if the project has one; otherwise keep the boundary simple and introduce a dependency only when needed.
- Keep transient UI state local when it does not need business logic, cross-screen sharing, or restoration. Persist/restore state only when product behavior requires it.

## Platform boundaries

- Define a narrow common interface for a platform capability only when shared code needs to invoke it. Inject the capability from the app composition boundary; avoid service locators and global mutable state.
- For capabilities such as motion sensors, permissions, audio routing, or native lifecycle callbacks, keep API calls and resource cleanup in platform implementations. Handle unavailable capability and permission denial as expected states.
- Use `expect`/`actual` only for small platform differences where that mechanism is clearer than an injected interface. Do not use it to hide broad platform-specific behavior in shared code.

## Tests

- Put shared business and state-transition tests in `commonTest`; test observable behavior, failure paths, and cancellation with deterministic fakes.
- Test platform adapters in their platform test source sets where practical. Do not require hardware, real network access, or wall-clock delays for ordinary unit tests.
- Add Compose UI tests for important shared interaction flows when the project has a working test setup. Keep platform-specific host tests focused on integration and lifecycle behavior.
