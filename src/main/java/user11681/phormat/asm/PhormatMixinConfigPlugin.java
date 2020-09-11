package user11681.phormat.asm;

import java.util.List;
import java.util.Set;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;
import org.objectweb.asm.Label;
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
            String name;
            AbstractInsnNode instruction;
            MethodInsnNode methodInstruction;

            for (final MethodNode method : targetClass.methods) {
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
        } else if (mixinClassName.endsWith("TextColorMixin")) {
            final MappingResolver resolver = FabricLoader.getInstance().getMappingResolver();
            final String getRgb = resolver.mapMethodName("intermediary", "class_5251", "method_27716", "()I");

            for (final MethodNode method : targetClass.methods) {
                if (getRgb.equals(method.name)) {
                    final String rgb = resolver.mapFieldName("intermediary", "class_5251", "field_24364", "I");

                    final Label endIf = new Label();

                    method.visitJumpInsn(Opcodes.IFEQ, endIf);
                    method.visitVarInsn(Opcodes.ALOAD, 0);
                    method.visitVarInsn(Opcodes.ALOAD, 0);
                    method.visitFieldInsn(Opcodes.GETFIELD, targetClassName, "phormat_colorFunction", "Luser11681/phormat/ColorFunction;");
                    method.visitFieldInsn(Opcodes.GETFIELD, targetClassName, rgb, "I");
                    method.visitMethodInsn(Opcodes.INVOKEINTERFACE, "user11681/phormat/ColorFunction", "apply", "(I)I", true);
                    method.visitInsn(Opcodes.DUP);
                    method.visitFieldInsn(Opcodes.PUTFIELD, targetClassName, "phormat_previousColor", "I");
                    method.visitInsn(Opcodes.IRETURN);
                    method.visitLabel(endIf);

                    break;
                }
            }
        }
    }

    @Override
    public void postApply(final String targetClassName, final ClassNode targetClass, final String mixinClassName, final IMixinInfo mixinInfo) {}
}
