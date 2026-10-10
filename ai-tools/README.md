# AI tool capability profiles

These profiles describe the minimum tool capabilities appropriate for common project tasks. They are vendor-neutral policy documents, not executable permissions or tool implementations.

## Use a profile

1. Select the profile that matches the task.
2. Use only the actions listed as allowed, and only on the task's relevant project files or test environment.
3. If the task needs an action outside the profile, stop and get authorization before proceeding.
4. Report which validation or media actions were actually performed.

## Profiles

- `profiles/unit-tests.md`: run and maintain unit tests.
- `profiles/ui-tests.md`: launch and interact with the app in an approved emulator, simulator, or UI-test environment.
- `profiles/images.md`: inspect or modify authorized image assets.
- `profiles/audio.md`: validate or play project audio in an approved test environment.

## Enforcement boundary

Repository instructions can guide an AI agent but cannot technically hide or disable tools. Enforce least privilege in the AI runtime, tool server, operating-system permissions, and CI environment. Configure each runtime to expose only the tools needed for the active task profile. Do not put credentials or machine-specific connection settings in this directory.

A runtime-specific configuration may map these profile names to concrete tools, but that mapping should live outside this vendor-neutral policy unless the project deliberately supports and documents a particular runtime.
