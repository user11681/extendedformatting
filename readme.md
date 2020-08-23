Phormat is a library that allows Fabric mod developers to easily add custom formatting to Minecraft.

### include
`build.gradle`:
```groovy
repositories {
    . . .
    maven {
        url "https://jitpack.io"
    }
}
. . .
dependencies {
    . . .
    modApi include("com.github.user11681:phormat:1.16-SNAPSHOT")
}
```

### use
`fabric.mod.json`:
```json
"entrypoints": {
    "phormat": [
        "com.example.Example"
    ]
}
```
`com/example/Example.java`:
```java
public class Example implements Runnable {
    

    @Override
    public void run() {}

