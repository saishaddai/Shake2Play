# Design System Document

## 1. Overview & Creative North Star: "The Sonic Pulse"
This design system is built to transform a standard utility into a high-octane, immersive sensory experience. Our Creative North Star is **"The Sonic Pulse"**—a philosophy that views the interface not as a static grid, but as a living, breathing frequency. 

To move beyond "standard" Android templates, we employ **Kinetic Depth**. By layering high-saturation accents (`primary`, `secondary`) against a void-like foundation (`surface-container-lowest`), we create a sense of infinite space. We break the "template" look through intentional asymmetry in card layouts and heavy, editorial typography that feels "loud" even when silent.

---

## 2. Colors & Atmospheric Depth
The palette is designed for high-energy impact, utilizing the contrast between a deep nocturnal base and neon-infused accents.

### The "No-Line" Rule
**Prohibit 1px solid borders for sectioning.** Boundaries must be defined solely through background color shifts or tonal transitions. Use `surface-container-low` for large section backgrounds sitting on a `surface` base. 

### Surface Hierarchy & Nesting
Treat the UI as a series of stacked, glowing plates. 
- **Base Layer:** `surface-container-lowest` (#000000).
- **Secondary Sectioning:** `surface-container` (#1a1a1a).
- **Interactive Elements (Cards):** Use `surface-container-highest` (#262626) with 40% opacity and a 20px backdrop blur to create a "Glassmorphism" effect, allowing the "Sonic Pulse" of background images to bleed through.

### Signature Textures
Main CTAs and hero elements must use a **Linear Gradient** (45°) transitioning from `primary` (#b6a0ff) to `primary-dim` (#7e51ff). This prevents the "flat-and-cheap" look and provides a professional, liquid-metal sheen.

---

## 3. Typography: Editorial Impact
We utilize a pairing of **Plus Jakarta Sans** (Display/Headlines) for its geometric aggression and **Be Vietnam Pro** (Body/Labels) for its modern, clean legibility.

- **Display-LG (3.5rem):** Reserved for "Shake to Play" states or high-impact sound titles. Use `-2%` letter spacing for a tighter, premium feel.
- **Headline-MD (1.75rem):** Used for category headers. These should be set in Bold/ExtraBold to anchor the page.
- **Body-LG (1rem):** High-readability weight for descriptions.
- **Label-MD (0.75rem):** Uppercase with `10%` letter spacing for metadata (e.g., duration, file size), conveying a technical, "pro-audio" aesthetic.

---

## 4. Elevation & Depth: Tonal Layering
Traditional material shadows are too muddy for a neon-dark theme. We achieve lift through **Light Emission**.

- **The Layering Principle:** Place a `surface-container-high` card on a `surface-dim` background. The delta in lightness provides the "lift."
- **Ambient Glows:** For floating "Play" buttons, use a shadow with a 24px blur, 10% opacity, using the `primary` (#b6a0ff) color instead of black. This mimics the way a neon light casts a glow on a dark surface.
- **The "Ghost Border" Fallback:** If a divider is mandatory for accessibility, use `outline-variant` (#484847) at **15% opacity**. Never use 100% opaque lines.
- **Glassmorphism:** Navigation bars and "Shake" overlays must use a `surface-variant` fill at 60% opacity with a heavy backdrop blur (32px), creating a "Frosted Neon" effect.

---

## 5. Components

### Cards & Lists
- **Sound Cards:** Must be full-bleed. The background image should use a "Darken" blend mode against `surface-container-highest`.
- **Forbid Dividers:** Separate list items using `spacing-4` (1rem) of vertical white space or by alternating background tones between `surface-container-low` and `surface-container`.
- **Corner Radius:** All cards must use `rounded-lg` (2rem) to maintain a playful, modern silhouette.

### Buttons (The Kinetic Triggers)
- **Primary Play Button:** Full gradient (`primary` to `primary-dim`), `rounded-full`, with a haptic-press state that scales the button to 95%.
- **Tertiary (Ghost):** No background. Use `secondary` (#00e3fd) text with `label-md` styling for "View All" or "Settings" actions.

### The "Shake" Visualizer
- **Central Element:** A large `rounded-xl` (3rem) container.
- **Interaction:** On movement, the container should scale and its "glow" (shadow) should expand using the `tertiary` (#ff6b99) token.
- **Minimalism:** No text labels. Use only `headline-lg` typography to announce the active sound.

---

## 6. Do’s and Don'ts

### Do:
- **Do** use `primary` and `secondary` accents sparingly to guide the eye toward "Play" actions.
- **Do** embrace asymmetry. A card might be slightly wider than the one below it to create a "shuffled" deck feel.
- **Do** use `secondary-container` for subtle "active" states in navigation.

### Don’t:
- **Don’t** use pure white (#ffffff) for long-form body text; use `on-surface-variant` (#adaaaa) to reduce eye strain in dark mode.
- **Don’t** use standard Android "Ripple" effects. Instead, use a subtle "Glow Expansion" using the accent colors.
- **Don’t** use sharp corners. Everything in this system is rounded and tactile to emphasize the "playful" nature of sound.