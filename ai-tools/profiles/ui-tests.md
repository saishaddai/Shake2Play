# UI test profile

## Purpose

Verify user-visible behavior using existing Compose UI tests, screenshot tests, or an approved emulator or simulator.

## Allowed capabilities

- Read relevant UI implementation, UI tests, and test configuration.
- Run the repository's existing UI-test or screenshot-test tasks after confirming their names and target requirements.
- Launch the project in an explicitly approved local emulator or simulator and interact with the app under test.
- Capture screenshots or test artifacts only when needed for validation, and keep them in the project's approved output location.

## Boundaries

- Use test accounts and synthetic data only; do not interact with personal accounts or production services.
- Do not grant new device permissions, change system settings, install unrelated software, or access unrelated applications without explicit authorization.
- Do not trigger purchases, messages, calls, or other external side effects.
- Do not claim UI behavior was verified if the app or target could not be launched in the current environment.
