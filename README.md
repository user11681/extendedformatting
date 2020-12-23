Phormat is a Fabric-based library that facilitates text formatting.

### include

`build.gradle`:

```groovy
repositories {
    // . . .
    maven {url = "https://dl.bintray.com/user11681/maven"}
}

dependencies {
    // . . .
    modApi(include("user11681:phormat:+")) // or choose a version from
    // https://dl.bintray.com/user11681/maven/user11681/phormat
}
```

### use

```java
public class Example {
    // will change color based on previous color and PRNG
    public static final ExtendedFormatting colors = FormattingRegistry.register("COLORS", 'u', 16, null)
        .color(previousColor -> previousColor + Random.nextInt(1 << 24));
    // constant purple
    public static final ExtendedFormatting purple = FormattingRegistry.register("PURPLE", 'v', 17, 0xD083FF);
    // custom rendering on text
    public static final ExtendedFormatting customFormatting = FormattingRegistry.register("THING", 'x', true).formatter((
        TextRendererDrawerAccess drawer,
        Style style,
        int charIndex,
        int character,
        FontStorage storage,
        Glyph glyph,
        GlyphRenderer glyphRenderer,
        float red,
        float green,
        float blue,
        float alpha,
        float advance) -> {
        float x = drawer.x(); // horizontal displacement of the left of the character
        // from the center of the text
        float y = drawer.y(); // vertical displacement of the top of the character
        // from the center of the text

        // will draw a rectangle covering bottom half of the height and full width of the character
        drawer.invokeAddRectangle(new GlyphRenderer.Rectangle(x, -y, x + advance, 0, 0.01F, red, green, blue, alpha));
    });
}
```
