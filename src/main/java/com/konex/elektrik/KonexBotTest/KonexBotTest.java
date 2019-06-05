//package com.konex.elektrik.KonexBotTest;
//
//import com.konex.elektrik.Entity.Order;
//import com.konex.elektrik.Entity.Status;
//import com.konex.elektrik.Entity.Subdivision;
//import com.konex.elektrik.Entity.User;
//import com.konex.elektrik.Service.Order.OrderService;
//import com.konex.elektrik.Service.Status.StatusService;
//import com.konex.elektrik.Service.Subdivision.SubdivisionService;
//import com.konex.elektrik.Service.User.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.telegram.telegrambots.bots.TelegramLongPollingBot;
//import org.telegram.telegrambots.api.methods.ParseMode;
//import org.telegram.telegrambots.api.methods.send.SendLocation;
//import org.telegram.telegrambots.api.methods.send.SendMessage;
//import org.telegram.telegrambots.api.methods.send.SendPhoto;
//import org.telegram.telegrambots.api.objects.PhotoSize;
//import org.telegram.telegrambots.api.objects.Update;
//import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
//import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardRemove;
//import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
//import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
//import org.telegram.telegrambots.exceptions.TelegramApiException;
//
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//@Component
//public class KonexBotTest extends TelegramLongPollingBot {
//
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private OrderService orderService;
//    @Autowired
//    private StatusService statusService;
//    @Autowired
//    private SubdivisionService subdivisionService;
//
//    @Override
//    public void onUpdateReceived(Update update) {
//
//// Set variables
//        String userFirstName = update.getMessage().getChat().getFirstName();
//        String userLastName = update.getMessage().getChat().getLastName();
//        String username = update.getMessage().getChat().getUserName();
//        String message_text = update.getMessage().getText();
//        long chat_id = update.getMessage().getChatId();
//        User user = userService.findUserByChatId(update.getMessage().getChatId());
//
//         if (update.getMessage().getContact() != null) {
//            try {
//                User userRegistChatId = userService.findUserByTelephone(update.getMessage().getContact().getPhoneNumber());
//
//                if (userRegistChatId.getId() != null) {
//                    userRegistChatId.setTelegramChatId(update.getMessage().getChatId());
//                    userService.addToUserChatId(userRegistChatId);
//                }
//            } catch (NullPointerException e) {
//                e.printStackTrace();
//            }
//        } else if (update.getMessage().getLocation() != null){
//             if (update.getMessage().getLocation().getLatitude() != null && update.getMessage().getLocation().getLongitude() != null) {
//                 user.setLatitude(update.getMessage().getLocation().getLatitude());
//                 user.setLongitude(update.getMessage().getLocation().getLongitude());
//                 userService.addToUserChatId(user);
//             }
//         } else if (update.hasMessage() && update.getMessage().hasText()) {
//
//            Long orderGetNumber = null;
//            try {
//                orderGetNumber = Long.valueOf(message_text.substring(1, message_text.length()));
//            } catch (NumberFormatException e) {
//                e.printStackTrace();
//            }
//
//            if (message_text.equals("/start")) {
//
//                SendMessage sendMessage = new SendMessage()
//                        .setChatId(chat_id)
//                        .setText("You send /start");
//
//                ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
//                sendMessage.setReplyMarkup(replyKeyboardMarkup);
//                replyKeyboardMarkup.setSelective(true);
//                replyKeyboardMarkup.setResizeKeyboard(true);
//                replyKeyboardMarkup.setOneTimeKeyboard(true);
//                List<KeyboardRow> keyboard = new ArrayList<>();
//
//                // first keyboard line
//                KeyboardRow keyboardFirstRow = new KeyboardRow();
//                KeyboardButton keyboardButton = new KeyboardButton();
//                keyboardButton.setText("Share your number").setRequestContact(true);
//                keyboardFirstRow.add(keyboardButton);
//
//                // second keyboard line
//                KeyboardRow keyboardSecondRow = new KeyboardRow();
//                KeyboardButton keyboardSecondButton = new KeyboardButton();
//                keyboardSecondButton.setText("Share your location").setRequestLocation(true);
//                keyboardSecondRow.add(keyboardSecondButton);
//                // add array to list
//                keyboard.add(keyboardFirstRow);
//                keyboard.add(keyboardSecondRow);
//
//                // add list to our keyboard
//                replyKeyboardMarkup.setKeyboard(keyboard);
//                System.out.println(sendMessage);
//                System.out.println(replyKeyboardMarkup);
//                try {
//                    sendMessage(sendMessage);
//                } catch (TelegramApiException e) {
//                    e.printStackTrace();
//                }
//            } else if(message_text.equals("/orders")) {
//                SendMessage message = new SendMessage()
//                        .setChatId(chat_id);
//                String textMessg = null;
//                Set<Order> orderList = orderService.getAllBySubdivision(userService.findUserByTelephone(user.getTelephone()).getSubdivisions());
//
//                for (Order order : orderList) {
//                    if (order.getStatus().getId() == 3L) {
//                        textMessg = "[Номер замовлення: "+ order.getId() + "](http://localhost:8080/order/track/" + order.getId() + ")" +
//                                "\nПодав: " + order.getUsers().getSurname() + " " + order.getUsers().getName() +
//                                "\nВід відділу: " + order.getUsers().getSubdivisions().getName() +
//                                "\nТекст заявки: " + order.getApplicationText() +
//                                "\nДата подачі: " + order.getDateOfApplication();
//                        message.setText(textMessg);
//                        message.setParseMode(ParseMode.MARKDOWN);
//                        try {
//                            sendMessage(message);
//                        } catch (TelegramApiException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            } else if (orderGetNumber != null) {
//                SendMessage sendMessage = new SendMessage()
//                        .setChatId(chat_id);
//                Order order = orderService.getById(Long.valueOf(message_text.substring(1, message_text.length())));
//                if (order.getStatus().getId() == 3L && order.getSubdivisions().getId() == user.getSubdivisions().getId()) {
//                    Status status = statusService.getById(2L);
//                    orderService.editOrderStatus(order, status, user.getSurname());
//                    sendMessage.setText("Заявка за номером:" + order.getId() + " прийнята вами");
//                    try {
//                        sendMessage(sendMessage);
//                    } catch (TelegramApiException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    sendMessage.setParseMode("Заявка за номером:" + order.getId() + " оброблена \nабо ви не відноситесь до віддлу, для якого призначено замовлення");
//                    try {
//                        sendMessage(sendMessage);
//                    } catch (TelegramApiException e) {
//                        e.printStackTrace();
//                    }
//                }
//            } else if (message_text.substring(0, 9).equals("/location")) {
//                String strFarmacyId = message_text.replaceAll("\\s+","");
//                Long farmacyId = Long.valueOf(strFarmacyId.substring(9, strFarmacyId.length()));
//
//                SendLocation sendLocation = new SendLocation()
//                        .setChatId(chat_id);
//                SendMessage message = new SendMessage()
//                        .setChatId(chat_id);
//
//                Subdivision subdivision = subdivisionService.findByName(farmacyId);
//                message.setText("[Карта](https://www.google.com.ua/maps/dir/" + subdivision.getCities().getCity() + " " +
//                        subdivision.getAddress() + "/" + user.getLatitude() +
//                        "," + user.getLongitude() + "/)");
//                message.setParseMode(ParseMode.MARKDOWN);
//                try {
//                    sendMessage(message);
////                    sendLocation(sendLocation);
//                } catch (TelegramApiException e) {
//                    e.printStackTrace();
//                }
//            }
//            else if (update.hasMessage() && update.getMessage().hasPhoto()) {
//                // Message contains photo
//                long chart_id = update.getMessage().getChatId();
//                List<PhotoSize> photos = update.getMessage().getPhoto();
//                String file_id = photos.stream()
//                        .sorted(Comparator.comparing(PhotoSize::getFileId).reversed())
//                        .findFirst()
//                        .orElse(null).getFileId();
//                int f_width = photos.stream()
//                        .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
//                        .findFirst()
//                        .orElse(null).getWidth();
//                int f_height = photos.stream()
//                        .sorted(Comparator.comparing(PhotoSize::getFileId).reversed())
//                        .findFirst()
//                        .orElse(null).getHeight();
//                String caption = "file_id " + file_id + "\nwidth: " + Integer.toString(f_width) + "\nheight: " +
//                        Integer.toString(f_height);
//                SendPhoto msg = new SendPhoto()
//                        .setChatId(chart_id)
//                        .setPhoto(file_id)
//                        .setCaption(caption);
//                try {
//                    sendPhoto(msg);
//                } catch (TelegramApiException e) {
//                    e.printStackTrace();
//                }
//            } else if(message_text.equals("/pic")) {
//
//                SendPhoto msg = new SendPhoto()
//                        .setChatId(chat_id)
//                        .setPhoto("AgADAgADLqkxG1n5yEpJ_bc9u-DhBZxRqw4ABMeuqzf4Itq7AAHvAwABAg")
//                        .setCaption("Photo");
//                try {
//                    sendPhoto(msg);
//                } catch (TelegramApiException e) {
//                    e.printStackTrace();
//                }
//            } else if (message_text.equals("/markup")) {
//                SendMessage message = new SendMessage()
//                        .setChatId(chat_id)
//                        .setText("Here is your keyboard");
//                ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
//                List<KeyboardRow> keyboard = new ArrayList<>();
//                KeyboardRow row = new KeyboardRow();
//                row.add("Row 1 Button 1");
//                row.add("Row 1 Button 2");
//                row.add("Row 1 Button 3");
//
//                keyboard.add(row);
////                row = new KeyboardRow();
////
////                row.add("Row 2 Button 1");
////                row.add("Row 2 Button 2");
////                row.add("Row 2 Button 3");
////
////                keyboard.add(row);
//
//                keyboardMarkup.setKeyboard(keyboard);
//                message.setReplyMarkup(keyboardMarkup);
//                try {
//                    sendMessage(message);
//                } catch (TelegramApiException e){
//                    e.printStackTrace();
//                }
//            } else if (message_text.equals("Row 1 Button 1")) {
//                SendPhoto msg = new SendPhoto()
//                        .setChatId(chat_id)
//                        .setPhoto("AgADAgADLqkxG1n5yEpJ_bc9u-DhBZxRqw4ABMeuqzf4Itq7AAHvAwABAg")
//                        .setCaption("Photo");
//                try {
//                    sendPhoto(msg);
//                } catch (TelegramApiException e) {
//                    e.printStackTrace();
//                }
//            } else if (message_text.equals("/hide")) {
//                SendMessage msg = new SendMessage()
//                        .setChatId(chat_id)
//                        .setText("Keyboard hidden");
//                ReplyKeyboardRemove keyboardRemove = new ReplyKeyboardRemove();
//                msg.setReplyMarkup(keyboardRemove);
//                try {
//                    sendMessage(msg);
//                } catch (TelegramApiException e) {
//                    e.printStackTrace();
//                }
//            }
//            else {
//                SendMessage message = new SendMessage()
//                        .setChatId(chat_id)
//                        .setText("Unknown command");
//                try {
//                    sendMessage(message);
//                } catch (TelegramApiException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    private void log(String firstName, String LastName, String userId, String text, String bot_answer) {
//
//        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//        Date date = new Date();
//
//    }
//
//    @Override
//    public String getBotUsername() {
//        return "KonexBotTest";
//    }
//
//    @Override
//    public String getBotToken() {
//        return "650236335:AAGXvY0OLqNHLT1naXUAUN0-mwCO_0FPe4g";
//    }
//}
