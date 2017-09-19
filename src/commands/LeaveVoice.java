package commands;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.managers.AudioManager;
import utils.Constants;

public class LeaveVoice extends Command {
    public LeaveVoice() {
        this.name = "leave";
        this.help = "makes bot leave current voice channel";
    }

    @Override
    protected void execute(CommandEvent event) {
        String target = "";
        String msg = event.getMessage().getContent();
        Member[] members = event.getGuild().getMembers().toArray(new Member[0]);
        VoiceChannel[] VChannels = event.getGuild().getVoiceChannels().toArray(new VoiceChannel[0]);

        for(int i = 0; i < members.length; i++){
            if(members[i].getUser().getId().equals(Constants.BOT_ID)) {
                disconnectFrom(members[i].getVoiceState().getChannel());
                i = members.length;
            }
        }

    }

    private void disconnectFrom(VoiceChannel channel) {
        AudioManager manager = channel.getGuild().getAudioManager();
        manager.closeAudioConnection();
    }
}
