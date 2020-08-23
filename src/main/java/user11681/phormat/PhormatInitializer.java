package user11681.phormat;

import com.chocohead.mm.api.ClassTinkerers;
import com.chocohead.mm.api.EnumAdder;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.Reference2CharOpenHashMap;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;

public class PhormatInitializer implements Runnable {
    static final Reference2CharOpenHashMap<Phormatting> formatToCode = new Reference2CharOpenHashMap<>();
    static final ObjectOpenHashSet<String> names = new ObjectOpenHashSet<>();

    static boolean initializing;
    static EnumAdder colorAdder;
    static EnumAdder modifierAdder;
    static EnumAdder primaryAdder;

    @Override
    public void run() {
        initializing = true;

        for (final EntrypointContainer<Runnable> entrypoint : FabricLoader.getInstance().getEntrypointContainers("extendedformatting", Runnable.class)) {
            entrypoint.getEntrypoint().run();
        }

        initializing = false;

        colorAdder.build();
        modifierAdder.build();
        primaryAdder.build();
        colorAdder = null;
        modifierAdder = null;
        primaryAdder = null;
    }

    static {
        final String formattingClass = FabricLoader.getInstance().getMappingResolver().mapClassName("intermediary", "net.minecraft.class_124");

        PhormatInitializer.colorAdder = ClassTinkerers.enumBuilder(formattingClass, String.class, char.class, int.class, Integer.class);
        PhormatInitializer.modifierAdder = ClassTinkerers.enumBuilder(formattingClass, String.class, char.class, boolean.class);
        PhormatInitializer.primaryAdder = ClassTinkerers.enumBuilder(formattingClass, String.class, char.class, boolean.class, int.class, Integer.class);
    }
}
