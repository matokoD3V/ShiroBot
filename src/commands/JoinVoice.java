package commands;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.managers.AudioManager;

public class JoinVoice extends Command {
    public JoinVoice() {
        this.name = "join";
        this.help = "makes bot join current voice channel";
    }

    @Override
    protected void execute(CommandEvent event) {
        String target = "";
        String msg = event.getMessage().getContent();
        Member member = event.getMember();
        VoiceChannel[] VChannels = event.getGuild().getVoiceChannels().toArray(new VoiceChannel[0]);

        if(msg.length() == 6)
            connectTo(member.getVoiceState().getChannel());
        if(msg.length() > 6) {
            target = msg.substring(7, msg.length());
            for (int i = 0; i < VChannels.length; i++) {
                if (VChannels[i].getName().toLowerCase().contains(target.toLowerCase())){
                    connectTo(VChannels[i]);
                    i = VChannels.length;
                }
            }
        }
    }

    private void connectTo(VoiceChannel channel) {
        AudioManager manager = channel.getGuild().getAudioManager();
        manager.openAudioConnection(channel);
    }
}
