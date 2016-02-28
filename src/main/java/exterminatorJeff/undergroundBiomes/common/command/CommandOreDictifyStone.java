package exterminatorJeff.undergroundBiomes.common.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import exterminatorJeff.undergroundBiomes.common.UndergroundBiomes;

public class CommandOreDictifyStone extends CommandBase {
	
	public CommandOreDictifyStone() {
	}
	
	@Override
	public int compareTo(Object compared) {
		return this.toString().compareTo(compared.toString());
	}
	
	@Override
	public String getCommandName() {
		return "oredictifystone";
	}
	
	@Override
	public String getCommandUsage(ICommandSender par1ICommandSender) {
		return "/" + this.getCommandName();
	}
	
	@Override
	public void processCommand(ICommandSender sender, String as[]) {
		try {
			int num = UndergroundBiomes.oreDictifyStone();
			sender.addChatMessage(new ChatComponentText("" + EnumChatFormatting.GREEN).appendText(StatCollector.translateToLocalFormatted("commands.oredictifystone.ok", num)));
		} catch (Exception exception) {
			sender.addChatMessage(new ChatComponentText("" + EnumChatFormatting.GREEN).appendText(StatCollector.translateToLocalFormatted("commands.oredictifystone.fail")));
			exception.printStackTrace();
		}
	}
}
