package com.demonicrous.furusato.ui.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Project;

/** Draws the title cubemap directly, without vanilla's destructive blur passes. */
public final class PanoramaRenderer {
    private static final ResourceLocation[] FACES = new ResourceLocation[6];

    static {
        for (int i = 0; i < FACES.length; i++) {
            FACES[i] = new ResourceLocation("minecraft",
                    "textures/gui/title/background/panorama_" + i + ".png");
        }
    }

    private PanoramaRenderer() {
    }

    public static void draw(Minecraft minecraft) {
        float aspect = (float) minecraft.displayWidth / (float) minecraft.displayHeight;
        float yaw = (System.currentTimeMillis() % 240000L) * 0.0015F;

        GlStateManager.disableAlpha();
        GlStateManager.disableCull();
        GlStateManager.depthMask(false);
        GlStateManager.matrixMode(GL11.GL_PROJECTION);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        Project.gluPerspective(100.0F, aspect, 0.05F, 10.0F);
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(-8.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(yaw, 0.0F, 1.0F, 0.0F);

        for (int face = 0; face < FACES.length; face++) {
            GlStateManager.pushMatrix();
            orientFace(face);
            minecraft.getTextureManager().bindTexture(FACES[face]);
            drawFace();
            GlStateManager.popMatrix();
        }

        GlStateManager.popMatrix();
        GlStateManager.matrixMode(GL11.GL_PROJECTION);
        GlStateManager.popMatrix();
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        GlStateManager.depthMask(true);
        GlStateManager.enableCull();
        GlStateManager.enableAlpha();
        minecraft.entityRenderer.setupOverlayRendering();
    }

    private static void orientFace(int face) {
        switch (face) {
            case 1: GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F); break;
            case 2: GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F); break;
            case 3: GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F); break;
            case 4: GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F); break;
            case 5: GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F); break;
            default: break;
        }
    }

    private static void drawFace() {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
        buffer.pos(-1.0D, -1.0D, 1.0D).tex(0.0D, 0.0D).color(255, 255, 255, 255).endVertex();
        buffer.pos(1.0D, -1.0D, 1.0D).tex(1.0D, 0.0D).color(255, 255, 255, 255).endVertex();
        buffer.pos(1.0D, 1.0D, 1.0D).tex(1.0D, 1.0D).color(255, 255, 255, 255).endVertex();
        buffer.pos(-1.0D, 1.0D, 1.0D).tex(0.0D, 1.0D).color(255, 255, 255, 255).endVertex();
        tessellator.draw();
    }
}
