package dev.xernas.loupgarou.utils;

import dev.xernas.loupgarou.messages.Message;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;

public class PromptUtils {

    public static Prompt getYesNoPrompt(Message question, BiConsumer<Boolean, ConversationContext> onAccept) {
        return new StringPrompt() {
            @NotNull
            @Override
            public String getPromptText(@NotNull ConversationContext conversationContext) {
                return question.getMessage();
            }
            @Nullable
            @Override
            public Prompt acceptInput(@NotNull ConversationContext conversationContext, @Nullable String s) {
                if (s != null) {
                    if (s.equalsIgnoreCase("Y")) {
                        onAccept.accept(true, conversationContext);
                    } else if (s.equalsIgnoreCase("N")) {
                        onAccept.accept(false, conversationContext);
                    }
                }
                return Prompt.END_OF_CONVERSATION;
            }
        };
    }

}
