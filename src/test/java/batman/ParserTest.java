package batman;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import batman.parser.Parser;
import batman.tasks.TaskList;

public class ParserTest {

    private static final String[] COMMANDS = {"event", "deadline", "todo"};
    private static final String[] DESC = {" project meeting ", " return book ", " borrow book "};
    private static final String DATETIME = "1/01/2022 11:00";
    private StringBuilder result;
    private String expected;
    private String input;

    @Test
    public void parser_incorrectInput_invalidCommandResult() {
        String command = "test";
        result = Parser.parseInput(command);
        expected = "I'm sorry, but I don't know what that means.\n";
        assertEquals(expected, result.toString());
    }

    @Test
    public void parser_incorrectInput_emptyDescResult() {
        for (int i = 0; i < 3; i++) {
            StringBuilder result = Parser.parseInput(COMMANDS[i]);
            expected = "The description of a task cannot be empty.\n";
            assertEquals(expected, result.toString());
        }
    }

    @Test
    public void parser_incorrectInput_missingArgResult() {
        for (int i = 0; i < 2; i++) {
            String input = COMMANDS[i] + DESC[i] + DATETIME;
            StringBuilder result = Parser.parseInput(input);
            expected = "Missing argument(s) for tasks\n"
                   + "e.g. <task> <desc> /(at or by) <datetime>\n";
            assertEquals(expected, result.toString());
        }
    }

    @Test
    public void parser_correctInput_validCommandResult() {
        TaskList taskList = new TaskList();
        input = COMMANDS[0] + DESC[0] + "/at " + DATETIME;
        result = Parser.parseInput(input);
        expected = "Got it. Task added:\n  " + taskList.get(0)
                + "\nNow you have " + taskList.getSize() + " tasks in the list.\n";
        assertEquals(expected, result.toString());

        input = COMMANDS[1] + DESC[1] + "/by " + DATETIME;
        result = Parser.parseInput(input);
        expected = "Got it. Task added:\n  " + taskList.get(1)
                + "\nNow you have " + taskList.getSize() + " tasks in the list.\n";
        assertEquals(expected, result.toString());

        input = COMMANDS[2] + DESC[2];
        result = Parser.parseInput(input);
        expected = "Got it. Task added:\n  " + taskList.get(2)
                + "\nNow you have " + taskList.getSize() + " tasks in the list.\n";
        assertEquals(expected, result.toString());

        input = "delete 3";
        result = Parser.parseInput(input);
        expected = "Got it. Task removed:\n  [T][ ]" + DESC[2]
                + "\nNow you have " + taskList.getSize() + " tasks in the list.\n";
        assertEquals(expected, result.toString());
    }

    @Test
    public void parser_correctInput_invalidIndexResult() {
        TaskList taskList = new TaskList();
        input = "delete 3";
        result = Parser.parseInput(input);
        expected = taskList.printList() + "Index does not exists in array.\n";
        assertEquals(expected, result.toString());

        input = "mark 3";
        result = Parser.parseInput(input);
        expected = taskList.printList() + "Index does not exists in array.\n";
        assertEquals(expected, result.toString());
    }

}
