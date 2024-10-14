package tutorease.address.logic.parser;

import static tutorease.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import tutorease.address.commons.core.index.Index;
import tutorease.address.logic.commands.DeleteLessonCommand;
import tutorease.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new DeleteLessonCommand object.
 */
public class DeleteLessonCommandParser implements Parser<DeleteLessonCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteLessonCommand
     * and returns a DeleteLessonCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public DeleteLessonCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args.trim());
            return new DeleteLessonCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteLessonCommand.MESSAGE_USAGE), pe);
        }
    }
}

