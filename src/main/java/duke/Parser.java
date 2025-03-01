package duke;

import java.time.LocalDate;

/**
 * Represents a parser for commands
 */
class Parser {
    private final Ui ui;

    Parser() {
        this.ui = new Ui();
    }

    public Command parse(String command) throws DukeException {
        String fullCommand = command.toLowerCase();
        String commandWord = ui.getCommandWord(fullCommand);
        if (fullCommand.equals("bye")) {
            ExitCommand c = new ExitCommand(fullCommand);
            return c;
        } else if (fullCommand.equals("save")) {
            SaveCommand c = new SaveCommand(fullCommand);
            return c;
        } else if (fullCommand.equals("list")) {
            ListCommand c = new ListCommand(fullCommand);
            return c;
        } else if (commandWord.equals("todo")) {
            if (ui.isValidTask(fullCommand)) {
                String taskName = ui.getTaskName(fullCommand);
                ToDo todo = new ToDo(taskName);
                AddCommand addCommand = new AddCommand("todo", todo);
                return addCommand;
            } else {
                throw new EmptyDescriptionException("Todo description cannot be empty!");
            }
        } else if (commandWord.equals("deadline")) {
            if (!ui.isValidTask(fullCommand)) {
                throw new EmptyDescriptionException("Deadline description cannot be empty!");
            } else if (!ui.isValidDeadline(fullCommand)) {
                throw new InvalidCommandFormatException("Deadlines must include \"/by\"");
            } else {
                String taskName = ui.getTaskName(fullCommand);
                LocalDate localDate = ui.getTaskDate(fullCommand);
                Deadline deadline = new Deadline(taskName, localDate);
                AddCommand addCommand = new AddCommand("deadline", deadline);
                return addCommand;
            }


        } else if (commandWord.equals("event")) {
            if (!ui.isValidTask(fullCommand)) {
                throw new EmptyDescriptionException("Event description cannot be empty!");
            } else if (!ui.isValidEvent(fullCommand)) {
                throw new InvalidCommandFormatException("Events must include \"/at\"");
            } else {
                String taskName = ui.getTaskName(fullCommand);
                LocalDate localDate = ui.getTaskDate(fullCommand);
                Event event = new Event(taskName, localDate);
                AddCommand addCommand = new AddCommand("event", event);
                return addCommand;
            }

        } else if (commandWord.equals("mark")) {
            if (ui.isValidMarkFormat(fullCommand)) {
                MarkCommand markCommand = new MarkCommand(fullCommand,
                        ui.markIndex(fullCommand));
                return markCommand;
            } else {
                throw new InvalidCommandFormatException("Please include item index!");
            }
        } else if (commandWord.equals("unmark")) {
            if (ui.isValidUnmarkFormat(fullCommand)) {
                UnmarkCommand unmarkCommand = new UnmarkCommand(fullCommand,
                        ui.markIndex(fullCommand));
                return unmarkCommand;
            } else {
                throw new InvalidCommandFormatException("Please include item index!");
            }
        } else if (commandWord.equals("find")) {
            if (ui.isValidTask(fullCommand)) {
                FindCommand findCommand = new FindCommand(fullCommand);
                return findCommand;
            } else {
                throw new InvalidCommandFormatException("Invalid find!");
            }
        } else if (commandWord.equals("delete")) {
            if (ui.isValidDeleteFormat(fullCommand)); {
                DeleteCommand deleteCommand = new DeleteCommand(fullCommand,
                        ui.deleteIndex(fullCommand));
                return deleteCommand;
            }
        } else if (commandWord.equals("sort")) {
            SortCommand sortCommand = new SortCommand(fullCommand);
            return sortCommand;
        } else {
                String generalFormatExceptionString = "Please give a proper command!\n" +
                        "List of commands: \n" +
                        "1. todo\n" +
                        "2. deadline\n" +
                        "3. event\n" +
                        "4. list\n" +
                        "5. mark\n" +
                        "6. unmark\n" +
                        "7. find\n" +
                        "8. delete \n" +
                        "9. sort \n" +
                        "10. bye";
                throw new InvalidCommandFormatException(generalFormatExceptionString);
        }
    }
}
