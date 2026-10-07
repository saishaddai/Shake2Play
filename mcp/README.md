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

## Notes

MCP is a separate concern from Kotlin Multiplatform. This project may use KMP for shared app code, while MCP remains an optional integration layer for AI tooling or external tooling access.
