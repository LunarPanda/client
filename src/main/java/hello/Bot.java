package hello;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;


import com.google.code.chatterbotapi.ChatterBot;
import com.google.code.chatterbotapi.ChatterBotFactory;
import com.google.code.chatterbotapi.ChatterBotSession;
import com.google.code.chatterbotapi.ChatterBotType;

@Component
@SessionScope
public class Bot {
	
	ChatterBotSession botsession;
	
	@Value("${spring.application.name}")
	private String appName;
	
	
	public Bot(){
		ChatterBotFactory factory = new ChatterBotFactory();
        ChatterBot bot=null;
		try {
			bot = factory.create(ChatterBotType.CLEVERBOT);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        botsession = bot.createSession();
	}
	
	String reply(String s){
		String r="";
		try {
			r=botsession.think(s);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println();
		return String.format("{\"content\":\"[%s] %s\"}",appName,r.replaceAll("\"", "\\\\\""));
	}
	
}
