# Investigate a build or test failure

Use this workflow when a repository check fails or a regression appears.

## Goals

- Identify the root cause before changing code.
- Confirm the failure with the smallest relevant command.
- Keep the fix narrow and aligned with the architecture.

## Workflow

1. Read the exact error output and identify the failing module or target.
2. Check the surrounding code and recent changes for the failing area.
3. Confirm whether the problem is shared logic, platform-specific code, or interop.
4. Form one concrete hypothesis and test it with the smallest change.
5. Re-run the relevant validation command.
6. Report the actual status, including any limitations.

## Guardrails

- Do not mask errors with generic catch blocks.
- Do not broaden the fix beyond the root cause.
- Do not claim success without fresh verification output.
- If the issue depends on unavailable tooling, state that limitation clearly.
