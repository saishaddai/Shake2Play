# Sound Library

The Sound Library design is the entry flow for browsing sounds, selecting one, and previewing it. The design includes a search action, image grid, recording button, and persistent bottom tabs; the agreed implementation scope differs from the full mockup.

## Design files

- [Screen image](./screen.png)
- [HTML reference](./code.html)
- [Design system](./DESIGN.md)

## Agreed scope

- Include search over the five sounds already bundled with the app.
- Keep the existing five sound clips. Do not imply that the mockup's “20 High-Fidelity Clips” are available.
- Do not add artwork until approved local assets are provided. The remote image URLs in the HTML are reference-only and must not be used by the app.
- Defer recording/import and the persistent bottom tab bar.
- Move to Shake to Play by selecting a sound; return from that flow with back navigation.
- Do not show duration or category until reliable metadata is available.

## Existing implementation

| Design behavior | Project name | Status |
| --- | --- | --- |
| Sound library list | `SoundLibraryScreen` and `SoundboardState.sounds` | Shared screen displays the actual available sound titles; it does not imply the mockup's 20 clips or use unapproved artwork. |
| Sound item title | `SoundItem.title` | Existing model field. |
| Select a sound | `SoundLibraryScreen`, `SoundboardPresenter.selectSound()`, and `SoundboardState.selectedSoundId` | Implemented; selected state is exposed to accessibility services. Screen transition is planned for step 4. |
| Preview selected sound | `SoundLibraryScreen`, `SoundboardPresenter.previewSelectedSound()`, and `SoundPlayer` | Implemented; platform unavailability is explained and the action is disabled when no player is injected. |
| Search sounds | `SoundLibraryScreen` and `filterSoundItems()` | Implemented as case-insensitive title filtering over the existing list; an empty search restores all entries and no-match feedback is shown. |
| Empty and playback error feedback | `SoundLibraryScreen`, `SoundboardState`, and `App()` | Empty library and search no-match states are distinct; playback status and error feedback remain visible in `App()`. |

The design's artwork grid, recording button, and bottom tabs are not requirements for this iteration. Do not add inactive controls.