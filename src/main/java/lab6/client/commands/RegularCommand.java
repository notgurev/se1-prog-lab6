package lab6.client.commands;

/**
 * Класс серверной команды, которая в clientExecute() должна (возможно) произвести валидацию, а затем вернуть
 * true, чтобы быть отправленной на сервер.
 */
public abstract class RegularCommand extends AbstractCommand {

    public RegularCommand(boolean isScriptCalling, String helpText, String key) {
        super(isScriptCalling, true, helpText, key);
    }
}
