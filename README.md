# FoxLocale
## 功能簡介
1.用法與 TaiwanifyEveryItem 相差無異，提供了更多功能的 API。  
2.自動搜尋玩家半徑10區塊內的「乾草堆」「莓果」「狐狸實體」

## 用法
`%locale_物品名稱%` 會將物品名稱轉為玩家設置的語言的對應名稱，如 ENDER_PERAL 轉換為 `終界珍珠`。

## API
如要使用 API 請參考以下類
```java
public abstract class LocaleApi {
    private static LocaleApi instance;

    public abstract @Nullable String matchName(Object object);
    public abstract @Nullable String matchName(Object object, Player player);
    public abstract @Nullable String matchName(Object object, Locale locale);

    public abstract @Nullable String getMaterialName(Material material);
    public abstract @Nullable String getMaterialName(Material material, Player player);
    public abstract @Nullable String getMaterialName(Material material, Locale locale);

    public abstract @Nullable String getBlockName(Block block);
    public abstract @Nullable String getBlockName(Block block, Player player);
    public abstract @Nullable String getBlockName(Block block, Locale locale);
    public abstract @Nullable String getBlockName(Material block);
    public abstract @Nullable String getBlockName(Material block, Player player);
    public abstract @Nullable String getBlockName(Material block, Locale locale);

    public abstract @Nullable String getEntityName(Entity entity);
    public abstract @Nullable String getEntityName(Entity entity, Player player);
    public abstract @Nullable String getEntityName(Entity entity, Locale locale);
    public abstract @Nullable String getEntityName(EntityType entity);
    public abstract @Nullable String getEntityName(EntityType entity, Player player);
    public abstract @Nullable String getEntityName(EntityType entity, Locale locale);

    public static LocaleApi getInstance() {
        return instance;
    }
}
```

## License

本專案於 LGPL 3.0 條款釋出。
