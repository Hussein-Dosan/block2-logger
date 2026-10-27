# Block 3 Logger — Android app

Set-by-set logger for the Block 3 back-and-shoulder specialisation. One
full-screen WebView over a self-contained HTML app in
`app/src/main/assets/index.html`.

**No permissions, no network, no account.** Everything you log is stored in the
WebView's `localStorage` on the phone.

---

## Updating from the Block 2 build

Same package name and same signing key, so this installs **over the top** of the
Block 2 app. Your Block 2 log is kept — logs are stored per block id, and
**Copy summary** still prints the Block 2 history above the Block 3 one.

Drop the whole folder into your existing repo (replacing what's there) and push.
The workflow builds it and publishes the APK as a Release.

---

## Build it

### Option A · GitHub Actions (nothing to install)

1. Push these files to your repo, replacing the old ones.
   Remember `.github/` will not upload through the browser's drag-and-drop —
   edit `.github/workflows/build-apk.yml` in place instead, or use
   **Add file → Create new file** and type the path.
2. Open the **Actions** tab. The `Build APK` workflow runs on push; otherwise
   click it and press **Run workflow**.
3. When it is green, go to the repo's main page → **Releases** in the right
   sidebar → newest release → download `Block3Logger-<n>.apk`.
4. Move it to the phone and tap it.

### Option B · Android Studio

**File → Open**, select this folder, let it sync, press **Run** (▶).
The APK lands at `app/build/outputs/apk/release/app-release.apk`.

---

## Signing

Release builds are signed with the **debug key**, so the APK installs with no
keystore to manage. The debug key is generated per machine: if you built the
Block 2 APK in GitHub Actions, keep building in GitHub Actions. Switching to a
local build produces a signature mismatch and Android will refuse the update
until you uninstall — which would take your log with it. **Back up first if you
ever switch** (Copy backup, paste it somewhere safe).

---

## Updating the program

Everything about the training block is plain data at the top of the `<script>`
in `app/src/main/assets/index.html`:

```js
const BLOCK_ID  = "block3";
const ARCHIVE   = { block2: { … } };   // old blocks, names only, so old logs stay readable
const WEEK_LABELS = ["DL","W1","W2","W3","W4","W5"];
const WEEK_NOTES  = [ … ];
const DAYS        = [ … ];
```

For a new block: edit `DAYS` and `WEEK_NOTES`, move the outgoing block's
exercise names into `ARCHIVE`, change `BLOCK_ID`, bump `versionCode` and
`versionName` in `app/build.gradle.kts`, and push.

**Week model:** `setsFor()` returns 2 sets for weeks 0 and 5 (the deload at each
end), adds a set in week 4 to any exercise flagged `w4:true`, and otherwise uses
the exercise's own `sets`. `tagFor()` hides technique badges on deload weeks and
hides `LP` before week 3.

---

## Layout notes

Three WebView settings keep the page at the right scale, and one keeps the data:

| Setting | Why |
|---|---|
| `setUseWideViewPort(false)` | Stops the WebView pretending it's a 980px desktop screen |
| `setLoadWithOverviewMode(false)` | Stops it zooming out to fit that fake width |
| `setTextZoom(100)` | Ignores the system font-size slider, which otherwise breaks the row layout |
| `setDomStorageEnabled(true)` | **The log lives in localStorage.** Never remove this one |

`android:windowSoftInputMode="adjustResize"` is what stops the keyboard covering
the set you are typing into.
