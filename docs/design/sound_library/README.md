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
| Sound library list | `App()` and `SoundboardState.sounds` | Existing shared screen uses sound-title buttons; it is not yet the designed image grid. |
| Sound item title | `SoundItem.title` | Existing model field. |
| Select a sound | `SoundboardPresenter.selectSound()` and `SoundboardState.selectedSoundId` | Existing selection behavior; screen transition is planned. |
| Preview selected sound | `SoundboardPresenter.previewSelectedSound()` and `SoundPlayer` | Existing behavior; preview control is on the current shared screen. |
| Search sounds | No existing project symbol | Accepted work for this iteration; search should filter the existing list without changing the sound library. |
| Empty and playback error feedback | `SoundboardState` and `App()` | Existing shared states and feedback. |

The design's artwork grid, recording button, and bottom tabs are not requirements for this iteration. Do not add inactive controls.