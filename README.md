# Minecraft Plugins

**English** · [Русский](README.ru.md)

A small toolbox of **Paper / Leaf 1.21.x** plugins, written in Java 21 and built
with Maven. They were originally separate repositories; this monorepo is the
single entry point for the whole set.

> Hobby project for a private Minecraft server. Test on a staging server before
> putting anything on a public one.

## Plugins

| Plugin | Command | What it does | Jar |
|---|---|---|---|
| **Egg** (`OrbitalCannon`) | `/egg` | Spawns custom projectiles and mobs with configurable modes (`orbital`, `megaorbital`, `stab`, `wither`), cooldowns and safety caps. | `Egg.jar` |
| **AnomalousWeather** | `/anomaly` | Random anomalous weather with boss bar, potion effects, protection items and crafting recipes. | `anomalous-weather-1.0.0.jar` |
| **PortalBlock** | `/portalblock` | Blocks travel through nether portals with a configurable message, cooldown and push-out. | `PortalBlock.jar` |
| **FirstJoinClass** | `/firstjoinclass` | Runs `/class` automatically when a player joins for the first time. | `FirstJoinClass.jar` |
| **AdminTools** | `/at` | Admin spawn tool: spawn entities immediately or as an item, with caps and cooldowns. | `AdminTools.jar` |

Each plugin has its own README, config and permissions in `plugins/<name>/`.

## Requirements

- JDK 21
- Maven 3.9+
- Paper or Leaf 1.21.x (the plugins target Paper API `1.21.11-R0.1-SNAPSHOT`)

## Build

From the repository root:

```bash
mvn -B package
```

Jars land in each module:

```
plugins/egg-plugin/target/Egg.jar
plugins/AnomalousWeather/target/anomalous-weather-1.0.0.jar
plugins/PortalBlock/target/PortalBlock.jar
plugins/FirstJoinClass/target/FirstJoinClass.jar
plugins/AdminTools/target/AdminTools.jar
```

Build one plugin only:

```bash
mvn -f plugins/PortalBlock/pom.xml package
```

If Java or Maven is not on `PATH`, point to the local toolchain first:

```powershell
$env:JAVA_HOME="$env:USERPROFILE\.tools\jdk-21.0.13+9"
$env:Path="$env:JAVA_HOME\bin;$env:USERPROFILE\.tools\apache-maven-3.9.9\bin;$env:Path"
mvn -B package
```

## Install

1. Copy the jar into the server's `plugins/` folder.
2. Restart the server (or use a plugin manager).
3. Edit `plugins/<PluginName>/config.yml` if needed.

## Repository layout

```
.
├── pom.xml                 aggregator build
├── plugins/
│   ├── egg-plugin/         Egg / OrbitalCannon
│   ├── AnomalousWeather/
│   ├── PortalBlock/
│   ├── FirstJoinClass/
│   └── AdminTools/
└── .github/workflows/      CI build
```

## Why a monorepo?

Each plugin still builds independently and keeps its own version, config and
README. The monorepo adds:

- one CI pipeline for all plugins;
- one issue tracker and one place to compare patterns;
- a single portfolio entry point instead of five tiny repos.

## Origins

The original repositories remain public. Each plugin also links back to its
origin from its own README.

| Plugin | Original repository |
|---|---|
| **Egg** (`OrbitalCannon`) | [Jadeonit/egg-plugin](https://github.com/Jadeonit/egg-plugin) |
| **AnomalousWeather** | [Jadeonit/AnomalousWeather](https://github.com/Jadeonit/AnomalousWeather) |
| **PortalBlock** | [Jadeonit/PortalBlock](https://github.com/Jadeonit/PortalBlock) |
| **FirstJoinClass** | [Jadeonit/FirstJoinClass](https://github.com/Jadeonit/FirstJoinClass) |
| **AdminTools** | local-only before this monorepo; first published here |

Related: [KaminiaCraft S3 public config](https://github.com/Jadeonit/KaminiaCraftS3_publicconfig).

## Notes

- These are small server utilities, not security tools. They run with the
  server's permissions and should be reviewed before use on a public server.
- Some early iterations were built with AI assistance; the individual READMEs
  disclose it where applicable. I am moving toward writing and reviewing new
  code by hand.
- No server addresses, tokens or player data are stored in this repository.

## License

MIT for the collection unless a plugin carries its own `LICENSE` file. See
[LICENSE](LICENSE).

---

Русская версия: [README.ru.md](README.ru.md).
