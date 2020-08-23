package user11681.extendedformatting;

import com.chocohead.mm.api.ClassTinkerers;
import com.chocohead.mm.api.EnumAdder;
import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import net.minecraft.util.Formatting;

public class ExtendedFormatting implements Runnable {
    public static final String ID = "extendedformatting";

    static final ReferenceArrayList<Format> formats = ReferenceArrayList.wrap(new Format[10], 0);

    static boolean initializing;
    static EnumAdder colorAdder;
    static EnumAdder modifierAdder;
    static EnumAdder primaryAdder;

    public void run() {
        initializing = true;

        for (final EntrypointContainer<Runnable> entrypoint : FabricLoader.getInstance().getEntrypointContainers(ID, Runnable.class)) {
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

        ExtendedFormatting.colorAdder = ClassTinkerers.enumBuilder(formattingClass, String.class, char.class, int.class, Integer.class);
        ExtendedFormatting.modifierAdder = ClassTinkerers.enumBuilder(formattingClass, String.class, char.class, boolean.class);
        ExtendedFormatting.primaryAdder = ClassTinkerers.enumBuilder(formattingClass, String.class, char.class, boolean.class, int.class, Integer.class);
    }

    public static class Postinit implements PreLaunchEntrypoint {
        public void onPreLaunch() {
            for (final Format format : ExtendedFormatting.formats) {
                format.formatting = ClassTinkerers.getEnum(Formatting.class, format.name);
            }
        }
    }
}
