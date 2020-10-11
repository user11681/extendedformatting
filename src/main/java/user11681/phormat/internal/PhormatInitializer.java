package user11681.phormat.internal;

import com.chocohead.mm.api.ClassTinkerers;
import com.chocohead.mm.api.EnumAdder;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.Reference2CharOpenHashMap;
import org.jetbrains.annotations.ApiStatus.Internal;
import user11681.dynamicentry.DynamicEntry;
import user11681.fabricasmtools.Mapper;
import user11681.phormat.Phormatting;

@Internal
public class PhormatInitializer implements Runnable {
    public static Reference2CharOpenHashMap<Phormatting> formatToCode = new Reference2CharOpenHashMap<>();
    public static ObjectOpenHashSet<String> names = new ObjectOpenHashSet<>();
    public static EnumAdder colorAdder;
    public static EnumAdder modifierAdder;
    public static EnumAdder primaryAdder;
    public static boolean initializing;

    @Override
    public void run() {
        initializing = true;

        DynamicEntry.maybeExecute("phormat:init", Runnable.class, Runnable::run);

        initializing = false;

        colorAdder.build();
        modifierAdder.build();
        primaryAdder.build();

        names = null;
        colorAdder = null;
        modifierAdder = null;
        primaryAdder = null;
    }

    static {
        final String formattingClass = Mapper.klass(124);

        PhormatInitializer.colorAdder = ClassTinkerers.enumBuilder(formattingClass, String.class, char.class, int.class, Integer.class);
        PhormatInitializer.modifierAdder = ClassTinkerers.enumBuilder(formattingClass, String.class, char.class, boolean.class);
        PhormatInitializer.primaryAdder = ClassTinkerers.enumBuilder(formattingClass, String.class, char.class, boolean.class, int.class, Integer.class);
    }
}
