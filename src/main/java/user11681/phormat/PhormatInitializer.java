package user11681.phormat;

import com.chocohead.mm.api.ClassTinkerers;
import com.chocohead.mm.api.EnumAdder;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.Reference2CharOpenHashMap;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.CustomValue;
import user11681.dynamicentry.DynamicEntry;

public class PhormatInitializer implements Runnable {
    static Reference2CharOpenHashMap<Phormatting> formatToCode = new Reference2CharOpenHashMap<>();
    static ObjectOpenHashSet<String> names = new ObjectOpenHashSet<>();
    static EnumAdder colorAdder;
    static EnumAdder modifierAdder;
    static EnumAdder primaryAdder;
    static boolean initializing;

    @Override
    public void run() {
        initializing = true;

        DynamicEntry.executeOptionalEntrypoint("phormat:init", Runnable.class, Runnable::run);

        initializing = false;

        colorAdder.build();
        modifierAdder.build();
        primaryAdder.build();
        names = null;
        colorAdder = null;
        modifierAdder = null;
        primaryAdder = null;
    }

    private static boolean tryLoadClass(final ModContainer mod, final CustomValue value) {
        if (value.getType() == CustomValue.CvType.STRING) {
            try {
                Class.forName(value.getAsString(), true, PhormatInitializer.class.getClassLoader());
            } catch (final ClassNotFoundException exception) {
                throw new IllegalArgumentException(String.format("class %s specified in the Fabric configuration file of mod %s does not exist", value.getAsString(), mod.getMetadata().getName()));
            }

            return false;
        }

        return true;
    }

    static {
        final String formattingClass = FabricLoader.getInstance().getMappingResolver().mapClassName("intermediary", "net.minecraft.class_124");

        PhormatInitializer.colorAdder = ClassTinkerers.enumBuilder(formattingClass, String.class, char.class, int.class, Integer.class);
        PhormatInitializer.modifierAdder = ClassTinkerers.enumBuilder(formattingClass, String.class, char.class, boolean.class);
        PhormatInitializer.primaryAdder = ClassTinkerers.enumBuilder(formattingClass, String.class, char.class, boolean.class, int.class, Integer.class);
    }
}
