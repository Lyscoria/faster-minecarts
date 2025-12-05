# Faster Minecarts

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

---

服务器自用 mod，用于**有选择地加速矿车**，同时避免破坏依赖标准矿车机制的红石设备（如全物品等储电装置）。

具体来说，此模组为特定的矿车开启了**实验性功能**，并且使其拥有独立的最大速度。使用方法：在铁路旁放置一个告示牌（任意种类，包括悬挂告示牌），并写上以下文本：
```
*NewSpeed*
30
```
任何经过告示牌旁边的**载客矿车**（载有乘客或生物）将被切换为使用实验性机制的矿车，以 30 m/s 为最大速度。可以在铁路沿线放置多个告示牌，为不同路段设置不同的限速。空矿车及其他类型的矿车完全不受影响。

> **注意：** 矿车会检查周围 **3×4×3 区域**（水平及上方 1 格，下方 2 格）内的告示牌。因此你可以将告示牌隐藏在地面下方。

**基于 Fabric。** 此模组必须**在服务端和客户端同时安装**才能生效。

> **性能提示：** 由于载客矿车每游戏刻都会检查告示牌，在存在大量此类矿车的区域可能会对性能产生影响。
