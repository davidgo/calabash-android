package sh.calaba.instrumentationbackend.actions.checkbox;


import sh.calaba.instrumentationbackend.InstrumentationBackend;
import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.actions.Action;


public class ConfirmCheckBoxNumber implements Action {

    @Override
    public Result execute(String... args) {
        String target = args[1];
        boolean checked = InstrumentationBackend.solo.isCheckBoxChecked(Integer.parseInt(args[0]) - 1);
        if (String.valueOf(checked) == target){
            return Result.successResult();
        }
        else{
            return new Result(false, "Failed to toggle checkbox: " + args[0]);
        }
    }
    
    @Override
    public String key() {
        return "confirm_checkbox_number";
    }

}
