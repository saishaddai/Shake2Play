# MCP guidance

This directory is for documentation about Model Context Protocol integrations when the repository uses them.

## Purpose

- Keep MCP-related setup, access requirements, and responsibilities documented.
- Separate the protocol configuration from the application architecture.
- Avoid mixing MCP setup with Kotlin or Compose project rules.

## General rules

- Document tool purpose, inputs, and outputs.
- Keep credentials and tokens out of source control.
- Describe environment requirements and startup expectations.
- Note any security or permission boundaries the MCP server enforces.

## GitHub issue access model

- The project may expose a GitHub issue-management capability for AI-assisted project coordination.
- That capability should be limited to the current repository and to issue-related operations only.
- Implemented as a narrow adapter or MCP tool, not as unrestricted GitHub browsing.

## Allowed operations

- list issues for this repository
- read a specific issue and comments
- create a new issue with a standard template
- update issue title, body, labels, or state when explicitly justified
- add a comment with progress or validation notes

## Restricted operations

- Do not modify repository settings or permissions
- Do not access unrelated repositories
- Do not create or update issues without a clear task context
- Do not expose credentials, tokens, or secret values in issue bodies or logs
- Do not close or reopen issues without a documented reason

## Secret handling

- Store any GitHub token or OAuth secret outside the repository.
- Use environment variables or secure local configuration only.
- Never write tokens into documentation, scripts, or issue text.

## Notes

MCP is a separate concern from Kotlin Multiplatform. This project may use KMP for shared app code, while MCP remains an optional integration layer for AI tooling or external tooling access. GitHub issue access should be explicit, narrow, and auditable.
