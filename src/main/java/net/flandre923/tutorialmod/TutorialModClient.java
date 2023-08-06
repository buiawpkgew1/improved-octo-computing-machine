package net.flandre923.tutorialmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.flandre923.tutorialmod.block.ModBlocks;
import net.flandre923.tutorialmod.block.entity.ModBlockEntities;
import net.flandre923.tutorialmod.block.entity.client.GemInfusingStationBlockEntityRenderer;
import net.flandre923.tutorialmod.client.ThirstHudOverlay;
import net.flandre923.tutorialmod.entity.ModEntities;
import net.flandre923.tutorialmod.entity.client.ChomperRenderer;
import net.flandre923.tutorialmod.event.KeyInputHandler;
import net.flandre923.tutorialmod.fluid.ModFluids;
import net.flandre923.tutorialmod.networking.ModMessage;
import net.flandre923.tutorialmod.screen.GemInfusingScreen;
import net.flandre923.tutorialmod.screen.ModScreenHandlers;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.util.Identifier;

public class TutorialModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // 将方块注册到特定渲染层，用于渲染透明和切割效果
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.EGGPLANT_CROP, RenderLayer.getCutout());

        // 注册自定义按键输入处理器，用于客户端输入处理
        KeyInputHandler.register();

        // 注册自定义数据包，用于服务器和客户端之间的通信
        ModMessage.registerC2SPackets();

        // 注册自定义 HUD（头顶显示）叠加层
        HudRenderCallback.EVENT.register(new ThirstHudOverlay());

        // 注册自定义流体渲染处理器用于模组的流体
        FluidRenderHandlerRegistry.INSTANCE.register(ModFluids.STILL_SOAP_WATER, ModFluids.FLOWING_SOAP_WATER,
                new SimpleFluidRenderHandler(
                        new Identifier("minecraft:block/water_still"),
                        new Identifier("minecraft:block/water_flow"),
                        0xA1E038D0
                ));

        // 设置特定方块和流体的自定义渲染层，用于半透明渲染
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(),
                ModFluids.FLOWING_SOAP_WATER, ModFluids.STILL_SOAP_WATER);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RED_MAPLE_LEAVES, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RED_MAPLE_SAPLING, RenderLayer.getCutout());

        // 设置特定方块的自定义渲染层，用于切割渲染
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BUTTERCUPS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_BUTTERCUPS, RenderLayer.getCutout());

        // 注册用于处理特定容器类型的自定义屏幕
        HandledScreens.register(ModScreenHandlers.GEM_INFUSING_SCREEN_HANDLER, GemInfusingScreen::new);

        // 注册用于渲染特定方块实体类型的自定义方块实体渲染器
        BlockEntityRendererFactories.register(ModBlockEntities.GEM_INFUSING_STATION,
                GemInfusingStationBlockEntityRenderer::new);

        // 注册用于渲染特定实体类型的自定义实体渲染器
        EntityRendererRegistry.register(ModEntities.CHOMPER, ChomperRenderer::new);
    }
}

