package cn.ksmcbrigade.csms;

import cn.ksmcbrigade.csms.config.Config;
import cn.ksmcbrigade.csms.config.Mode;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.File;
import java.io.IOException;

@Mod(CustomSpyglassMagnificationScreen.MOD_ID)
public class CustomSpyglassMagnificationScreen {

    public static final String MOD_ID = "csms";

    public static File configFile = new File("config/csms-config.json");
    public static Config config;

    static {
        try {
            config = new Config(configFile);
        } catch (IOException e) {
            e.printStackTrace();
            config = new Config(configFile,Mode.VANILLA);
        }
    }

    public CustomSpyglassMagnificationScreen() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void command(RegisterClientCommandsEvent event){
        event.getDispatcher().register(Commands.literal("csms-config").executes(context -> {
            context.getSource().sendSystemMessage(Component.literal("Mode: "+config.mode.name()+" ("+config.mode.index+")"));
            return 0;
        }).then(Commands.argument("mode", IntegerArgumentType.integer(0,3)).executes(context -> {
            config.mode = Mode.valueOf(IntegerArgumentType.getInteger(context,"mode"));
            context.getSource().sendSystemMessage(CommonComponents.GUI_DONE);
            try {
                config.save();
                return 0;
            } catch (IOException e) {
                e.printStackTrace();
                return 1;
            }
        })));
    }
}
