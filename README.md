# Faster Minecarts

服务器自用 mod，用于**有选择地加速矿车**，同时避免破坏依赖矿车机制的红石设备（如全物品）。具体来说，模组可以选择性地为部分矿车开启**实验性功能**，并且使其拥有独立的最大速度。使用方法：在铁路旁放置一个告示牌（任意种类，包括悬挂告示牌），并写上以下文本：
```
*NewSpeed*
30
```
任何经过告示牌旁边的**载有乘客(玩家或生物)的矿车**将切换至实验性机制，并以 30m/s 为最大速度。可以在铁路沿线放置多个告示牌，为不同路段设置不同的限速。其他矿车完全不受影响，保留原有的运动机制。

矿车会检查周围 **3×4×3 区域**（水平及上方 1 格，下方 2 格）内的告示牌，可以将告示牌隐藏在地面下方。

模组基于 Fabric，且必须在服务端和客户端同时安装。模组可能导致一定的性能损失，请自行取舍。

---

A Minecraft mod that **selectively speeds up minecarts** without interfering with redstone contraptions like all-items sorters that rely on standard minecart mechanics.

This mod introduces **experimental features** to certain minecarts, allowing them to operate with an independent maximum speed. To use it, place a sign (any type, including hanging signs) near your rails and write the following text on it:
```
*NewSpeed*
30
```
Any **occupied minecart** (carrying a passenger or entity) passing near this sign will be upgraded to use the experimental mechanics, with its maximum speed set to 30 blocks per second. You can place multiple signs along your railway to define different speed limits for different sections. Unoccupied minecarts and other cart types remain completely unaffected.

> **Note:** A minecart checks for signs within a **3×4×3 area** around it (1 block horizontally and above, 2 blocks below). This allows you to hide signs beneath the rails if desired.

**Requires Fabric.** This mod must be installed on **both the server and the client** to function.

> **Performance Note:** Since occupied minecarts check for signs every game tick, there might be a performance impact in areas with a very large number of such carts.
