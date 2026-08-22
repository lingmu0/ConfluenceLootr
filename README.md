# Confluence Lootr

让汇流来世（Confluence）的原生箱子兼容 Lootr，同时保留汇流来世的箱子外观。

## 功能

- 只有带战利品表的汇流来世箱子会转换为 Lootr 箱子。
- 玩家手动放置、没有战利品表的箱子仍是普通箱子。
- 未打开的箱子显示 Lootr 粒子效果。
- 打开时使用原生开盖动画，关闭界面后正常关盖。
- 挖掘时显示 Lootr 风格提示，只有潜行挖掘才能真正破坏箱子。
- 支持 Minecraft 1.21.1 + NeoForge。

## 构建

```powershell
.\gradlew.bat build
```

开发客户端：

```powershell
.\gradlew.bat runClient
```

构建产物位于 `build/libs/`。

## 依赖

- 汇流来世（Confluence）
- Lootr

运行时需要单独安装上述依赖模组。

## 许可证

本项目源代码使用 MIT License，详见 [LICENSE](LICENSE)。第三方依赖的许可证说明见 [THIRD_PARTY_NOTICES.md](THIRD_PARTY_NOTICES.md)。
