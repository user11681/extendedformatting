package user11681.extendedformatting;

import com.chocohead.mm.api.ClassTinkerers;
import com.chocohead.mm.api.EnumAdder;
import it.unimi.dsi.fastutil.objects.Reference2CharMap;
import it.unimi.dsi.fastutil.objects.Reference2CharOpenHashMap;
import java.util.regex.Pattern;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import net.minecraft.util.Formatting;
import user11681.extendedformatting.asm.access.CustomFormattingAccess;
import user11681.extendedformatting.asm.mixin.FormattingPatternAccess;

public class ExtendedFormatting implements Runnable {
    public static final String ID = "extendedformatting";

    static final Reference2CharOpenHashMap<Format> formats = new Reference2CharOpenHashMap<>();

    static boolean initializing;
    static EnumAdder colorAdder;
    static EnumAdder modifierAdder;
    static EnumAdder primaryAdder;

    @Override
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

    public static class Postinit implements PreLaunchEntrypoint {
        @Override
        public void onPreLaunch() {
            final String pattern = FormattingPatternAccess.getPattern().toString();

            for (final Reference2CharMap.Entry<Format> entry : ExtendedFormatting.formats.reference2CharEntrySet()) {
                final Format format = entry.getKey();
                final char code = entry.getCharValue();

                format.formatting = ClassTinkerers.getEnum(Formatting.class, format.name);
                ((CustomFormattingAccess) (Object) format.formatting).setFormat(format);

                if (pattern.indexOf(code) >= 0) {
                    throw new IllegalArgumentException(String.format("a formatting with the code %s already exists.", code));
                }

                FormattingPatternAccess.setPattern(Pattern.compile(pattern.replace("]", code + "]")));
            }
        }
    }

    static {
        final String formattingClass = FabricLoader.getInstance().getMappingResolver().mapClassName("intermediary", "net.minecraft.class_124");

        ExtendedFormatting.colorAdder = ClassTinkerers.enumBuilder(formattingClass, String.class, char.class, int.class, Integer.class);
        ExtendedFormatting.modifierAdder = ClassTinkerers.enumBuilder(formattingClass, String.class, char.class, boolean.class);
        ExtendedFormatting.primaryAdder = ClassTinkerers.enumBuilder(formattingClass, String.class, char.class, boolean.class, int.class, Integer.class);
    }
}
