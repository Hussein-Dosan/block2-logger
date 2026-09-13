# Block 2 Logger — Android app

A set-by-set logger for the Block 2 classic split. One full-screen WebView over a
self-contained HTML app in `app/src/main/assets/index.html`.

**No permissions, no network, no account.** Everything you log is stored in the
WebView's `localStorage` on the phone.

---

## Build it — pick one

### Option A · GitHub Actions (no software to install)

If you'd rather not install Android Studio:

1. Create a new **private** repository on GitHub.
2. Upload this whole folder to it (drag and drop works — keep the folder structure).
3. Open the **Actions** tab. The `Build APK` workflow runs automatically on push;
   if it doesn't, click it and press **Run workflow**.
4. When it finishes (3–5 minutes), open the run and download the
   **Block2Logger-apk** artifact. `app-release.apk` is inside the zip.
5. Move the APK to your phone and tap it. Android will ask you to allow installs
   from that source once.

### Option B · Android Studio

1. Install Android Studio, then **File → Open** and select this folder.
2. Let it sync. It will offer to download the Android SDK and generate the Gradle
   wrapper — accept both.
3. Plug your phone in with USB debugging on, and press **Run** (▶).
   Or **Build → Build Bundle(s) / APK(s) → Build APK(s)** to get a file.

The APK lands at `app/build/outputs/apk/release/app-release.apk`.

---

## Signing

Release builds are signed with the **debug key** (see `app/build.gradle.kts`), so
the APK installs with no keystore for you to manage. Two consequences worth knowing:

- The debug key is generated per machine. If you build on your PC now and rebuild
  in GitHub Actions later, the signatures won't match and Android will refuse the
  update — uninstall the old one first.
- It is fine for personal sideloading, and not suitable for the Play Store.

For a stable key of your own, generate a keystore and replace the `signingConfig`
line with a proper `signingConfigs.create("release") { ... }` block.

---

## Updating the program

The training program is plain data at the top of the `<script>` in
`app/src/main/assets/index.html`:

```js
const BLOCK_ID = "block2";
const WEEK_NOTES = [ ... ];
const DAYS = [ ... ];
```

To load a new block, edit `DAYS` and `WEEK_NOTES`, then **change `BLOCK_ID`**
(for example to `"block3"`). Logs are stored per block id, so the old block's
history is kept rather than overwritten. Bump `versionCode` and `versionName`
in `app/build.gradle.kts`, rebuild, and install over the top.

---

## Layout notes

Three settings in `MainActivity.java` are what keep the page at the right scale —
the same problem that made the standalone file look zoomed out in a browser:

| Setting | Why |
|---|---|
| `setUseWideViewPort(false)` | Stops the WebView pretending it's a 980px desktop screen |
| `setLoadWithOverviewMode(false)` | Stops it zooming out to fit that fake width |
| `setTextZoom(100)` | Ignores the system font-size slider, which otherwise breaks the row layout |

And `setDomStorageEnabled(true)` is what makes the log persist at all. If you ever
strip settings out of this file, keep that one.

`android:windowSoftInputMode="adjustResize"` is what stops the keyboard covering
the set you're typing into.
