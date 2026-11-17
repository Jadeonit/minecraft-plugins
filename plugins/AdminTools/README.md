# AdminTools

До монорепозитория жил локально; впервые опубликован в [Jadeonit/minecraft-plugins](https://github.com/Jadeonit/minecraft-plugins).

Набор административных команд для **Paper / Leaf 1.21.11**.

## Команды

```
/at spawn <сущность> <кол-во> <игрок> <now|item>
/at reload
```

| Аргумент | Значения |
|---|---|
| `сущность` | `tnt`, `arrow`, `warden`, `zombie`, `creeper`, `falling_block`, ... |
| `кол-во` | число или `unlimited` |
| `игрок` | ник, `me` / `я` |
| `режим` | `now` / `сразу` — выполнить сейчас; `item` / `предмет` — выдать активатор |

Примеры:

```
/at spawn tnt 100 Steve now
/at spawn warden 300 Steve item
/at spawn arrow unlimited Alex item
/at spawn creeper 50 me now
```

## Возможности

- Любая спавнящаяся сущность.
- Любое количество, включая `unlimited`.
- Запуск сразу или заранее настроенным предметом.
- Мобы опускаются плавно и не получают урон от падения.
- Автоудаление созданных сущностей через настраиваемое время.
- Кулдаун, лимиты и права.

## Предмет

По умолчанию предмет выглядит как обычный: без имени и описания.
Параметры хранятся в `PersistentDataContainer`.

```yaml
item:
  name: ""
  lore: false
```

## Сборка

Нужен **JDK 21** и **Maven**.

```bash
mvn clean package
```

Готовый файл: `target/AdminTools.jar`.

Если Maven/JDK нет в PATH, они лежат локально:

```powershell
$env:JAVA_HOME="$env:USERPROFILE\.tools\jdk-21.0.13+9"
$env:Path="$env:JAVA_HOME\bin;$env:USERPROFILE\.tools\apache-maven-3.9.9\bin;$env:Path"
mvn -f plugins/AdminTools/pom.xml clean package
```

## Установка

1. Положите `AdminTools.jar` в папку `plugins/`.
2. Перезапустите сервер.
3. Конфиг: `plugins/AdminTools/config.yml`.

## Права

| Право | По умолчанию | Описание |
|---|---|---|
| `admintools.use` | op | Доступ к `/at` |
| `admintools.spawn` | op | Выдача активатора |
| `admintools.unlimited` | op | Режим `unlimited` |
| `admintools.bypasscap` | op | Игнорировать лимит за запуск |
| `admintools.bypasscooldown` | op | Игнорировать кулдаун |
| `admintools.reload` | op | `/at reload` |

## Конфиг

- `item.name` — имя предмета (`""` = стандартное).
- `item.lore` — показывать параметры в описании.
- `messages.prefix` — префикс сообщений (`""` = без префикса).
- `defaults.projectile`, `defaults.count` — значения по умолчанию.
- `spawn.height`, `spawn.radius`, `spawn.velocity` — параметры появления.
- `spawn.per-tick` — сколько сущностей создавать за тик.
- `spawn.tnt-fuse-extra` — дополнительные тики к фитилю TNT.
- `spawn.disable-ai` — отключить AI у мобов.
- `mobs.slow-falling` — плавное падение мобов.
- `mobs.slow-falling-seconds` — длительность эффекта.
- `mobs.fall-speed` — начальная скорость падения мобов.
- `safety.max-entities-per-run` — лимит за один запуск (`-1` = без лимита).
- `safety.unlimited-cap` — количество для `unlimited` (`-1` = без лимита).
- `safety.cleanup-seconds` — автоудаление сущностей (`0` = не удалять).
- `safety.blocked-types` — запрещённые типы сущностей.
- `cooldown-seconds` — кулдаун между запусками.
- `effects.launch-sound` — звук запуска.

## AI disclosure

Проект создан с помощью ИИ и проверен вручную на Paper 1.21.11.

## Лицензия

MIT.
