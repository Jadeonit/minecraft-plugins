# Minecraft Plugins

[English](README.md) · **Русский**

Небольшой набор плагинов для **Paper / Leaf 1.21.x** на Java 21 и Maven.
Раньше они лежали в отдельных репозиториях; теперь это монорепозиторий —
единая точка входа для всего набора.

> Хобби-проект для своего Minecraft-сервера. Перед публичным сервером
> проверяй на тестовом.

## Плагины

| Плагин | Команда | Что делает | Jar |
|---|---|---|---|
| **Egg** (`OrbitalCannon`) | `/egg` | Спавнит снаряды и мобов в настраиваемых режимах (`orbital`, `megaorbital`, `stab`, `wither`), с кулдаунами и лимитами. | `Egg.jar` |
| **AnomalousWeather** | `/anomaly` | Случайная аномальная погода: босс-бар, зелья, защитные предметы и крафты. | `anomalous-weather-1.0.0.jar` |
| **PortalBlock** | `/portalblock` | Блокирует проход через порталы Нижнего мира; настраиваемое сообщение, кулдаун и выталкивание. | `PortalBlock.jar` |
| **FirstJoinClass** | `/firstjoinclass` | Автоматически вызывает `/class` при первом входе игрока. | `FirstJoinClass.jar` |
| **AdminTools** | `/at` | Админ-спавн сущностей: сразу или предметом, с лимитами и кулдаунами. | `AdminTools.jar` |

У каждого плагина свой README, конфиг и права в `plugins/<name>/`.

## Требования

- JDK 21
- Maven 3.9+
- Paper или Leaf 1.21.x (плагины собраны под Paper API `1.21.11-R0.1-SNAPSHOT`)

## Сборка

Из корня репозитория:

```bash
mvn -B package
```

Jar-файлы появятся в модулях:

```
plugins/egg-plugin/target/Egg.jar
plugins/AnomalousWeather/target/anomalous-weather-1.0.0.jar
plugins/PortalBlock/target/PortalBlock.jar
plugins/FirstJoinClass/target/FirstJoinClass.jar
plugins/AdminTools/target/AdminTools.jar
```

Собрать только один плагин:

```bash
mvn -f plugins/PortalBlock/pom.xml package
```

Если Java или Maven нет в `PATH`, сначала укажи локальный toolchain:

```powershell
$env:JAVA_HOME="$env:USERPROFILE\.tools\jdk-21.0.13+9"
$env:Path="$env:JAVA_HOME\bin;$env:USERPROFILE\.tools\apache-maven-3.9.9\bin;$env:Path"
mvn -B package
```

## Установка

1. Скопируй jar в папку `plugins/` сервера.
2. Перезапусти сервер (или используй менеджер плагинов).
3. При необходимости отредактируй `plugins/<PluginName>/config.yml`.

## Структура репозитория

```
.
├── pom.xml                 агрегатор сборки
├── plugins/
│   ├── egg-plugin/         Egg / OrbitalCannon
│   ├── AnomalousWeather/
│   ├── PortalBlock/
│   ├── FirstJoinClass/
│   └── AdminTools/
└── .github/workflows/      CI-сборка
```

## Зачем монорепозиторий?

Каждый плагин по-прежнему собирается отдельно и хранит свою версию, конфиг и
README. Монорепозиторий добавляет:

- один CI на все плагины;
- один трекер задач и одно место, где видно общие паттерны;
- одну точку входа в портфолио вместо пяти маленьких репозиториев.

## Откуда плагины

Оригинальные репозитории остались публичными. Каждый плагин также ссылается
на свой исток из собственного README.

| Плагин | Оригинальный репозиторий |
|---|---|
| **Egg** (`OrbitalCannon`) | [Jadeonit/egg-plugin](https://github.com/Jadeonit/egg-plugin) |
| **AnomalousWeather** | [Jadeonit/AnomalousWeather](https://github.com/Jadeonit/AnomalousWeather) |
| **PortalBlock** | [Jadeonit/PortalBlock](https://github.com/Jadeonit/PortalBlock) |
| **FirstJoinClass** | [Jadeonit/FirstJoinClass](https://github.com/Jadeonit/FirstJoinClass) |
| **AdminTools** | до монорепозитория жил локально; впервые опубликован здесь |

Связанное: [публичные конфиги KaminiaCraft S3](https://github.com/Jadeonit/KaminiaCraftS3_publicconfig).

## Примечания

- Это небольшие серверные утилиты, а не security-инструменты. Они работают с
  правами сервера, поэтому перед публичным сервером их стоит просмотреть.
- Часть ранних итераций делалась с помощью ИИ; это указано в README отдельных
  плагинов. Новый код всё больше пишется и проверяется вручную.
- В репозитории нет адресов серверов, токенов и данных игроков.

## Лицензия

MIT для всего набора, если у плагина нет собственного `LICENSE`. См.
[LICENSE](LICENSE).

---

English version: [README.md](README.md).
