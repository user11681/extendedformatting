package user11681.phormat.asm;

import java.util.List;
import java.util.Set;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

public class PhormatMixinConfigPlugin implements IMixinConfigPlugin {
    @Override
    public void onLoad(final String mixinPackage) {}

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(final String targetClassName, final String mixinClassName) {
        return true;
    }

    @Override
    public void acceptTargets(final Set<String> myTargets, final Set<String> otherTargets) {}

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(final String targetClassName, final ClassNode targetClass, final String mixinClassName, final IMixinInfo mixinInfo) {
        if (mixinClassName.endsWith("StyleMixin")) {
            final MethodNode[] methods = targetClass.methods.toArray(new MethodNode[0]);
            String name;
            AbstractInsnNode instruction;
            MethodInsnNode methodInstruction;

            for (final MethodNode method : methods) {
                name = method.name;

                if (name.endsWith("27705") || name.endsWith("27706") || name.endsWith("27707") || name.endsWith("Formatting") && name.startsWith("with")) {
                    instruction = method.instructions.getFirst();

                    while (instruction != null) {
                        if (instruction.getOpcode() == Opcodes.INVOKEVIRTUAL && (methodInstruction = (MethodInsnNode) instruction).name.equals("ordinal")) {
                            method.instructions.set(instruction, new MethodInsnNode(Opcodes.INVOKESTATIC, targetClassName.replace('.', '/'), "phormat_hackOrdinal", "(L" + methodInstruction.owner + ";)I"));

                            break;
                        }

                        instruction = instruction.getNext();
                    }
                }
            }
        }
    }

    @Override
    public void postApply(final String targetClassName, final ClassNode targetClass, final String mixinClassName, final IMixinInfo mixinInfo) {}
}
