package ru.danko.lib.tgbot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ReplyKeyboardRemove;
import com.pengrad.telegrambot.request.AnswerCallbackQuery;
import com.pengrad.telegrambot.request.DeleteMessage;
import com.pengrad.telegrambot.request.EditMessageReplyMarkup;
import com.pengrad.telegrambot.request.GetChat;
import com.pengrad.telegrambot.request.GetChatAdministrators;
import com.pengrad.telegrambot.request.GetChatMember;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetChatAdministratorsResponse;
import com.pengrad.telegrambot.response.GetChatMemberResponse;
import com.pengrad.telegrambot.response.GetChatResponse;
import com.pengrad.telegrambot.response.SendResponse;

import ru.danko.lib.tgbot.events.CallbackQueryEvent;
import ru.danko.lib.tgbot.events.ChatMemberEvent;
import ru.danko.lib.tgbot.events.MessageEvnet;
import ru.danko.lib.tgbot.events.postCannelEvent;

public class Bot {
private String token;
private static TelegramBot bot;
public Bot(String token) {
	this.token = token;
	start();
}
public void start() {
	if(token.equals("YOUR_TELEGRAM_TOKEN")) {
		System.out.println("Ошибка, укажите токен бота в файле config.yml");
		return;
	}
	bot = new TelegramBot(token);
	MessageEvnet MessageEvent = new MessageEvnet();
	postCannelEvent ChannelPostEvent = new postCannelEvent();
	CallbackQueryEvent callback = new CallbackQueryEvent();
	ChatMemberEvent chatmembe = new ChatMemberEvent();
		bot.setUpdatesListener(updates -> {
		    for(Update up : updates) {
		    	if(up.message() != null) {
		    	MessageEvent.send(up);
		    	}
		    	if(up.channelPost() != null) {
		    		ChannelPostEvent.send(up);
		    	}
		    	if(up.callbackQuery() != null) {
		    		callback.send(up);
		    	}
		    	if(up.chatMember() != null) {
		    		chatmembe.send(up);
		    	}
		    	
		    }
		    return UpdatesListener.CONFIRMED_UPDATES_ALL;
		});
	
}
public static SendResponse sendMessage(long chatId, String msg, InlineKeyboardMarkup buttons, ReplyKeyboardMarkup board, int replyMsgId) {
	if(msg==null) {
		msg="";
	}
	SendMessage send = new SendMessage(chatId, msg);
	if(board!=null) {
		send.replyMarkup(board);
	}
	if(buttons!=null) {
		send.replyMarkup(buttons);
	}
	if(replyMsgId != 0) {
		send.replyToMessageId(replyMsgId);
	}
	return bot.execute(send);
}
public static BaseResponse editCallBackQuery(Long chatId, int messageId, InlineKeyboardMarkup board) {
	EditMessageReplyMarkup send = new EditMessageReplyMarkup(chatId, messageId);
	
	send.replyMarkup(board);
	return bot.execute(send);
}
public static void sendAnswerCallBackQuery(CallbackQuery callback, boolean alert, String text) {
	AnswerCallbackQuery answer = new AnswerCallbackQuery(callback.id());
	answer.showAlert(alert);
	answer.text(text);
	bot.execute(answer);
}
public static SendResponse replyMessage(long chatId, String msg, int messageId) {
	return bot.execute(new SendMessage(chatId, msg).replyToMessageId(messageId));
}
public static SendResponse sendMessage(long chatId, String msg) {
	SendMessage send =	new SendMessage(chatId, msg);
	return bot.execute(send);
}

public static SendResponse sendMessageButton(long chatId, String msg, InlineKeyboardMarkup board) {
SendMessage send =	new SendMessage(chatId, msg);
send.replyMarkup(board);
	return bot.execute(send);
}

public static SendResponse sendReplyKeyboard(long chatId, String msg,ReplyKeyboardMarkup board) {
	SendMessage send =	new SendMessage(chatId, msg);
	send.replyMarkup(board);
	return bot.execute(send);
}
public static SendResponse removeChatBoard(long chatId, String msg,ReplyKeyboardRemove board) {
	SendMessage send =	new SendMessage(chatId, msg);
	send.replyMarkup(board);
	return bot.execute(send);

}
public static BaseResponse deleteMessage(Object chatId, int msgId) {
	return bot.execute(new DeleteMessage(chatId, msgId));
}
public static GetChatAdministratorsResponse getAdministrators(Object chatId) {
	return bot.execute(new GetChatAdministrators(chatId));
}
public static GetChatResponse getChat(Object chatId) {
	return bot.execute(new GetChat(chatId));
}
public static GetChatMemberResponse getChatMembers(Object chatId, long userId) {
	GetChatMemberResponse res = bot.execute(new GetChatMember(chatId, userId));
	return res;
}
}
