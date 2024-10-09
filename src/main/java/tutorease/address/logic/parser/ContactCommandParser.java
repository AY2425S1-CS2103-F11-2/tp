package tutorease.address.logic.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tutorease.address.logic.commands.Command;
import tutorease.address.logic.commands.DeleteContactCommand;
import tutorease.address.logic.commands.ListContactCommand;
import tutorease.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates the appropriate ContactCommand objects.
 */
public class ContactCommandParser implements Parser<Command> {
    public static final String COMMAND_WORD = "contact";
    private static final Pattern CONTACT_COMMAND_FORMAT = Pattern.compile("(?<subCommand>\\S+)\\s*(?<subArguments>.*)");

    @Override
    public Command parse(String args) throws ParseException {
        final Matcher matcher = CONTACT_COMMAND_FORMAT.matcher(args.trim());

        if (!matcher.matches()) {
            throw new ParseException("Invalid contact command format. Please refer to the help for usage.");
        }

        final String subCommand = matcher.group("subCommand").toLowerCase();
        final String subArguments = matcher.group("subArguments");

        switch (subCommand) {
        case DeleteContactCommand.SUB_COMMAND_WORD:
            return new DeleteContactCommandParser().parse(subArguments);
        case ListContactCommand.SUB_COMMAND_WORD:
            return new ListContactCommand();
        // Future sub-commands like add, edit can be handled here
        default:
            throw new ParseException("Unknown contact sub-command: " + subCommand);
        }
    }
}

