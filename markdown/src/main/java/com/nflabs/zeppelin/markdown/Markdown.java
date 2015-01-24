package com.nflabs.zeppelin.markdown;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.pegdown.Extensions;
import org.pegdown.PegDownProcessor;

import com.nflabs.zeppelin.interpreter.Interpreter;
import com.nflabs.zeppelin.interpreter.InterpreterContext;
import com.nflabs.zeppelin.interpreter.InterpreterResult;
import com.nflabs.zeppelin.interpreter.InterpreterResult.Code;
import com.nflabs.zeppelin.scheduler.Scheduler;
import com.nflabs.zeppelin.scheduler.SchedulerFactory;

/**
 * Markdown interpreter for Zeppelin.
 *
 * @author Leemoonsoo
 * @author anthonycorbacho
 * @author felixcheung
 *
 */
public class Markdown extends Interpreter {
  private PegDownProcessor md;

  static {
    Interpreter.register("md", Markdown.class.getName());
  }

  public Markdown(Properties property) {
    super(property);
  }

  @Override
  public void open() {
    md = new PegDownProcessor(Extensions.ALL);
  }

  @Override
  public void close() {}

  @Override
  public Object getValue(String name) {
    return null;
  }

  @Override
  public InterpreterResult interpret(String st, InterpreterContext interpreterContext) {
    String html = md.markdownToHtml(st);
    if (html == null) {
      return new InterpreterResult(Code.ERROR, "Error parsing Markdown");
    }
    return new InterpreterResult(Code.SUCCESS, "%html " + html);
  }

  @Override
  public void cancel(InterpreterContext context) {}

  @Override
  public void bindValue(String name, Object o) {}

  @Override
  public FormType getFormType() {
    return FormType.SIMPLE;
  }

  @Override
  public int getProgress(InterpreterContext context) {
    return 0;
  }

  @Override
  public Scheduler getScheduler() {
    return SchedulerFactory.singleton().createOrGetParallelScheduler(
        Markdown.class.getName() + this.hashCode(), 5);
  }

  @Override
  public List<String> completion(String buf, int cursor) {
    return null;
  }
}
