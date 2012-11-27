package sh.calaba.instrumentationbackend.actions.checkbox;


import sh.calaba.instrumentationbackend.InstrumentationBackend;
import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.actions.Action;


public class CheckCheckBoxNumber implements Action {

    @Override
    public Result execute(String... args) {
        boolean checked = InstrumentationBackend.solo.isCheckBoxChecked(Integer.parseInt(args[0]) - 1);
        return new Result(true, String.valueOf(checked));
    }
    @Override
    public String key() {
        return "is_checked_checkbox_number";
    }

}
