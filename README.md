# Confluence Lootr

让汇流来世（Confluence）的原生箱子兼容 Lootr，同时保留汇流来世的箱子外观。本分支面向 Minecraft 1.20.1 + Forge；1.21.1 + NeoForge 版本位于 `main` 分支。

## 功能

- 只有带战利品表的汇流来世箱子会转换为 Lootr 箱子。
- 玩家手动放置、没有战利品表的箱子仍是普通箱子。
- 未打开的箱子显示 Lootr 粒子效果。
- 打开时使用原生开盖动画，关闭界面后正常关盖。
- 挖掘时显示 Lootr 风格提示，只有潜行挖掘才能真正破坏箱子。
- 支持 Minecraft 1.20.1 + Forge。

## 构建

```powershell
.\gradlew.bat build
```

构建产物位于 `build/libs/`。

## 依赖

- [汇流来世（Confluence: Otherworld）1.2.6 或更新的 Forge 1.20.1 版](https://www.curseforge.com/minecraft/mc-mods/confluence/files/8940876)
- [Lootr 0.7.35.94 或更新的 Forge 1.20.1 版](https://www.curseforge.com/minecraft/mc-mods/lootr/files/7263076)
- [MesdagPortLib 1.2.5 或更新版本](https://www.curseforge.com/minecraft/mc-mods/mesdagportlib/files/8940829)（汇流来世前置）
- [Curios 5.14.1+1.20.1](https://www.curseforge.com/minecraft/mc-mods/curios/files/6418456)（汇流来世前置）
- [GeckoLib 4.8.4 或更新版本](https://www.curseforge.com/minecraft/mc-mods/geckolib/files/8285794)（汇流来世前置）

正常安装游戏时需要安装上述依赖模组；Gradle 开发环境会自动解析这些依赖，但不会将其打包进本模组。

## 许可证

本项目源代码使用 MIT License，详见 [LICENSE](LICENSE)。第三方依赖的许可证说明见 [THIRD_PARTY_NOTICES.md](THIRD_PARTY_NOTICES.md)。
